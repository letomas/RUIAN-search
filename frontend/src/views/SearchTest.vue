<template>
  <div class="search">
    <h1>Vyhledávání v RÚIAN</h1>
    <SearchForm v-bind:query="query" v-on:search="search($event)"></SearchForm>
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
      query: null,
      admCode: null
    };
  },
  methods: {
    search(query) {
      this.$log.debug(query);
    }
  }
};
</script>

<style></style>
