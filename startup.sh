#!/bin/bash

# This script starts Solr on localhost, creates a core with "solr create",
# configures it with the Schema API, stops Solr, and then starts Solr as normal.
# Any arguments are passed to the "solr create".
# To simply create a core:
#      docker run -p 8983:8983 -d -v $PWD/startup.sh:/startup.sh:ro solr /startup.sh -c mycore
#
# Source: https://github.com/docker-solr/docker-solr-examples/blob/master/schema-api-startup/myscript.sh

set -e
echo "Executing $0 $@"

# Allow easier debugging with `docker run -e VERBOSE=yes`
if [[ "$VERBOSE" = "yes" ]]; then
    set -x
fi

# Run the optional initdb
. /opt/docker-solr/scripts/run-initdb

# Start a Solr so we can use the Schema API, but only on localhost,
# so that clients don't see Solr until we have configured it.
start-local-solr

# Get the core name.
core_name=$1

# Check whether core already exists
if [ -d "/var/solr/data/$core_name" ]; then
	echo "Core already exists, skipping creation"
else
	# Now configure with the Schema API
	# Modify this with your desired schema configuration
	echo "Creating core: $core_name"
	/opt/solr/bin/solr create -c $core_name

	curl -X POST -H 'Content-type:application/json' --data-binary '{
		"add-field":[
			{
				"name":"K_d_ADM",
				"type":"string",
			},
			{
				"name":"K_d_obce",
				"type":"string",
			},
			{
				"name":"N_zev_obce",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"K_d_MOMC",
				"type":"string",
				"default":"",
			},
			{
				"name":"N_zev_MOMC",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"K_d_MOP",
				"type":"string",
				"default":"",
			},
			{
				"name":"N_zev_MOP",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"K_d___sti_obce",
				"type":"string",
			},
			{
				"name":"N_zev___sti_obce",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"K_d_ulice",
				"type":"string",
				"default":"",
			},
			{
				"name":"N_zev_ulice",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"Typ_SO",
				"type":"text_general",
			},											
			{
				"name":"__slo_domovn_",
				"type":"string",
			},
			{
				"name":"__slo_orienta_n_",
				"type":"string",
				"default":"",
			},			
			{
				"name":"Znak___sla_orienta_n_ho",
				"type":"string",
				"default":"",
			},			
			{
				"name":"PS_",
				"type":"string",
			},
			{
				"name":"Sou_adnice_X",
				"type":"pdoubles",
			},
			{
				"name":"Sou_adnice_Y",
				"type":"pdoubles",
			},
			{
				"name":"Plat__Od",
				"type":"pdates",
			},
			{
				"name":"Search_field"
				"type":"text_general",
				"default":"",
				"multiValued":true,
			}
		]
	}' http://localhost:8983/solr/$core_name/schema

	curl -X POST -H 'Content-type:application/json' --data-binary '{
		"add-copy-field":[
			{
				"source":"N_zev_*",
				"dest":"Search_field"
			},
			{
				"source":"PS_",
				"dest":"Search_field"
			},
			{
				"source":"__slo_domovn_",
				"dest":"Search_field"
			},
			{
				"source":"__slo_orienta_n_",
				"dest":"Search_field"
			},
			{
				"source":"Znak___sla_orienta_n_ho",
				"dest":"Search_field"
			},  
		]            
	}' http://localhost:8983/solr/$core_name/schema
fi

echo "Finished configuring with the Schema API"

stop-local-solr

# Now run Solr in the foreground, listening to all interfaces
exec solr -f