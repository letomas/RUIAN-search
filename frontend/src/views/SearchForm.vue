<template>
  <div class="search">
    <h1>Vyhledávání adresních míst v RÚIAN</h1>
    <SearchForm
      v-on:search="search(1)"
      v-on:searchByAdmCode="searchByAdmCode(1)"
    ></SearchForm>
    <div v-if="showResult === true">
      <AddressTable />
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
import AddressTable from "../components/AddressTable.vue";
import api from "../api.js";

import { mapState, mapMutations } from "vuex";

export default {
  name: "SearchTest",
  components: {
    SearchForm,
    AddressTable
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.resetQueryState();
    });
  },
  computed: {
    ...mapState(["items"]),
    ...mapState(["city"]),
    ...mapState(["district"]),
    ...mapState(["street"]),
    ...mapState(["houseNumber"]),
    ...mapState(["admCode"])
  },
  data() {
    return {
      fields: [
        { key: "admCode", label: "Kód adresního místa" },
        { key: "streetName", label: "Název ulice" },
        { key: "buildingType", label: "Typ čísla domovního" },
        { key: "houseNumber", label: "Číslo domovní" },
        { key: "fullOrientationalNumber", label: "Číslo orientační" },
        { key: "districtName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      page: 1,
      totalElements: null,
      error: null,
      showResult: false,
      noResult: false,
      isSearchingByCode: false
    };
  },
  methods: {
    ...mapMutations(["updateItems"]),
    ...mapMutations(["resetQueryState"]),
    search(page) {
      const query = {
        city: this.city,
        district: this.district,
        street: this.street,
        houseNumber: this.houseNumber
      };
      this.page = page;
      this.isSearchingByCode = false;
      this.showResult = false;
      this.noResult = false;

      api
        .getFormQueryResult(query, page - 1)
        .then(result => {
          this.updateItems(result.data.content);
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
          this.updateItems(result.data.content);
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
