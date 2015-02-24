package sk.seges.sesam.tools;

import gnu.getopt.Getopt;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class JNDICreator {
    private static final Logger log = Logger.getLogger(JNDICreator.class);
    
    private Map<String, Object> objects;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setObjects(Map<String, Object> objects) {
        this.objects = objects;
    }

    public void bind() throws NamingException {
        if(log.isInfoEnabled()) {
            log.info("Starting binding to context = " + context.getEnvironment());
        }
        
        for (Entry<String, Object> entry : objects.entrySet()) {
		String fsDir = fsDir();
		if(!isWindows() && fsDir != null) {
			if(fsDir.startsWith("file://")) {
				fsDir = fsDir.substring(7);
			}
			String jndiPath = entry.getKey().substring(0, entry.getKey().lastIndexOf("/"));
			File jndi = new File(fsDir, jndiPath);
			if(!jndi.exists()) {
				boolean res = jndi.mkdirs();
				if(log.isInfoEnabled()) {
					log.info("Created FS dir = " + jndi + ", res = " + res);
				}
			}
		}
            context.bind(entry.getKey(), entry.getValue());
            if(log.isInfoEnabled())
                log.info("Bound name = " + entry.getKey() + ", object = " + entry.getValue());
        }
        
        if(log.isInfoEnabled())
            log.info("All objects (" + objects.size() + ") bound successfully.");
    }

	public static String fsDir() {
		if("com.sun.jndi.fscontext.RefFSContextFactory".equals(System.getProperty("java.naming.factory.initial"))) {
			return System.getProperty("java.naming.provider.url");
		}
		return null;
	}

	public static boolean isWindows(){
 		String os = System.getProperty("os.name").toLowerCase();
		//windows
		return (os.indexOf( "win" ) >= 0); 
	}

    public static void main(String[] args) {
    	String file = null;
    	
    	int c;
		Getopt g = new Getopt(JNDICreator.class.getSimpleName(), args, "-f:h");
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'f':
				file = g.getOptarg();
				break;				
			case 'h':
			default:
				help();
				System.exit(42);
			}
		}
        
        String springContextFile = "file:" + file;
        
        if(! (new File(file).exists()) ){
            System.err.println("Initialization xml file " + file + " does not exist."); 
            System.exit(42);
        };
        
        new FileSystemXmlApplicationContext (springContextFile);        
    }

	private static void help() {
		System.out.println(JNDICreator.class.getSimpleName() + " [options]");
		System.out.println("Supported options:");
		System.out.println("-h\t\t\tprint this help");
		System.out.println("-f initializer script\tPath to initializer script (XML file)");
	}
}
