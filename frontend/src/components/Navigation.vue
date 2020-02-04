<template>
  <div id="nav">
    <b-navbar toggleable="lg" type="dark" variant="dark" fixed="top">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav tabs>
          <b-nav-item href="#" style="color: red;">Úvod</b-nav-item>
          <b-nav-item href="#">Vyhledávání</b-nav-item>
          <b-nav-item href="#">Adresní místa v okolí</b-nav-item>
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
