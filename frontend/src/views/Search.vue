<template>
  <div class="search">
    <h1>Vyhledávání adresních míst</h1>
    <Searchbar />
    <h2>
      <BIconAlertTriangleFill variant="danger"></BIconAlertTriangleFill>
      Work in progress
    </h2>
  </div>
</template>

<script>
import Searchbar from "../components/Searchbar.vue";

export default {
  name: "Search",
  components: {
    Searchbar
  }
};
</script>
