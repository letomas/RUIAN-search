<template>
  <div class="search-form">
    <div class="container" fluid>
      <b-form>
        <b-form-group
          label="Ulice:"
          label-for="street-input"
          label-cols="2"
          label-align-sm="left"
        >
          <b-form-input
            id="street-input"
            v-model="street"
            size="sm"
            trim
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label="Číslo domovní:"
          label-for="houseNumber-input"
          label-cols="2"
          label-align-sm="left"
        >
          <b-form-input
            id="houseNumber-input"
            v-model="houseNumber"
            size="sm"
            trim
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label="Město:"
          label-for="city-input"
          label-cols="2"
          label-align-sm="left"
        >
          <b-form-input
            id="city-input"
            v-model="city"
            size="sm"
            trim
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label="Městská část/obvod:"
          label-for="borough-input"
          label-cols="2"
          label-align-sm="left"
        >
          <b-form-input
            id="borough-input"
            v-model="borough"
            size="sm"
            trim
          ></b-form-input>
        </b-form-group>
        <b-row>
          <b-button
            v-on:click="search(street, houseNumber, city, borough)"
            variant="primary"
          >
            Vyhledat
          </b-button>
        </b-row>
      </b-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchForm",
  props: {
    street: {
      type: String,
      default: ""
    },
    houseNumber: {
      type: String,
      default: ""
    },
    city: {
      type: String,
      default: ""
    },
    borough: {
      type: String,
      default: ""
    }
  },
  methods: {
    search(street, houseNumber, city, borough) {
      this.$emit("search", { street, houseNumber, city, borough });
    }
  }
};
</script>

<style scoped>
input {
  width: 40%;
}
div.container {
  margin: 1.2em 1em 1em 0.8em;
}
button {
  margin-left: 1em;
}
</style>
