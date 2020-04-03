#!/bin/bash

# Download RUIAN data
url=$(wget -q -O - https://nahlizenidokn.cuzk.cz/StahniAdresniMistaRUIAN.aspx | grep 'id="ctl00_bodyPlaceHolder_linkCR"' | sed -r 's/^.+href="([^"]+)".+$/\1/')
echo "Downloading RUIAN data"
wget "$url"
unzip -a -- *.zip

echo "Modifying csv files..."
mkdir -p data
mkdir -p temp
for file in CSV/*.csv; do
	outputFile=${file#*/}
	# Convert encoding from Windows 1250 to UTF-8
	iconv -f CP1250 -t UTF-8 "$file" -o "temp/$outputFile"
	rm "$file"
	# Add new column containing house number and orientational number combined
	# Column 13 contains house number, column 14 contains orientational number and column 15 contains orientational number letter
	awk -F";" 'NR>1 {$20 = $14 ? $13 "/" $14 $15 : $13; print} NR==1 {$20 = "Identifikace"; print}' OFS=";" "temp/$outputFile" > "data/$outputFile"
	rm "temp/$outputFile"
done

# Delete data in core
echo "Deleting existing data"
curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/ruian/update?commit=true' -d '{ "delete": {"query":"*:*"} }'

# Index data using post tool
echo "Indexing data"
docker run --rm -v "$PWD/data:/data" --network=host solr:8.3 post -c ruian -params "separator=%3B" /data/

if [[ "$?" != 0 ]]; then
	echo "Failed to index data, check if docker is running"
	rm -rf -- *.zip CSV temp
	exit 1
fi

# Delete files
rm -rf -- *.zip CSV temp data

echo "Indexing done"
exit 0
