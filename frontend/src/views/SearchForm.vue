<template>
  <div class="search">
    <h1>Vyhledávání v RÚIAN</h1>
    <SearchForm
      v-bind:query.sync="query"
      v-on:search="search($event)"
    ></SearchForm>
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
import SearchForm from "../components/SearchForm";
import api from "../api.js";

export default {
  name: "SearchTest",
  components: {
    SearchForm
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
      query: {
        city: "",
        borough: "",
        street: "",
        houseNumber: ""
      },
      admCode: null
    };
  },
  methods: {
    search(query) {
      api
        .getFormQueryResult(query)
        .then(result => {
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

<style></style>
