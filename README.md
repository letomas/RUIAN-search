# Vyhledávání v RÚIAN
Tato apliakce slouží k usnadnění vyhledávání v registru územní identifikace, adres a nemovitostí.

### Navigace
  - [O projektu](#o-projektu)
  - [Požadavky](#sw-po%c5%beadavky)
  - [Spuštění](#spu%c5%a1t%c4%9bn%c3%ad)
  - [Indexace dat](#indexace-dat)
  - [Open API](#open-api)
  - [Konfigurace](#konfigurace)

## O projektu
Aplikace využívá třívrstvou kontejnerizovanou architekturu. Na datové vrstvě je použit Apache Solr, na aplikační vrstvě spring-boot a na prezentační vrstvě vue a vuetify. Data jsou získávána pomocí skriptu z [této stránky](https://nahlizenidokn.cuzk.cz/StahniAdresniMistaRUIAN.aspx) ve formátu csv. Data na stránce jsou aktualizována každý měsíc (poslední den v měsíci). Pro aktualizaci dat v aplikaci je nutné ručně sputit k tomu určený skript (viz [Spuštění](#spuštění)).

## SW Požadavky
- Docker
- Docker-compose
- Bash

## Spuštění
>**Upozornění:** Pro spuštění je nutné zachovat unixové konce řádků (LF). Pokud máte v gitu nastavené automatické převádění na CRLF, vypněte toto nastavení pomocí příkazu `git config --global core.autocrlf input`. Popřípadě zkonvertujte konce řádků pomocí nástroje dos2unix nebo textového editoru.

Po stažení/naklonování projektu je možné aplikaci spustit pomocí skriptu:
```
./init.sh
```
Při provedení změn je nutné přidat argument `build`:
```
./init.sh build
```
Tento skript volá docker-compose up -d (případně ještě s argumentem --build) a kontroluje stav kontejnerů. Jakmile jsou všechny kontejnery připravené, vypíše se do příkazové řádky: `Application is ready`. Při prvním spuštětní a při aktualizaci dat je třeba použít příslušný skript viz [indexace dat](#indexace-dat).


## Indexace dat
K nahrání a aktualizaci dat slouží skript:
```
./index.sh
```
Skript potřebuje ke spuštění zbuildit image, který umožní úpravu CSV souborů. Image stačí zbuildit jednou. Další build je nutný pouze pokud provedete změny v aplikaci pro úpravu dat.

 Image je možné zbuildit buď přidáním argumentu `build`:
```
./index.sh build
```
, nebo manuálně tímto příkazem:
```
docker build --tag csvmodifier ./CSVModifier/
```
Pokud se rozhodnete build image provést manuálně, musíte jej provést před spuštěním skriptu pro indexaci.

Skript stáhne zip soubor, který osahuje přes 6000 csv souborů, jeden soubor pro každou obec v ČR. Zip soubor je nutné rozbalit. Csv soubory se musí upravit (zkonvertovat kódování z Windows-1250 na UTF-8 a přidat sloupce s identifikací a zkonvertovanými souřadnicemi) a následně nahrát do Solru pomocí Post toolu (nástroj pro nahrávání souborů do Solru přes příkazovou řádku). Celý proces může zabrat přibližně 20 minut. Data se uchovávají v Docker volume, takže při restartu aplikace není třeba data znovu indexovat.

Po spuštění je aplikace dostupná z [http://localhost:8000](http://localhost:8000)

## Konfigurace
Aplikace je ve výchozím stavu dostupná z portu 8000. Toto nastavení je možné změnit v `Docker-compose.yml`. Je třeba změnit nastavení portů u služby **vue-app**.

## Open API
Restové rohzraní je popsáno pomocí specifikace Open API. Dokumentace je dostupná na adrese:
[https://app.swaggerhub.com/apis-docs/letomas/Address-search-RUIAN/1.0.0-oas3#/](https://app.swaggerhub.com/apis-docs/letomas/Address-search-RUIAN/1.0.0-oas3)
