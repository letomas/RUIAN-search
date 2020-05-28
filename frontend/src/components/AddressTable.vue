<template>
  <v-data-table
    :headers="headers"
    :items="items"
    no-data-text="Nebyl nalezen žádný výsledek"
    hide-default-footer
  >
    <template v-slot:item.fullOrientationalNumber="{ item }">
      {{ item.orientationalNumber }}{{ item.orientationalNumberLetter }}
    </template>
    <template v-slot:item.detail="{ item }">
      <v-btn
        :to="{ name: 'addressDetail', params: { id: item.admCode } }"
        color="indigo accent-2"
        dark
        exact
      >
        Detail
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "AddressTable",
  computed: {
    ...mapState(["items"])
  },
  data() {
    return {
      headers: [
        { value: "admCode", text: "Kód adresního místa" },
        { value: "streetName", text: "Název ulice" },
        { value: "buildingType", text: "Typ čísla domovního" },
        { value: "houseNumber", text: "Číslo domovní" },
        { value: "fullOrientationalNumber", text: "Číslo orientační" },
        { value: "district", text: "Název části obce" },
        { value: "cityName", text: "Název obce" },
        { value: "detail", text: "" }
      ]
    };
  }
};
</script>
