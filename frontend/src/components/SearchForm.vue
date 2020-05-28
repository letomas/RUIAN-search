<template>
  <v-container class="text-left">
    <v-container>
      <v-row>
        <v-col>
          <v-combobox
            label="Obec"
            placeholder=" "
            append-icon=""
            :loading="loadingCity"
            :items="citySuggestions"
            :search-input.sync="searchCity"
            clearable
          >
            <template v-if="this.searchCity" v-slot:no-data>
              <div>Žádný výsledek</div>
            </template>
          </v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Část obce"
            placeholder=" "
            append-icon=""
            :loading="loadingDistrict"
            :items="districtSuggestions"
            :search-input.sync="searchDistrict"
            clearable
          >
            <template v-if="this.searchDistrict" v-slot:no-data>
              <div>Žádný výsledek</div>
            </template>
          </v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Ulice"
            placeholder=" "
            append-icon=""
            :loading="loadingStreet"
            :items="streetSuggestions"
            :search-input.sync="searchStreet"
            clearable
          >
            <template v-if="this.searchStreet" v-slot:no-data>
              <div>Žádný výsledek</div>
            </template>
          </v-combobox>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-combobox
            label="Číslo domovní/orientační"
            placeholder=" "
            append-icon=""
            :loading="loadingHouseNumber"
            :items="houseNumberSuggestions"
            :search-input.sync="searchHouseNumber"
            clearable
          >
            <template v-if="this.searchHouseNumber" v-slot:no-data>
              <div>Žádný výsledek</div>
            </template>
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
          <v-autocomplete
            label="Kód adresního místa"
            ref="codeAutocomplete"
            placeholder=" "
            append-icon=""
            :loading="loadingCode"
            append-outer-icon="search"
            :items="codeSuggestions"
            :search-input.sync="searchCode"
            item-text="admCode"
            @input="redirectToDetail($event)"
            @click:append-outer="searchByAdmCode"
            clearable
          >
          </v-autocomplete>
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
      searchCode: "",
      loadingCity: false,
      loadingDistrict: false,
      loadingStreet: false,
      loadingHouseNumber: false,
      loadingCode: false
    };
  },
  methods: {
    search() {
      this.$emit("search");
    },
    searchByAdmCode() {
      this.$refs.codeAutocomplete.isMenuActive = false;
      this.$emit("searchByAdmCode");
    },
    getCitySuggestions: _.debounce(function(city) {
      this.loadingCity = true;
      api
        .getCitySuggestions(city)
        .then(result => {
          this.citySuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        })
        .finally(() => {
          this.loadingCity = false;
        });
    }, 250),
    getDistrictSuggestions: _.debounce(function(city, district) {
      this.loadingDistrict = true;

      api
        .getDistrictSuggestions(city, district)
        .then(result => {
          this.districtSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        })
        .finally(() => {
          this.loadingDistrict = false;
        });
    }, 250),
    getStreetSuggestions: _.debounce(function(city, district, street) {
      this.loadingStreet = true;

      api
        .getStreetSuggestions(city, district, street)
        .then(result => {
          this.streetSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        })
        .finally(() => {
          this.loadingStreet = false;
        });
    }, 250),
    getHouseNumberSuggestions: _.debounce(function(
      city,
      district,
      street,
      houseNumber
    ) {
      this.loadingHouseNumber = true;

      api
        .getHouseNumberSuggestions(city, district, street, houseNumber)
        .then(result => {
          this.houseNumberSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        })
        .finally(() => {
          this.loadingHouseNumber = false;
        });
    },
    250),
    getCodeSuggestions: _.debounce(function(admCode) {
      this.loadingCode = true;

      api
        .findByAdmCode(admCode, 0)
        .then(result => {
          this.codeSuggestions = result.data.content;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        })
        .finally(() => {
          this.loadingCode = false;
        });
    }, 250),
    redirectToDetail(admCode) {
      this.$router.push({
        name: "addressDetail",
        params: { id: admCode }
      });
    }
  },
  watch: {
    searchCity(value) {
      this.city = value ? value : "";
      this.getCitySuggestions(this.city);
    },
    searchDistrict(value) {
      this.district = value ? value : "";
      this.getDistrictSuggestions(this.city, this.district);
    },
    searchStreet(value) {
      this.street = value ? value : "";
      this.getStreetSuggestions(this.city, this.district, this.street);
    },
    searchHouseNumber(value) {
      this.houseNumber = value ? value : "";

      this.getCitySuggestions(
        this.city,
        this.district,
        this.street,
        this.houseNumber
      );
    },
    searchCode(value) {
      this.admCode = value ? value : "";
      this.getCodeSuggestions(this.admCode);
    }
  }
};
</script>

<style scoped>
h3 {
  font-size: 1.3em;
}
</style>
