#!/bin/sh

# Delete data in core
curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/ruian/update?commit=true' -d '{ "delete": {"query":"*:*"} }'

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

# Ingest data using post tool
docker run --rm -v "$PWD/data:/data" --network=host solr:8 post -c ruian -params "separator=%3B" /data/

# Delete files
rm -rf *.zip CSV data

exit 0
