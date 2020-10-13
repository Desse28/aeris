<template>
  <div id="app">
    <v-app>
      <v-app-bar
              app
              color="primary"
              dark
      >
        <div class="d-flex align-center">
          <v-img
                  alt="Vuetify Logo"
                  class="shrink mr-2"
                  contain
                  src="https://www7.obs-mip.fr/wp-content-aeris/uploads/sites/15/2017/09/logo_passy.png"
                  transition="scale-transition"
                  width="160"
          />
        </div>

        <v-spacer></v-spacer>

        <v-list
                class="d-flex flex-row"
                color="primary"
                max-height="60"
        >
          <v-list-item>
            <v-tab class="mt-4">
              <AerisDatavalidationLangSwitcher/>
            </v-tab>
          </v-list-item>
          <AerisDatavalidationAccountItem/>
          <AerisDatavalidationLoginItem/>
        </v-list>
      </v-app-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-app>
  </div>
</template>
<script>
  import AerisDatavalidationLoginItem from "../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-items/components/aeris-datavalidation-loginitem"
  import AerisDatavalidationAccountItem from "../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-items/components/aeris-datavalidation-accountitem"
  import AerisDatavalidationLangSwitcher from "./../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-switchers/aeris-datavalidation-langswitcher"

  export default {
    name: 'App',
    components : {
      AerisDatavalidationLoginItem,
      AerisDatavalidationAccountItem,
      AerisDatavalidationLangSwitcher
    }
  };
</script>
