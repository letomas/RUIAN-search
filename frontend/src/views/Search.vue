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
    <div v-if="showResult === true">
      <h3 v-bind:query="query">Výsledek vyhledávání pro: {{ query }}</h3>
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
    <div v-else-if="noResult === true">
      <p v-bind:query="query">Nebyl nalezen žádný výsledek pro: {{ query }}</p>
    </div>
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
        { key: "buildingType", label: "Typ SO" },
        { key: "houseNumber", label: "Identifikace" },
        { key: "boroughName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      items: [],
      showResult: false,
      noResult: false,
      error: null,
      query: null,
      admCode: null
    };
  },
  methods: {
    search(query) {
      this.showResult = false;
      this.noResult = false;

      api
        .getQueryResult(query)
        .then(result => {
          this.$log.debug("Query result is:" + result.data);
          this.items = result.data.content;
          if (typeof this.items !== "undefined" && this.items.length > 0) {
            this.showResult = true;
          } else {
            this.noResult = true;
          }
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    searchByAdmCode(admCode) {
      this.showResult = false;
      this.noResult = false;

      api
        .findByAdmCode(admCode)
        .then(result => {
          this.$log.debug("Query result is:" + result.data);
          this.items = result.data.content;
          if (typeof this.items !== "undefined" && this.items.length > 0) {
            this.showResult = true;
          } else {
            this.noResult = true;
          }
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }
  }
};
</script>
