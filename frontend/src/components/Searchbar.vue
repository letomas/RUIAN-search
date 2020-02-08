<template>
  <div class="search-bar">
    <b-container fluid>
      <b-row>
        <label for="name-search">Vyhledávání adresních míst podle názvu:</label>
      </b-row>
      <b-row>
        <b-input-group id="name-search">
          <b-form-input
            type="search"
            placeholder="Zadejte název adresního místa"
          ></b-form-input>

          <b-input-group-append>
            <b-button variant="primary">
              <BIconSearch></BIconSearch>
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-row>

      <b-row>
        <label for="code-search">
          Vyhledávání adresních míst podle jejich kódu:
        </label>
      </b-row>
      <b-row>
        <b-input-group id="code-search">
          <b-form-input
            type="search"
            label="Vyhledávání adresních míst podle jejich kódu:"
            placeholder="Zadejte kód ADM"
          ></b-form-input>

          <b-input-group-append>
            <b-button variant="primary">
              <BIconSearch></BIconSearch>
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "Searchbar"
};
</script>

<style scoped>
.input-group {
  margin-bottom: 1rem;
}
#name-search {
  width: 40%;
}
#code-search {
  width: 15%;
}
</style>
