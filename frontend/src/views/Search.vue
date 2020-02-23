<template>
  <div class="search">
    <h1>Vyhledávání adresních míst</h1>
    <b-container fluid>
      <Searchbar />
    </b-container>
    <b-table :items="items" :fields="fields">
      <template v-slot:cell(detail)>
        <b-button variant="primary">Detail</b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
import Searchbar from "../components/Searchbar.vue";
import api from "../api.js";

export default {
  name: "Search",
  components: {
    Searchbar
  },
  data() {
    return {
      fields: [
        { key: "admCode", label: "Kód ADM" },
        { key: "streetName", label: "Název ulice" },
        { key: "boroughName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      items: []
    };
  },
  mounted() {
    api
      .getQueryResult("praha")
      .then(result => {
        this.$log.debug("Query result is:" + result.data);
        this.items = result.data.content;
      })
      .catch(error => {
        this.$log.debug(error);
      });
  }
};
</script>
