<template>
  <v-container class="pt-0" fluid>
    <h1>Adresní místa v okolí</h1>
    <div class="text-left">
      Pro vyhledání adresních míst zadejte souřadnice bodu v zobrazení WGS84.
      Mapa se vycentruje na vámi zadaný bod a pod mapou bude seznam blízkych
      adresních míst.
    </div>

    <v-form>
      <v-container fluid>
        <v-row>
          <v-col cols="12" sm="5">
            <v-text-field label="Souřadnice X" v-model="coordinateX" required>
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="5">
            <v-text-field label="Souřadnice Y" v-model="coordinateY" required>
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
        <ul v-for="item in items" v-bind:key="item.admCode">
          <li>
            <l-marker
              v-bind:lat-lng="[
                item.coordinatesLatLon.x,
                item.coordinatesLatLon.y
              ]"
            />
          </li>
        </ul>
      </l-map>
    </v-container>
    <v-btn @click="resetLocation" color="red lighten-1" dark>
      Reset
    </v-btn>

    <AddressTable />
  </v-container>
</template>

<script>
import "leaflet/dist/leaflet.css";
import AddressTable from "../components/AddressTable.vue";
import api from "../api.js";

import { mapState, mapMutations } from "vuex";
import { LMap, LTileLayer, LMarker } from "vue2-leaflet";
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
    AddressTable,
    LMap,
    LTileLayer,
    LMarker
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
      distance: "0.2",
      error: null
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
          // set coordinates to Czech republic's center if user denies permissions to his position
          x = 49.7437572;
          y = 15.3386383;
          vm.zoom = 7;
          vm.location = [x, y];
          vm.updateLocationAvailable(true);
        });
      }
    );
  },
  methods: {
    ...mapMutations(["updateItems"]),
    ...mapMutations(["updateLocationAvailable"]),
    setError(error) {
      this.error = error;
    },
    onSubmit(evt) {
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
          // set coordinates to Czech republic's center if user denies permissions to his position
          x = 49.7437572;
          y = 15.3386383;
          this.zoom = 7;
          this.location = [x, y];
          this.updateItems([]);
        }
      );
    }
  }
};
</script>

<style scoped>
#map-container {
  height: 30em;
}
</style>
