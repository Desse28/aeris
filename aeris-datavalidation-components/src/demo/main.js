import Vue from 'vue'
import Vuex from "vuex";
import VueRouter from "vue-router"
import vuetify from './../../plugins/vuetify'
import axios from "axios";
import VueAxios from 'vue-axios'
import app from "./App.vue";
import catalogue from "./modules/aeris-datavalidation-ui/submodules/catalogue-ground-data";
import AerisDataValidationChart from "../lib/modules/aeris-datavalidation-blocks/submodules/aeris-datavalidation-chart/components/aeris-datavalidation-chart";
import Keycloak from "keycloak-js";

Vue.use(VueAxios, axios);
Vue.use(Vuex);
Vue.use(VueRouter);
Vue.config.productionTip = false

let initOptions = {
  url: "http://localhost:8180/auth",
  realm: "test",
  clientId: "test-web"
};

export let keycloak = Keycloak(initOptions);

const store = new Vuex.Store({
  namespaced: true,
  modules: {}
});

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: catalogue
    },
    {
      path: "/data-validation-tool",
      component: AerisDataValidationChart
    },
  ]
});

keycloak
    .init({
      onLoad: "check-sso",
      promiseType: "native"
    })
    .then(function(authenticated) {
      if (authenticated) {
        // Récupération des informations de l'utilisateur
        if (keycloak.tokenParsed) {
          let username = keycloak.tokenParsed.preferred_username;
          console.log("Test UserName : ");
          console.log(username);
          /*
          var name = keycloak.tokenParsed.given_name;
          var family_name = keycloak.tokenParsed.family_name;
          var email = keycloak.tokenParsed.email;*/

          // Le rôle est porté par le back-end (app-spring)
          if (keycloak.tokenParsed.resource_access["app-spring"]) {
            var resourceRoles = keycloak.tokenParsed.resource_access["app-spring"].roles;
          }

          var realmRoles = keycloak.tokenParsed.realm_access.roles;
          var roles = [];
          if (realmRoles) {
            roles = roles.concat(realmRoles)
            console.log("Test roles : ");
            console.log(roles);
          }
          if (resourceRoles) {
            roles = roles.concat(resourceRoles)
            console.log("Test roles : ");
            console.log(roles);
          }
        }
      }

      // Intercepteur
      axios.interceptors.request.use(
          config => {
            if (keycloak.token) {
              config.headers.Authorization = "Bearer " + keycloak.token;
              config.headers.Accept = "application/json";
            }
            console.log("Test interceptors");
            return config;
          },
          error => Promise.reject(error)
      );

      // Mise à jour du token
      function updateSSoToken() {
        setTimeout(function() {
          keycloak.updateToken(200000);
          updateSSoToken();
        }, 200000);
      }
      updateSSoToken();

      // Lancement de l’application
      new Vue({
        el: "#app",
        router,
        store,
        vuetify,
        template: "<app/>",
        components: {
          app
        }
      }).$mount("#app");
    });