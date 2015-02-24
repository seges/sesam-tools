#!/bin/bash

function create()
{
	find `pwd`/../$1 -name .bindings -exec rm {} \;
	./jndi-create.sh `pwd`/../$1 ../initializers/jndi-initializer-$1.xml
}

list="fwd-zetta-acris fwd-zetta-acris2 fwd-zetta-acris_profi local-acris_test zetta-acris_profi"

for jndi in $list; do
	echo "[jndi] working on $jndi"
	create $jndi
done
