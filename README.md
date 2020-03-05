# Vyhledávání v RÚIAN
Tato apliakce slouží k usnadnění vyhledávání v registru územní identifikace, adres a nemovitostí.

### Navigace
  - [O projektu](#o-projektu)
  - [Požadavky](#po%c5%beadavky)
  - [Spuštění](#spu%c5%a1t%c4%9bn%c3%ad)
  - [Konfigurace](#Konfigurace)

## O projektu
Aplikace využívá třívrstvou kontejnerizovanou architekturu. Na datové vrstvě je použit Apache Solr, na aplikační vrstvě spring-boot a na prezentační vrstvě vue a bootstrap. Data jsou získávána pomocí skriptu z [této stránky](https://nahlizenidokn.cuzk.cz/StahniAdresniMistaRUIAN.aspx) ve formátu csv. Data na stránce jsou aktualizována každý měsíc (poslední den v měsíci). Pro aktualizaci dat v aplikaci je nutné ručně sputit k tomu určený skript (viz [Spuštění](#spuštění)).

## Požadavky
- docker
- docker-compose
- bash

## Spuštění
Po stažení/naklonování projektu je možné aplikaci sputit pomocí příkazu:
```
docker-compose up -d
```
Při provedení změn je nutné znovu sestavit image, je tedy nutné použít příkaz:
```
docker-compose up --build -d
```

K nahrání a aktualizaci dat je nutné spusit skript:
```
./index.sh
```
Skript stáhne zip soubor, který osahuje přes 6000 csv souborů, jeden soubor pro každou obec v ČR. Zip soubor je nutné rozbalit. Csv soubory se musí upravit (zkonvertovat kódování z Windows-1250 na UTF-8) a následně nahrát do Solru pomocí Post toolu (nástroj pro nahrávání souborů do Solru přes příkazovou řádku). Celý proces může zabrat přibližně 10 minut.

## Konfigurace
Součástí projektu je soubor `.env`, ve kterém je možné nastavit porty jednotlivých aplikacích. Ve výchozím stavu jsou porty nastaveny takto:
- solr: 8983
- spring-boot: 8081
- vue: 8000
