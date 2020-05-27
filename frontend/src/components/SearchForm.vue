<template>
  <v-container class="text-left">
    <v-container>
      <v-row>
        <v-col>
          <v-combobox
            label="Obec"
            no-data-text="Žádný výsledek"
            placeholder=" "
            append-icon=""
            :items="citySuggestions"
            :search-input.sync="searchCity"
            clearable
          ></v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Část obce"
            no-data-text="Žádný výsledek"
            placeholder=" "
            append-icon=""
            :items="districtSuggestions"
            :search-input.sync="searchDistrict"
            clearable
          ></v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Ulice"
            no-data-text="Žádný výsledek"
            placeholder=" "
            append-icon=""
            :items="streetSuggestions"
            :search-input.sync="searchStreet"
            clearable
          ></v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Číslo domovní/orientační"
            no-data-text="Žádný výsledek"
            placeholder=" "
            append-icon=""
            :items="houseNumberSuggestions"
            :search-input.sync="searchHouseNumber"
            clearable
          >
          </v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn
            @click="search(city, district, street, houseNumber)"
            color="indigo accent-2"
            dark
          >
            Vyhledat
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col>
          <h3>
            Vyhledávání adresních míst podle jejich kódu:
          </h3>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Kód adresního místa"
            no-data-text="Žádný výsledek"
            placeholder=" "
            append-icon=""
            :items="codeSuggestions"
            :search-input.sync="searchCode"
            item-text="admCode"
            @input="redirectToDetail($event)"
            clearable
          ></v-combobox>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import _ from "underscore";

import api from "../api.js";

export default {
  name: "SearchForm",
  computed: {
    city: {
      get() {
        return this.$store.state.city;
      },
      set(val) {
        this.$store.commit("updateCity", val);
      }
    },
    district: {
      get() {
        return this.$store.state.district;
      },
      set(val) {
        this.$store.commit("updateDistrict", val);
      }
    },
    street: {
      get() {
        return this.$store.state.street;
      },
      set(val) {
        this.$store.commit("updateStreet", val);
      }
    },
    houseNumber: {
      get() {
        return this.$store.state.houseNumber;
      },
      set(val) {
        this.$store.commit("updateHouseNumber", val);
      }
    },
    admCode: {
      get() {
        return this.$store.state.admCode;
      },
      set(val) {
        this.$store.commit("updateAdmCode", val);
      }
    }
  },
  data() {
    return {
      citySuggestions: [],
      districtSuggestions: [],
      streetSuggestions: [],
      houseNumberSuggestions: [],
      codeSuggestions: [],
      searchCity: "",
      searchDistrict: "",
      searchStreet: "",
      searchHouseNumber: "",
      searchCode: ""
    };
  },
  methods: {
    search() {
      this.$emit("search");
    },
    searchByAdmCode() {
      this.$emit("searchByAdmCode");
    },
    getCitySuggestions: _.debounce(function(city) {
      api
        .getCitySuggestions(city)
        .then(result => {
          this.citySuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }, 250),
    getDistrictSuggestions: _.debounce(function(city, district) {
      api
        .getDistrictSuggestions(city, district)
        .then(result => {
          this.districtSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }, 250),
    getStreetSuggestions: _.debounce(function(city, district, street) {
      api
        .getStreetSuggestions(city, district, street)
        .then(result => {
          this.streetSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }, 250),
    getHouseNumberSuggestions: _.debounce(function(
      city,
      district,
      street,
      houseNumber
    ) {
      api
        .getHouseNumberSuggestions(city, district, street, houseNumber)
        .then(result => {
          this.houseNumberSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    250),
    getCodeSuggestions: _.debounce(function(admCode) {
      api
        .findByAdmCode(admCode, 0)
        .then(result => {
          this.codeSuggestions = result.data.content;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    }, 250),
    redirectToDetail(address) {
      this.$log.debug(address);
      this.$router.push({
        name: "addressDetail",
        params: { id: address.admCode }
      });
    }
  },
  watch: {
    searchCity(value) {
      this.city = value ? value : "";
      this.getCitySuggestions(value);
    },
    searchDistrict(value) {
      this.district = value ? value : "";
      this.getDistrictSuggestions(this.city, value);
    },
    searchStreet(value) {
      this.street = value ? value : "";
      this.getStreetSuggestions(this.city, this.district, value);
    },
    searchHouseNumber(value) {
      this.houseNumber = value ? value : "";

      this.getCitySuggestions(this.city, this.district, this.street, value);
    },
    searchCode(value) {
      this.admCode = value ? value : "";
      this.getCodeSuggestions(value);
    }
  }
};
</script>

<style scoped>
h3 {
  font-size: 1.3em;
}
</style>
