<template>
  <div>
    <h1>Detail adresního místa</h1>
    <Address v-bind:address="address" v-bind:coordinates="coordinates" />
  </div>
</template>

<script>
import Address from "../components/Address.vue";
import api from "../api.js";
import converter from "../jtskConverter.js";

export default {
  name: "Search",
  components: {
    Address
  },
  data() {
    return {
      address: null,
      coordinates: null,
      error: null
    };
  },
  beforeRouteEnter(to, from, next) {
    api
      .getDetail(to.params.id)
      .then(result => {
        next(vm => vm.setData(result.data));
      })
      .catch(error => {
        next(vm => vm.setError(error));
      });
  },
  methods: {
    setData(address) {
      this.address = address;
      this.coordinates = this.getCoordinates(address);
    },
    setError(error) {
      this.error = error;
    },
    getCoordinates(address) {
      const conversion = converter.jtsk_to_wgs(
        address.coordinateX,
        address.coordinateY
      );
      return [conversion.lat, conversion.lon];
    }
  }
};
</script>
