<template>
  <div id="address">
    <b-container class="address-info" fluid>
      <b-row>
        <b-col class="header" cols="3">Kód:</b-col>
        <b-col>{{ address.admCode }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">Obec:</b-col>
        <b-col>{{ address.cityName }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">Část obce:</b-col>
        <b-col>{{ address.districtName }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">Městská část/obvod:</b-col>
        <b-col>{{ address.boroughName }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">Ulice:</b-col>
        <b-col>{{ address.streetName }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">PSČ:</b-col>
        <b-col>{{ address.postalCode }}</b-col>
      </b-row>
      <b-row>
        <b-col class="header" cols="3">IRI:</b-col>
        <b-col>
          <a :href="IRIBaseUrl + address.admCode">
            {{ IRIBaseUrl }}{{ address.admCode }}
          </a>
        </b-col>
    </b-container>
    <b-container class="text-left" fluid>
      <!-- Add tooltip https://www.zakonyprolidi.cz/cs/2011-359?citace=1#prilohy !-->
      <h3>Adresa dle vyhlášky č. 359/2011 Sb.</h3>
      <b-row>
        <b-col>{{ firstRow }}</b-col>
      </b-row>
      <b-row>
        <b-col>{{ secondRow }}</b-col>
      </b-row>
      <b-row>
        <b-col>{{ thirdRow }}</b-col>
      </b-row>
      <b-row class="inline-address">
        <b-col class="header" cols="2">Adresa v řádku: </b-col>
        <b-col>{{ inline }}</b-col>
      </b-row>
    </b-container>
    <div v-if="hasCoordinates">
      <b-container class="text-left" fluid>
        <b-row>
          <b-col class="header" cols="3">Souřadnice v JTSK</b-col>
        </b-row>
        <b-row>
          <b-col>
            X: {{ address.coordinateX }}, Y: {{ address.coordinateY }}
          </b-col>
        </b-row>
        <b-row>
          <b-col class="header" cols="3">Souřadnice ve WGS84</b-col>
        </b-row>
        <b-row>
          <b-col> X: {{ coordinates[0] }}, Y: {{ coordinates[1] }} </b-col>
        </b-row>
      </b-container>
      <b-container id="map-container" fluid>
        <l-map :center="coordinates" :zoom="zoom" :minZoom="3">
          <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
          <l-marker :lat-lng="coordinates" />
        </l-map>
      </b-container>
    </div>
  </div>
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
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.hasCoordinates = false;
        return null;
      }
      return [coords.x, coords.y];
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
.address-info {
  text-align: left;
  margin-bottom: 2em;
}
.inline-address {
  margin-top: 1.2em;
  margin-bottom: 2em;
}

.header {
  font-weight: bold;
}
#map-container {
  height: 40em;
  width: 90%;
  text-align: left;
}
</style>
