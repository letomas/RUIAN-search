<template>
  <div id="address_table">
    <b-table :items="items" :fields="fields">
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
        { key: "admCode", label: "Kód ADM" },
        { key: "streetName", label: "Název ulice" },
        { key: "buildingType", label: "Typ SO" },
        { key: "houseNumber", label: "Identifikace" },
        { key: "boroughName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ]
    };
  }
};
</script>
