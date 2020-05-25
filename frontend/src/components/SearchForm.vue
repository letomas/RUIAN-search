<template>
  <div class="search-form">
    <div class="container" fluid>
      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label>Obec:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="citySuggestions"
            v-model="city"
            v-bind:minMatchingChars="1"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label>Část obce:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="districtSuggestions"
            v-model="district"
            v-bind:minMatchingChars="1"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label>Ulice:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="streetSuggestions"
            v-model="street"
            v-bind:minMatchingChars="1"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label>Číslo domovní:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="houseNumberSuggestions"
            v-model="houseNumber"
            v-bind:minMatchingChars="1"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row>
        <b-button
          class="ml-3"
          v-on:click="search(city, district, street, houseNumber)"
          variant="primary"
        >
          Vyhledat
        </b-button>
      </b-row>
    </div>
    <div id="code-container" class="container ml-4" fluid>
      <b-row>
        <h3>
          Vyhledávání adresních míst podle jejich kódu:
        </h3>
      </b-row>

      <b-row class="typehead">
        <Typeahead
          :data="codeSuggestions"
          :serializer="address => address.admCode.toString()"
          v-bind:minMatchingChars="1"
          v-model="admCode"
          id="code-search"
          @hit="redirectToDetail($event)"
        >
          <template slot="append">
            <b-button variant="primary" v-on:click="searchByAdmCode">
              <BIconSearch></BIconSearch>
            </b-button>
          </template>
        </Typeahead>
      </b-row>
    </div>
  </div>
</template>

<script>
import Typeahead from "vue-bootstrap-typeahead";
import _ from "underscore";

import api from "../api.js";

export default {
  name: "SearchForm",
  components: {
    Typeahead
  },
  computed: {
    city: {
      get() {
        return this.$store.state.city;
      },
      set: _.debounce(function(val) {
        this.$store.commit("updateCity", val);
        this.getCitySuggestions(val);
      }, 400)
    },
    district: {
      get() {
        return this.$store.state.district;
      },
      set: _.debounce(function(val) {
        this.$store.commit("updateDistrict", val);
        this.getDistrictSuggestions(this.city, val);
      }, 400)
    },
    street: {
      get() {
        return this.$store.state.street;
      },
      set: _.debounce(function(val) {
        this.$store.commit("updateStreet", val);
        this.getStreetSuggestions(this.city, this.district, val);
      }, 400)
    },
    houseNumber: {
      get() {
        return this.$store.state.houseNumber;
      },
      set: _.debounce(function(val) {
        this.$store.commit("updateHouseNumber", val);
        this.getHouseNumberSuggestions(
          this.city,
          this.district,
          this.street,
          val
        );
      }, 400)
    },
    admCode: {
      get() {
        return this.$store.state.admCode;
      },
      set: _.debounce(function(val) {
        this.$store.commit("updateAdmCode", val);
        this.getCodeSuggestions(val);
      }, 400)
    }
  },
  data() {
    return {
      citySuggestions: [],
      districtSuggestions: [],
      streetSuggestions: [],
      houseNumberSuggestions: [],
      codeSuggestions: []
    };
  },
  methods: {
    search() {
      this.$emit("search");
    },
    searchByAdmCode() {
      this.$emit("searchByAdmCode");
    },
    getCitySuggestions(city) {
      api
        .getCitySuggestions(city)
        .then(result => {
          this.citySuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    getDistrictSuggestions(city, district) {
      api
        .getDistrictSuggestions(city, district)
        .then(result => {
          this.districtSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    getStreetSuggestions(city, district, street) {
      api
        .getStreetSuggestions(city, district, street)
        .then(result => {
          this.streetSuggestions = result.data;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    getHouseNumberSuggestions(city, district, street, houseNumber) {
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
    getCodeSuggestions(admCode) {
      api
        .findByAdmCode(admCode, 0)
        .then(result => {
          this.codeSuggestions = result.data.content;
        })
        .catch(error => {
          this.error = error.toString();
          this.$log.debug(error);
        });
    },
    redirectToDetail(address) {
      this.$router.push({
        name: "addressDetail",
        params: { id: address.admCode }
      });
    }
  }
};
</script>

<style scoped>
.typehead {
  margin-bottom: 1rem;
}
.input {
  width: 50%;
}
#code-search {
  width: 30%;
}
div.container {
  margin: 1.2em 1em 1em 0.8em;
}
h3 {
  font-size: 1.3em;
}
</style>
