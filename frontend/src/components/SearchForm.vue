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
          class="form-button"
          v-on:click="search(city, district, street, houseNumber)"
          variant="primary"
        >
          Vyhledat
        </b-button>
      </b-row>
    </div>
    <div id="code-container" class="container" fluid>
      <b-row>
        <h3>
          Vyhledávání adresních míst podle jejich kódu:
        </h3>
      </b-row>

      <b-row class="typehead">
        <Typeahead
          :data="codeSuggestions"
          :serializer="address => address.admCode.toString()"
          v-model="admCodeLocal"
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
  props: ["query", "admCode"],
  data() {
    return {
      citySuggestions: [],
      districtSuggestions: [],
      streetSuggestions: [],
      houseNumberSuggestions: [],
      codeSuggestions: [],
      city: this.query.city,
      district: this.query.district,
      street: this.query.street,
      houseNumber: this.query.houseNumber,
      admCodeLocal: this.admCode
    };
  },
  methods: {
    search(city, district, street, houseNumber) {
      this.$emit("search", { city, district, street, houseNumber });
    },
    searchByAdmCode() {
      this.$emit("searchByAdmCode", this.admCodeLocal);
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
        .findByAdmCode(admCode)
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
    }, 400),
    admCodeLocal: _.debounce(function(input) {
      this.getCodeSuggestions(input);
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
#code-search {
  width: 30%;
}
div.container {
  margin: 1.2em 1em 1em 0.8em;
}
#code-container {
  margin-left: 2em;
}
.form-button {
  margin-left: 1em;
}
h3 {
  font-size: 1.3em;
}
</style>
