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
      <AddressTable />
    </div>
    <div v-else-if="noResult === true">
      <p v-bind:query="query">Nebyl nalezen žádný výsledek pro: {{ query }}</p>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import Searchbar from "../components/Searchbar.vue";
import AddressTable from "../components/AddressTable.vue";
import api from "../api.js";

export default {
  name: "Search",
  components: {
    Searchbar,
    AddressTable
  },
  data() {
    return {
      showResult: false,
      noResult: false,
      error: null,
      query: null,
      admCode: null
    };
  },
  methods: {
    ...mapMutations(["updateItems"]),
    search(query) {
      this.showResult = false;
      this.noResult = false;
      this.query = query;

      api
        .getQueryResult(query)
        .then(result => {
          let items = result.data.content;
          this.updateItems(items);
          if (typeof items !== "undefined" && items.length > 0) {
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
      this.admCode = admCode;

      api
        .findByAdmCode(admCode)
        .then(result => {
          let items = result.data.content;
          this.updateItems(items);
          if (typeof items !== "undefined" && items.length > 0) {
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
