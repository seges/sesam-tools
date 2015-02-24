#!/usr/bin/env python

import sys
from os import *
from os.path import *

def main(args):

	WORKDIR = dirname(args[0])
	print len(args)
	if len(args) < 2:
		print """`basename $0` <JNDI_output_dir> <initializer_script_path>
			JNDI_output_dir       - absolute (!!!) path to JNDI bindings directory
			initializer_script_path - path to initializer script file"""
		exit(42)

	WORKDIR = "."
	BINDINGS_DIR = args[1]
	SCRIPT = args[2]

	system("java -Djava.naming.factory.initial=com.sun.jndi.fscontext.RefFSContextFactory -Djava.naming.provider.url=file://" + BINDINGS_DIR + " -jar " + WORKDIR + "/lib/sesam-tools-${pom.version}.jar -f " + SCRIPT)

if __name__ == '__main__':
    main(sys.argv[0:])

