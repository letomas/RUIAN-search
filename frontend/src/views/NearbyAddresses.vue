<template>
  <div class="nearbyAddresses">
    <h1>Adresní místa v okolí</h1>
    <div id="form-label">
      Pro vyhledání adresních míst zadejte souřadnice bodu v zobrazení WGS84.
      Mapa se vycentruje na vámi zadaný bod a pod mapou bude seznam blízkych
      adresních míst.
    </div>
    <b-container fluid v-if="locationAvailable">
      <b-form @submit="onSubmit" inline>
        <b-form-group label="X:" label-for="input-coord-x">
          <b-form-input
            id="input-coord-x"
            v-model="coordinateX"
            required
            placeholder="49.7437506"
          ></b-form-input>
        </b-form-group>
        <b-form-group label="Y:" label-for="input-coord-y">
          <b-form-input
            id="input-coord-y"
            v-model="coordinateY"
            required
            placeholder="15.3386478"
          ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary" style="float:left"
          >Vyhledat</b-button
        >
      </b-form>
    </b-container>
    <b-container id="map-container" fluid v-if="locationAvailable">
      <l-map :center="location" :zoom="zoom" :minZoom="3">
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
    </b-container>
    <b-button id="reset-button" type="warning" @click="resetLocation">
      Reset
    </b-button>
    <AddressTable />
  </div>
</template>

<script>
import "leaflet/dist/leaflet.css";
import AddressTable from "../components/AddressTable.vue";
import api from "../api.js";

import { mapMutations } from "vuex";
import { mapState } from "vuex";
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
    ...mapState(["location"]),
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
      distance: "0.2"
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
          vm.updateLocation([x, y]);
          vm.getNearbyAddresses(x, y, vm.distance);
        });
      },
      error => {
        next(vm => vm.setError(error));
      }
    );
  },
  methods: {
    ...mapMutations(["updateItems"]),
    ...mapMutations(["updateLocation"]),
    ...mapMutations(["updateLocationAvailable"]),
    setError(error) {
      this.error = error;
    },
    onSubmit(evt) {
      evt.preventDefault();
      this.updateLocation([this.coordinateX, this.coordinateY]);
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
      navigator.geolocation.getCurrentPosition(position => {
        x = position.coords.latitude;
        y = position.coords.longitude;
        this.updateLocation([x, y]);
        this.getNearbyAddresses(x, y, this.distance);
      });
    }
  }
};
</script>

<style scoped>
input {
  margin: 0 0.8em 0 0.2em;
}
#reset-button {
  margin-bottom: 0.5em;
}
#form-label {
  margin-left: 0.4em;
  font-size: 1.1em;
  text-align: left;
}
#map-container {
  margin-top: 2em;
  margin-bottom: 0.8em;
  height: 30em;
  width: 70%;
}
</style>
