<template>
  <div id="address_table">
    <b-table :items="items" :fields="fields">
      <template v-slot:cell(fullOrientationalNumber)="{ item }">
        <div>
          {{ item.orientationalNumber }}{{ item.orientationalNumberLetter }}
        </div>
      </template>
      <template v-slot:cell(detail)="{ item }">
        <b-button
          variant="primary"
          :to="{ name: 'addressDetail', params: { id: item.admCode } }"
        >
          Detail
        </b-button>
      </template>
    </b-table>
  </div>
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
      fields: [
        { key: "admCode", label: "Kód adresního místa" },
        { key: "streetName", label: "Název ulice" },
        { key: "buildingType", label: "Typ čísla domovního" },
        { key: "houseNumber", label: "Číslo domovní" },
        { key: "fullOrientationalNumber", label: "Číslo orientační" },
        { key: "district", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ]
    };
  }
};
</script>
