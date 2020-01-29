#!/bin/sh

# Download RUIAN data
url=$(wget -q -O - https://nahlizenidokn.cuzk.cz/StahniAdresniMistaRUIAN.aspx | grep 'id="ctl00_bodyPlaceHolder_linkCR"' | sed -r 's/^.+href="([^"]+)".+$/\1/')
wget $url
unzip -a *.zip

# Convert encoding from Windows 1250 to UTF-8
echo "Converting encoding..."
mkdir -p data
for file in CSV/*.csv; do
	outputFile=${file#*/}
	iconv -f CP1250 -t UTF-8 "$file" -o "data/$outputFile"
done

{
	# Delete data in core
	curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/ruian/update?commit=true' -d '{ "delete": {"query":"*:*"} }'

	# Update Solr schema
	# Some fields need to be converted to text so they can be used in search
	# There are fields, which can be empty, so they need default value to be indexed
	curl -X POST -H 'Content-type:application/json' --data-binary '{
		"add-field":[
			{
				"name":"K_d_ADM",
				"type":"string",
			},
			{
				"name":"__slo_domovn_",
				"type":"string",
			},
			{
				"name":"PS_",
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
				"name":"N_zev_obce",
				"type":"text_general",
				"default":"",
			},
			{
				"name":"Search_field"
				"type":"text_general",
				"default":"",
				"multiValued":true,
			}
		]
	}' http://localhost:8983/solr/ruian/schema

	curl -X POST -H 'Content-type:application/json' --data-binary '{
		"delete-copy-field":[
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
	}' http://localhost:8983/solr/ruian/schema

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
	}' http://localhost:8983/solr/ruian/schema
} >> solr_logs.log

# Ingest data using post tool
docker run --rm -v "$PWD/data:/data" --network=host solr:8 post -c ruian -params "separator=%3B" /data/

# Delete files
rm -rf *.zip CSV data

exit 0
