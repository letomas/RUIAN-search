<template>
  <div class="search-form">
    <div class="container" fluid>
      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label for="input">Obec:</label>
        </b-col>
        <b-col>
          <Typeahead :data="citySuggestions" v-model="city" class="input">
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label for="input">Část obce:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="districtSuggestions"
            v-model="district"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label for="input">Ulice:</label>
        </b-col>
        <b-col>
          <Typeahead :data="streetSuggestions" v-model="street" class="input">
          </Typeahead>
        </b-col>
      </b-row>

      <b-row class="typehead">
        <b-col cols="2" align="left">
          <label for="input">Číslo domovní:</label>
        </b-col>
        <b-col>
          <Typeahead
            :data="houseNumberSuggestions"
            v-model="houseNumber"
            minMatchingChars="1"
            class="input"
          >
          </Typeahead>
        </b-col>
      </b-row>

      <b-row>
        <b-button
          v-on:click="search(city, district, street, houseNumber)"
          variant="primary"
        >
          Vyhledat
        </b-button>
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
  props: ["query"],
  data() {
    return {
      citySuggestions: [],
      districtSuggestions: [],
      streetSuggestions: [],
      houseNumberSuggestions: [],
      city: this.query.city,
      district: this.query.district,
      street: this.query.street,
      houseNumber: this.query.houseNumber
    };
  },
  methods: {
    search(city, district, street, houseNumber) {
      this.$emit("search", { city, district, street, houseNumber });
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
    }
  },
  watch: {
    city: _.debounce(function(input) {
      this.getCitySuggestions(input);
    }, 400),
    district: _.debounce(function(input) {
      this.getDistrictSuggestions(this.city, input);
    }, 400),
    street: _.debounce(function(input) {
      this.getStreetSuggestions(this.city, this.district, input);
    }, 400),
    houseNumber: _.debounce(function(input) {
      this.getHouseNumberSuggestions(
        this.city,
        this.district,
        this.street,
        input
      );
    }, 400)
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
div.container {
  margin: 1.2em 1em 1em 0.8em;
}
button {
  margin-left: 1em;
}
</style>
