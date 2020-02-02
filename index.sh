#!/bin/bash

# Download RUIAN data
url=$(wget -q -O - https://nahlizenidokn.cuzk.cz/StahniAdresniMistaRUIAN.aspx | grep 'id="ctl00_bodyPlaceHolder_linkCR"' | sed -r 's/^.+href="([^"]+)".+$/\1/')
echo "Downloading RUIAN data"
wget $url
unzip -a *.zip

# Convert encoding from Windows 1250 to UTF-8
echo "Converting encoding..."
mkdir -p data
for file in CSV/*.csv; do
	outputFile=${file#*/}
	iconv -f CP1250 -t UTF-8 "$file" -o "data/$outputFile"
done

# Delete data in core
echo "Deleting existing data"
curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/ruian/update?commit=true' -d '{ "delete": {"query":"*:*"} }'

# Index data using post tool
echo "Indexing data"
docker run --rm -v "$PWD/data:/data" --network=host solr:8 post -c ruian -params "separator=%3B" /data/

# Delete files
rm -rf *.zip CSV data

exit 0
