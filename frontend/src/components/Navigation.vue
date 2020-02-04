<template>
  <div id="nav">
    <b-navbar toggleable="lg" type="dark" variant="dark" fixed="top">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav tabs>
          <b-nav-item :to="{ name: 'home' }">Úvod</b-nav-item>
          <b-nav-item :to="{ name: 'search' }">Vyhledávání</b-nav-item>
          <b-nav-item :to="{ name: 'nearbyAddresses' }"
            >Adresní místa v okolí</b-nav-item
          >
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "Navigation"
};
</script>
