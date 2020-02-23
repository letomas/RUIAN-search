<template>
  <div id="address">
    <b-container class="address-info" fluid>
      <b-row>
        <b-col cols="3">Kód:</b-col>
        <b-col>21692742</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Obec:</b-col>
        <b-col>Praha</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Část obce:</b-col>
        <b-col>Josefov</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Městská část/obvod</b-col>
        <b-col>Praha 1</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">Ulice:</b-col>
        <b-col>Kaprova</b-col>
      </b-row>
      <b-row>
        <b-col cols="3">PSČ</b-col>
        <b-col>11000</b-col>
      </b-row>
    </b-container>
    <b-container class="text-left" fluid>
      <h3>Adresa dle vyhlášky č. 359/2011 Sb.</h3>
      <b-row>
        <b-col>Kaprova 1/1</b-col>
      </b-row>
      <b-row>
        <b-col>Josefov</b-col>
      </b-row>
      <b-row>
        <b-col>11000 Praha 1</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "Address"
};
</script>

<style scoped>
.address-info {
  text-align: left;
  margin-bottom: 2em;
}
</style>
