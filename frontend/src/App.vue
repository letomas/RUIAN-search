<template>
  <v-app>
    <Navigation />

    <v-content class="pt-7">
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import Navigation from "./components/Navigation";
export default {
  name: "app",
  components: {
    Navigation
  }
};
</script>

<style lang="scss">
#app {
  font-family: "Roboto", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.v-label {
  font-size: 1.12em;
}
.map {
  position: relative;
  z-index: 0;
}
</style>
