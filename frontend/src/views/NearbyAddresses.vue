<template>
  <v-container class="pt-0" fluid>
    <h1>Adresní místa v okolí</h1>
    <v-container class="text-left" fluid>
      Pro vyhledání adresních míst v okolí bodu zadejte GPS souřadnice bodu v
      desítkové soustavě (např. 49.7437572, 15.3386383). Nezadávejte souřadnice
      ve stupních, minutách a vteřinách. Mapa se vycentruje na vámi zadaný bod a
      pod mapou bude seznam blízkych adresních míst. Tlačítko reset vrátí mapu
      do původního stavu (zpět na vaše okolí, pokud jste povolili sdílení
      polohy).
    </v-container>

    <v-form ref="form" v-model="valid">
      <v-container fluid>
        <v-row>
          <v-col cols="12" sm="5">
            <v-text-field
              label="Souřadnice X"
              v-model="coordinateX"
              :rules="coordinateRules"
              required
            >
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="5">
            <v-text-field
              label="Souřadnice Y"
              v-model="coordinateY"
              :rules="coordinateRules"
              required
            >
            </v-text-field>
          </v-col>
          <v-col class="pt-0 text-left" align-self="center">
            <v-btn @click="onSubmit" color="indigo accent-2" dark
              >Vyhledat</v-btn
            >
          </v-col>
        </v-row>
      </v-container>
    </v-form>

    <v-container id="map-container" fluid v-if="locationAvailable">
      <l-map :center.sync="location" :zoom.sync="zoom" :minZoom="3">
        <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
        <ul v-for="item in items" :key="item.admCode">
          <li>
            <l-marker
              :lat-lng="[item.coordinatesLatLon.x, item.coordinatesLatLon.y]"
            >
              <l-popup>
                <v-container>
                  <v-row class="font-weight-bold">
                    Adresní místo {{ item.admCode }}
                  </v-row>
                  <v-row>
                    {{ buildFirstRow(item) }}
                  </v-row>
                  <v-row>
                    {{ buildSecondRow(item) }}
                  </v-row>
                  <v-row v-if="buildThirdRow(item)">
                    {{ buildThirdRow(item) }}
                  </v-row>
                  <v-row class="mt-3">
                    <v-btn
                      :to="{
                        name: 'addressDetail',
                        params: { id: item.admCode }
                      }"
                      color="indigo accent-2"
                      exact
                      dark
                      >Detail</v-btn
                    >
                  </v-row>
                </v-container>
              </l-popup>
            </l-marker>
          </li>
        </ul>
      </l-map>
    </v-container>
    <v-btn class="mb-5" @click="resetLocation" color="red lighten-1" dark>
      Reset
    </v-btn>
  </v-container>
</template>

<script>
import "leaflet/dist/leaflet.css";
import api from "../api.js";
import addressBuilder from "../addressBuilder.js";

import { mapState, mapMutations } from "vuex";
import { LMap, LTileLayer, LMarker, LPopup } from "vue2-leaflet";
import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png")
});

export default {
  name: "Search",
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LPopup
  },
  computed: {
    ...mapState(["items"]),
    ...mapState(["locationAvailable"])
  },
  data() {
    return {
      zoom: 20,
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      coordinateX: null,
      coordinateY: null,
      location: [],
      defaultLocation: [49.7437572, 15.3386383],
      distance: "0.2",
      error: null,
      valid: false,
      coordinateRules: [
        v => !!v || "Zadejde prosím souřadnici X",
        v => !isNaN(parseFloat(v)) || "Zadejte prosím platnou souřadnici"
      ]
    };
  },
  beforeRouteEnter(to, from, next) {
    let x;
    let y;

    navigator.geolocation.getCurrentPosition(
      position => {
        next(vm => {
          x = position.coords.latitude;
          y = position.coords.longitude;
          vm.location = [x, y];
          vm.updateLocationAvailable(true);
          if (position) {
            vm.getNearbyAddresses(x, y, vm.distance);
          }
        });
      },
      error => {
        next(vm => {
          vm.setError(error);
          vm.zoom = 7;
          // set coordinates to Czech republic's center if user denies permissions to his position
          vm.location = vm.defaultLocation;
          vm.updateLocationAvailable(true);
        });
      }
    );
  },
  beforeRouteLeave(to, from, next) {
    this.updateLocationAvailable(false);
    next();
  },
  methods: {
    ...mapMutations(["updateItems"]),
    ...mapMutations(["updateLocationAvailable"]),
    setError(error) {
      this.error = error;
    },
    onSubmit(evt) {
      if (!this.valid) {
        return;
      }
      evt.preventDefault();
      this.zoom = 20;
      this.location = [this.coordinateX, this.coordinateY];
      this.getNearbyAddresses(
        this.coordinateX,
        this.coordinateY,
        this.distance
      );
    },
    getNearbyAddresses(x, y, distance) {
      api
        .getNearbyAddresses(x, y, distance)
        .then(result => {
          this.updateItems(result.data.content);
        })
        .catch(error => {
          this.error = error;
        });
    },
    resetLocation() {
      let x;
      let y;
      this.$refs.form.resetValidation();
      navigator.geolocation.getCurrentPosition(
        position => {
          x = position.coords.latitude;
          y = position.coords.longitude;
          this.zoom = 20;
          this.location = [x, y];
          this.getNearbyAddresses(x, y, this.distance);
        },
        error => {
          this.setError(error);
          this.zoom = 7;
          // set coordinates to Czech republic's center if user denies permissions to his position
          this.location = this.defaultLocation;
          this.updateItems([]);
        }
      );
    },
    buildFirstRow(address) {
      return addressBuilder.build(address).firstRow;
    },
    buildSecondRow(address) {
      return addressBuilder.build(address).secondRow;
    },
    buildThirdRow(address) {
      return addressBuilder.build(address).thirdRow;
    }
  }
};
</script>

<style scoped>
#map-container {
  height: 30em;
}
</style>
