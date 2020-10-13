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
          <AerisDatavalidationAccountDialog/>
          <v-list-item  v-if="!authenticated">
            <v-tab
                v-on:click="login"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ $t('appbar.login') }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>mdi-login</v-icon>
              </v-list-item-action>
            </v-tab>
          </v-list-item>
        </v-list>
      </v-app-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-app>
  </div>
</template>
<script>
import {keycloak}  from '../../plugins/keycloak'
  import AerisDatavalidationAccountDialog from "./../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-accountdialog"
  import AerisDatavalidationLangSwitcher from "./../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-switchers/aeris-datavalidation-langswitcher"

  export default {
    name: 'App',
    components : {
      AerisDatavalidationLangSwitcher,
      AerisDatavalidationAccountDialog
    },
    watch: {
      '$store.state.common.authenticated': function() {
        this.authenticated = this.$store.state.common.authenticated
        if(this.authenticated)
          this.$router.push( '/data-validation-tool')
      }
    },
    data: function() {
      return {
        authenticated : false,
      }
    },
    methods: {
      login : function() {
        keycloak.login({ redirectUri: process.env.VUE_APP_ORIGN_URL + '/data-validation-tool' })
      },
    },
  };
</script>
