#!/bin/bash

WORKDIR=`dirname $0`

if [[ $# -lt 2 ]] ; then
	echo "`basename $0` <JNDI_output_dir> <initializer_script_path>"
	echo "	JNDI_output_dir       - absolute (!!!) path to JNDI bindings directory"
	echo "	initializer_script_path - path to initializer script file"
	exit
fi

BINDINGS_DIR="$1"
SCRIPT="$2"

java -Djava.naming.factory.initial=com.sun.jndi.fscontext.RefFSContextFactory -Djava.naming.provider.url=file://$BINDINGS_DIR -jar $WORKDIR/lib/sesam-tools-${pom.version}.jar -f $SCRIPT

