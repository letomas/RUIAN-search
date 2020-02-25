<template>
  <div class="search">
    <h1>Vyhledávání adresních míst</h1>
    <b-container fluid>
      <Searchbar
        v-bind:query="query"
        v-bind:admCode="admCode"
        v-on:search="search($event)"
        v-on:searchByAdmCode="searchByAdmCode($event)"
      />
    </b-container>
    <b-table :items="items" :fields="fields">
      <template v-slot:cell(detail)="{ item }">
        <b-button
          variant="primary"
          :to="{ name: 'addressDetail', params: { id: item.admCode } }"
        >
          Detail
        </b-button>
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
      items: [],
      error: null,
      query: null,
      admCode: null
    };
  },
  methods: {
    search(query) {
      api
        .getQueryResult(query)
        .then(result => {
          this.$log.debug("Query result is:" + result.data);
          this.items = result.data.content;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    searchByAdmCode(admCode) {
      api
        .findByAdmCode(admCode)
        .then(result => {
          this.$log.debug("Query result is:" + result.data);
          this.items = result.data.content;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }
  }
};
</script>
