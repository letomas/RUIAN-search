<template>
  <div class="search">
    <h1>Vyhledávání adresních míst</h1>
    <Searchbar />
    <b-table :items="items" :fields="fields">
      <template v-slot:cell(detail)>
        <b-button variant="primary">Detail</b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
import Searchbar from "../components/Searchbar.vue";

export default {
  name: "Search",
  components: {
    Searchbar
  },
  data() {
    return {
      fields: [
        { key: "admCode", label: "Kód ADM" },
        { key: "streetName", label: "Název ulice" },
        { key: "boroughName", label: "Název části obce" },
        { key: "cityName", label: "Název obce" },
        { key: "detail", label: "" }
      ],
      items: [
        {
          admCode: 21690278,
          cityName: "Praha",
          boroughName: "Hradčany",
          streetName: "Hrad I. nádvoří"
        },
        {
          admCode: 21690294,
          cityName: "Praha",
          boroughName: "Hradčany",
          streetName: "Jiřská"
        }
      ]
    };
  }
};
</script>
