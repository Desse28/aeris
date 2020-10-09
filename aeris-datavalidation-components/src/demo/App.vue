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
          <v-list-item
                  v-for="item in items"
                  :key="item.text"
                  link
          >
            <v-tab
                v-if="!authenticated && item.text === getLoginMessage"
                v-on:click="login"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
            </v-tab>

            <v-tab
                v-else-if="authenticated && item.text === getLogoutMessage"
                v-on:click="logout"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
            </v-tab>

            <v-tab
                v-else-if="authenticated && item.text !== getLoginMessage"
                :to="item.to">
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
            </v-tab>
          </v-list-item>
          <v-list-item>
            <v-tab class="mt-4">
              <AerisDatavalidationLangSwitcher/>
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
  import AerisDatavalidationLangSwitcher from "./../lib/modules/aeris-datavalidation-ui/submodules/aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-switchers/aeris-datavalidation-langswitcher"

  export default {
    name: 'App',
    watch: {
      '$store.state.common.authenticated': function() {
        this.authenticated = this.$store.state.common.authenticated
        if(this.authenticated)
          this.$router.push( '/data-validation-tool')
      }
    },
    components : {
      AerisDatavalidationLangSwitcher
    },
    data: function() {
      return {
        authenticated : false,
      }
    },
    computed : {
      items : function () {
        let items = [
          {icon: 'mdi-chart-line', text: this.$t('appbar.data_validation_tool'), to: "/data-validation-tool"},
          {icon: 'mdi-login', text: this.$t('appbar.login'), to: ""},
          {icon: 'mdi-logout', text: this.$t('appbar.logout'), to: ""},
        ];
        return items
      },
      getLoginMessage : function () {
        return this.$t('appbar.login')
      },
      getLogoutMessage : function () {
        return this.$t('appbar.logout')
      }
    },
    mounted() {
      if(!keycloak.authenticated && this.$router.currentRoute.path !== '/')
        this.$router.push( '/')
    },
    methods: {
      login : function() {
        keycloak.login({ redirectUri: process.env.VUE_APP_ORIGN_URL + '/data-validation-tool' })
      },
      logout : function() {
        keycloak.logout({ redirectUri: process.env.VUE_APP_ORIGN_URL })
      }
    },
  };
</script>
