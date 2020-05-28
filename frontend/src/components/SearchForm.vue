<template>
  <v-container class="text-left px-0">
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
            ref="districtInput"
            label="Část obce"
            placeholder=" "
            append-icon=""
            :loading="loadingDistrict"
            :items="districtSuggestions"
            :search-input.sync="searchDistrict"
            @focus="showDistrictsInCity"
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
            ref="streetInput"
            label="Ulice"
            placeholder=" "
            append-icon=""
            :loading="loadingStreet"
            :items="streetSuggestions"
            :search-input.sync="searchStreet"
            @focus="showStreetsInCity"
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
            ref="houseNumberInput"
            label="Číslo domovní/orientační"
            placeholder=" "
            append-icon=""
            :loading="loadingHouseNumber"
            :items="houseNumberSuggestions"
            :search-input.sync="searchHouseNumber"
            @focus="showHouseNumbers"
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
            no-data-text="Žádný výsledek"
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
import { mapState, mapMutations } from "vuex";

import api from "../api.js";

export default {
  name: "SearchForm",
  computed: {
    ...mapState(["city"]),
    ...mapState(["district"]),
    ...mapState(["street"]),
    ...mapState(["houseNumber"]),
    ...mapState(["admCode"])
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
    ...mapMutations(["updateCity"]),
    ...mapMutations(["updateDistrict"]),
    ...mapMutations(["updateStreet"]),
    ...mapMutations(["updateHouseNumber"]),
    ...mapMutations(["updateAdmCode"]),
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
    showDistrictsInCity() {
      if (!this.city) {
        return;
      }

      this.getDistrictSuggestions(this.city, "");
      this.$refs.districtInput.isMenuActive = true;
    },
    showStreetsInCity() {
      if (!this.city) {
        return;
      }

      this.getStreetSuggestions(this.city, this.district, this.street);
      this.$refs.streetInput.isMenuActive = true;
    },
    showHouseNumbers() {
      if (!this.city || !this.street) {
        return;
      }

      this.getHouseNumberSuggestions(this.city, this.district, this.street, "");
      this.$refs.houseNumberInput.isMenuActive = true;
    },
    redirectToDetail(admCode) {
      this.$router.push({
        name: "addressDetail",
        params: { id: admCode }
      });
    }
  },
  watch: {
    searchCity(value) {
      const city = value ? value : "";
      this.updateCity(city);
      this.districtSuggestions = [];
      this.streetSuggestions = [];
      this.houseNumberSuggestions = [];

      this.getCitySuggestions(this.city);
    },
    searchDistrict(value) {
      const district = value ? value : "";
      this.updateDistrict(district);
      this.streetSuggestions = [];
      this.houseNumberSuggestions = [];

      this.getDistrictSuggestions(this.city, this.district);
    },
    searchStreet(value) {
      const street = value ? value : "";
      this.updateStreet(street);
      this.houseNumberSuggestions = [];

      this.getStreetSuggestions(this.city, this.district, this.street);
    },
    searchHouseNumber(value) {
      const houseNumber = value ? value : "";
      this.updateHouseNumber(houseNumber);

      this.getHouseNumberSuggestions(
        this.city,
        this.district,
        this.street,
        this.houseNumber
      );
    },
    searchCode(value) {
      const admCode = value ? value : "";
      this.updateAdmCode(admCode);

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
