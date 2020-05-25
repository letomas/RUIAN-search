<template>
  <div class="search">
    <h1>Vyhledávání v RÚIAN</h1>
    <SearchForm
      v-on:search="search(1)"
      v-on:searchByAdmCode="searchByAdmCode(1)"
    ></SearchForm>
    <div v-if="showResult === true">
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
      <b-pagination
        v-model="page"
        :total-rows="totalElements"
        :per-page="10"
        align="center"
        @input="changePage"
      ></b-pagination>
    </div>
    <div v-else-if="noResult === true">
      <h3>Nebyl nalezen žádný výsledek.</h3>
    </div>
  </div>
</template>

<script>
import SearchForm from "../components/SearchForm";
import api from "../api.js";

import { mapState } from "vuex";

export default {
  name: "SearchTest",
  components: {
    SearchForm
  },
  computed: {
    ...mapState(["city"]),
    ...mapState(["district"]),
    ...mapState(["street"]),
    ...mapState(["houseNumber"]),
    ...mapState(["admCode"])
  },
  data() {
    return {
      fields: [
        { key: "admCode", label: "Kód ADM" },
        { key: "streetName", label: "Název ulice" },
        { key: "buildingType", label: "Typ SO" },
        { key: "identification", label: "Identifikace" },
        { key: "districtName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      items: [],
      page: 1,
      totalElements: null,
      error: null,
      showResult: false,
      noResult: false,
      isSearchingByCode: false
    };
  },
  methods: {
    search(page) {
      this.page = page;
      this.isSearchingByCode = false;
      const query = {
        city: this.city,
        district: this.district,
        street: this.street,
        houseNumber: this.houseNumber
      };
      this.showResult = false;
      this.noResult = false;

      api
        .getFormQueryResult(query, page - 1)
        .then(result => {
          this.items = result.data.content;
          this.totalElements = result.data.totalElements;
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
    searchByAdmCode(page) {
      this.page = page;
      this.isSearchingByCode = true;
      this.showResult = false;
      this.noResult = false;

      api
        .findByAdmCode(this.admCode, page - 1)
        .then(result => {
          this.items = result.data.content;
          this.totalElements = result.data.totalElements;

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
    changePage() {
      if (this.isSearchingByCode) {
        this.searchByAdmCode(this.page);
      } else {
        this.search(this.page);
      }
    }
  }
};
</script>

<style></style>
