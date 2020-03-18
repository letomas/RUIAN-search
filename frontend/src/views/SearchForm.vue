<template>
  <div class="search">
    <h1>Vyhledávání v RÚIAN</h1>
    <SearchForm
      v-bind:query.sync="query"
      v-bind:admCode.sync="admCode"
      v-on:search="search($event)"
      v-on:searchByAdmCode="searchByAdmCode($event)"
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
        <template v-slot:cell(identification)="{ item }">
          <span v-if="item.orientationalNumber">
            {{ item.houseNumber }}/{{ item.orientationalNumber
            }}{{ item.orientationalNumberLetter }}
          </span>
          <span v-else>
            {{ item.houseNumber }}
          </span>
        </template>
      </b-table>
    </div>
    <div v-else-if="noResult === true">
      <h3>Nebyl nalezen žádný výsledek.</h3>
    </div>
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
        { key: "buildingType", label: "Typ SO" },
        { key: "identification", label: "Identifikace" },
        { key: "districtName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      items: [],
      error: null,
      query: {
        city: "",
        district: "",
        street: "",
        houseNumber: ""
      },
      admCode: null,
      showResult: false,
      noResult: false
    };
  },
  methods: {
    search(query) {
      this.showResult = false;
      this.noResult = false;

      api
        .getFormQueryResult(query)
        .then(result => {
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

<style></style>
