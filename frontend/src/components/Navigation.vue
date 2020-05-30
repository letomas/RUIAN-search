<template>
  <nav>
    <v-app-bar app dark hide-on-scroll>
      <v-app-bar-nav-icon
        class="hidden-md-and-up"
        @click.stop="drawer = !drawer"
      ></v-app-bar-nav-icon>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
          Vyhledávání adresních míst
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-app-bar-items class="hidden-sm-and-down">
        <v-btn
          v-for="item in items"
          :key="item.path"
          :to="{ name: item.path }"
          exact
          ><v-icon class="mr-1">{{ item.icon }}</v-icon
          >{{ item.title }}</v-btn
        >
        <a
          href="https://github.com/letomas/RUIAN-search"
          rel="noopener noreferrer"
          target="_blank"
        >
          <v-btn>
            <img
              id="github-logo"
              class="mr-1"
              alt="github-logo"
              src="../../public/assets/icons/GitHub-Mark-Light-32px.png"
            />
            Github repozitář
          </v-btn>
        </a>
      </v-app-bar-items>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" fixed temporary hide-on-scroll app>
      <v-list nav>
        <v-list-item
          v-for="item in items"
          :key="item.path"
          :to="{ name: item.path }"
          exact
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item href="https://github.com/letomas/RUIAN-search">
          <v-list-item-icon>
            <v-icon>computer</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>Github repozitář</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
export default {
  name: "Navigation",
  data() {
    return {
      drawer: false,
      items: [
        { title: "Vyhledávání", path: "home", icon: "search" },
        {
          title: "Adresní místa v okolí",
          path: "nearbyAddresses",
          icon: "map"
        }
      ]
    };
  }
};
</script>

<style scoped>
a {
  text-decoration: none;
}
#github-logo {
  height: 1.5em;
}
</style>
