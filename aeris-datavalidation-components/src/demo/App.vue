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
        >
          <v-list-item
                  v-for="item in items"
                  :key="item.text"
                  link
          >
            <v-tab
                v-if="!authenticated && item.text === 'Login'"
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
                v-else-if="authenticated && item.text === 'Logout'"
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
                v-else-if="authenticated && item.text !== 'Login'"
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
        </v-list>
      </v-app-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-app>
  </div>
</template>
<script>
  import { keycloak } from './main.js'
  export default {
    name: 'App',
    components: {
    },
    data: function() {
      return {
        items: [
          { icon: 'mdi-chart-line', text: 'Data validation tool', to : "/data-validation-tool" },
          { icon: 'mdi-login', text: 'Login', to : "" },
          { icon: 'mdi-logout', text: 'Logout', to : "" },
        ],
        authenticated : keycloak.authenticated,
      }
    },
    mounted() {
    },
    methods: {
      login : function () {
        keycloak.login()

      },
      logout : function () {
        keycloak.logout()
      }
    }
  };
</script>
