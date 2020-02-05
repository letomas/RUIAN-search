<template>
  <div class="search-bar">
    <b-input-group>
      <b-form-input
        type="search"
        placeholder="Zde zadejte název adresního místa"
      ></b-form-input>

      <b-input-group-append>
        <b-button variant="primary">
          <BIconSearch></BIconSearch>
        </b-button>
      </b-input-group-append>
    </b-input-group>
  </div>
</template>

<script>
export default {
  name: "Searchbar"
};
</script>
