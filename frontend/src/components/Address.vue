<template>
  <v-container class="text-left px-5" v-if="address" fluid>
    <v-container fluid>
      <v-row v-for="item in items" :key="item.name">
        <v-col class="font-weight-bold py-1" cols="12" md="3">{{
          item.name
        }}</v-col>
        <v-col class="py-1">{{ item.value }} </v-col>
      </v-row>

      <v-row>
        <v-col class="font-weight-bold py-1" cols="12" md="3">
          <a
            id="iri"
            href="https://ofn.gov.cz/adresy/draft/#vlastnost-adresa-adresní-místo"
            rel="noopener noreferrer"
          >
            IRI:
          </a>
        </v-col>
        <v-col class="py-1">
          <a :href="IRIBaseUrl + address.admCode">
            {{ IRIBaseUrl }}{{ address.admCode }}
          </a>
        </v-col>
      </v-row>
    </v-container>

    <v-divider></v-divider>

    <v-container class="mt-4" fluid>
      <h3>
        Adresa dle vyhlášky č. 359/2011 Sb.
        <v-btn
          href="https://www.zakonyprolidi.cz/cs/2011-359?citace=1#prilohy"
          rel="noopener noreferrer"
          small
          icon
          ><v-icon>help_outline</v-icon></v-btn
        >
      </h3>
      <v-row>
        <v-col class="py-1">{{ firstRow }}</v-col>
      </v-row>
      <v-row>
        <v-col class="py-1">{{ secondRow }}</v-col>
      </v-row>
      <v-row>
        <v-col class="py-1">{{ thirdRow }}</v-col>
      </v-row>
      <v-row class="my-1">
        <v-col class="font-weight-bold" cols="12" md="3"
          >Adresa v řádku:
        </v-col>
        <v-col>{{ inline }}</v-col>
      </v-row>
    </v-container>

    <div v-if="coordinates">
      <v-divider></v-divider>
      <v-container fluid>
        <v-row>
          <v-col class="font-weight-bold"
            >Souřadnice S-JTSK
            <v-btn
              href="https://geoportal.cuzk.cz/(S(we0f0rkrjb0vrqvoy4akhx3u))/Default.aspx?mode=TextMeta&side=sit.trans&text=souradsystemy"
              rel="noopener noreferrer"
              small
              icon
              ><v-icon>help_outline</v-icon></v-btn
            >
          </v-col>
        </v-row>
        <v-row>
          <v-col class="py-1">
            X: {{ address.coordinateX }}, Y: {{ address.coordinateY }}
          </v-col>
        </v-row>
        <v-row>
          <v-col class="font-weight-bold">Souřadnice GPS</v-col>
        </v-row>
        <v-row>
          <v-col class="py-1">
            X: {{ coordinates[0] }}, Y: {{ coordinates[1] }}
          </v-col>
        </v-row>
        <v-row v-for="mapLink in mapLinks" :key="mapLink.name">
          <v-col class="py-1">
            <a :href="mapLink.link" rel="noopener noreferrer" target="_blank"
              ><v-icon>{{ mapLink.icon }}</v-icon> {{ mapLink.name }}</a
            >
          </v-col>
        </v-row>
      </v-container>
      <v-container id="map-container" class="mt-4 map" fluid>
        <l-map :center="coordinates" :zoom="zoom" :minZoom="3">
          <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
          <l-marker :lat-lng="coordinates" />
        </l-map>
      </v-container>
    </div>
  </v-container>
</template>

<script>
import "leaflet/dist/leaflet.css";
import addressBuilder from "../addressBuilder.js";

import { LMap, LTileLayer, LMarker } from "vue2-leaflet";
import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png")
});

export default {
  name: "Address",
  props: {
    address: {
      type: Object
    }
  },
  components: {
    LMap,
    LTileLayer,
    LMarker
  },
  computed: {
    firstRow: function() {
      return addressBuilder.build(this.address).firstRow;
    },
    secondRow: function() {
      return addressBuilder.build(this.address).secondRow;
    },
    thirdRow: function() {
      return addressBuilder.build(this.address).thirdRow;
    },
    inline: function() {
      return addressBuilder.buildInline(this.address);
    },
    coordinates: function() {
      let coords = this.address.coordinatesLatLon;
      if (!coords) {
        return null;
      }

      return [coords.x, coords.y];
    },
    items: function() {
      return [
        { name: "Kód adresního místa:", value: this.address.admCode },
        { name: "Obec:", value: this.address.cityName },
        { name: "Část obce:", value: this.address.districtName },
        {
          name: "Městský obvod/městská část:",
          value: this.address.boroughName
        },
        { name: "Název ulice:", value: this.address.streetName },
        { name: "PSČ:", value: this.address.postalCode },
        { name: "Číslo domovní:", value: this.address.houseNumber },
        {
          name: "Číslo orientační:",
          value:
            this.address.orientationalNumber +
            this.address.orientationalNumberLetter
        }
      ];
    },
    mapLinks: function() {
      return [
        {
          name: "Google Maps",
          icon: "room",
          link:
            "https://www.google.com/maps/search/?api=1&query=" +
            this.coordinates
        },
        {
          name: "Mapy.cz",
          icon: "explore",
          link:
            "https://mapy.cz/?source=coor&id=" +
            this.coordinates[1] +
            "," +
            this.coordinates[0]
        },
        {
          name: "OpenStreetMap",
          icon: "map",
          link:
            "http://www.openstreetmap.org/?mlat=" +
            this.coordinates[0] +
            "&mlon=" +
            this.coordinates[1] +
            "&zoom=20"
        }
      ];
    }
  },
  data() {
    return {
      IRIBaseUrl: "https://linked.cuzk.cz/resource/ruian/adresni-misto/",
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      zoom: 20,
      hasCoordinates: true
    };
  }
};
</script>

<style scoped>
#map-container {
  height: 35rem;
}

a {
  text-decoration: none;
}

#iri {
  border-bottom: 2px dotted;
  color: black;
}

#iri:hover {
  cursor: help;
}
</style>
