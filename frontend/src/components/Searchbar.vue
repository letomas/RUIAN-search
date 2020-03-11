<template>
  <div class="search-bar">
    <b-container fluid>
      <b-row>
        <label for="name-search">Vyhledávání adresních míst podle názvu:</label>
      </b-row>

      <b-row class="typehead">
        <Typeahead
          :data="suggestions"
          :serializer="address => buildAddress(address)"
          placeholder="Zadejte adresní místo"
          v-model="query"
          id="name-search"
          @hit="redirectToDetail($event)"
        >
          <template slot="append">
            <b-button variant="primary" v-on:click="search(query)">
              <BIconSearch></BIconSearch>
            </b-button>
          </template>
        </Typeahead>
      </b-row>

      <b-row>
        <label for="code-search">
          Vyhledávání adresních míst podle jejich kódu:
        </label>
      </b-row>

      <b-row class="typehead">
        <Typeahead
          :data="codeSuggestions"
          :serializer="address => address.admCode.toString()"
          placeholder="Zadejte kód ADM"
          v-model="admCode"
          id="code-search"
          @hit="redirectToDetail($event)"
        >
          <template slot="append">
            <b-button variant="primary" v-on:click="searchByAdmCode(admCode)">
              <BIconSearch></BIconSearch>
            </b-button>
          </template>
        </Typeahead>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Typeahead from "vue-bootstrap-typeahead";
import _ from "underscore";

import api from "../api.js";
import addressBuilder from "../addressBuilder.js";

export default {
  name: "Searchbar",
  components: {
    Typeahead
  },
  props: {
    query: {
      type: String
    },
    admCode: {
      type: Number
    }
  },
  data() {
    return {
      suggestions: [],
      codeSuggestions: []
    };
  },
  methods: {
    search(query) {
      this.$emit("search", query);
    },
    searchByAdmCode(admCode) {
      this.$emit("searchByAdmCode", admCode);
    },
    getSuggestions(query) {
      api
        .getQueryResult(query)
        .then(result => {
          this.suggestions = result.data.content;
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
    },
    buildAddress(address) {
      return addressBuilder.buildInline(address);
    }
  },
  watch: {
    query: _.debounce(function(input) {
      this.getSuggestions(input);
    }, 400),
    admCode: _.debounce(function(input) {
      this.getCodeSuggestions(input);
    }, 400)
  }
};
</script>

<style scoped>
.typehead {
  margin-bottom: 1rem;
}
#name-search {
  width: 40%;
}
#code-search {
  width: 15%;
}
</style>
