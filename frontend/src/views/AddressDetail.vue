<template>
  <div>
    <h1>Detail adresního místa</h1>
    <Address :address="address" />
  </div>
</template>

<script>
import Address from "../components/Address.vue";
import api from "../api.js";

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
        if (result.data) {
          next(vm => vm.setData(result.data));
        } else {
          next({ name: "404" });
        }
      })
      .catch(error => {
        next(vm => vm.setError(error));
      });
  },
  methods: {
    setData(address) {
      this.address = address;
    },
    setError(error) {
      this.error = error;
    }
  }
};
</script>
