<template>
  <div id="address">
    <b-container class="address-info" fluid>
      <b-row>
        <b-col cols="3">Kód:</b-col>
        <b-col>{{ address.admCode }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Obec:</b-col>
        <b-col>{{ address.cityName }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Část obce:</b-col>
        <b-col>{{ address.districtName }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Městská část/obvod:</b-col>
        <b-col>{{ address.boroughName }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Ulice:</b-col>
        <b-col>{{ address.streetName }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">PSČ:</b-col>
        <b-col>{{ address.postalCode }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">IRI:</b-col>
        <b-col>
          <a :href="IRIBaseUrl + address.admCode">
            {{ IRIBaseUrl }}{{ address.admCode }}
          </a>
        </b-col>
      </b-row>
    </b-container>
    <b-container class="text-left" fluid>
      <!-- Add tooltip https://www.zakonyprolidi.cz/cs/2011-359?citace=1#prilohy !-->
      <h3>Adresa dle vyhlášky č. 359/2011 Sb.</h3>
      <b-row>
        <b-col>{{ firstRow }}</b-col>
      </b-row>
      <b-row>
        <b-col>{{ secondRow }}</b-col>
      </b-row>
      <b-row>
        <b-col>{{ thirdRow }}</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import addressBuilder from "../addressBuilder.js";

export default {
  name: "Address",
  props: {
    address: {
      type: Object
    }
  },
  computed: {
    firstRow: function() {
      return addressBuilder.build(this.address).firstRow;
    },
    secondRow: function() {
      return addressBuilder.build(this.address).secondRow;
    },
    thirdRow: function() {
      return addressBuilder.build(this.address).thirdRow;
    }
  },
  data() {
    return {
      IRIBaseUrl: "https://linked.cuzk.cz/resource/ruian/adresni-misto/"
    };
  }
};
</script>

<style scoped>
.address-info {
  text-align: left;
  margin-bottom: 2em;
}
</style>
