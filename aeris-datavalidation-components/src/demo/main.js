import Vue from 'vue'
import Vuex from "vuex"
import VueRouter from "vue-router"
import i18n from './../../plugins/vue-i18n'
import vuetify from './../../plugins/vuetify'
import axios from "axios"
import VueAxios from 'vue-axios'
import {start_keycloack} from "../../plugins/keycloak.js"
import app from "./App.vue"
import common from "./../lib/modules/aeris-datavalidation-common/store/index"
import myMixin from "./../lib/modules/aeris-datavalidation-common/mixin/index"
import AerisDataValidationHomePage from "../lib/modules/aeris-datavalidation-pages/components/aeris-datavalidation-homepage"
import AerisDataValidationMainPage from "../lib/modules/aeris-datavalidation-pages/components/aeris-datavalidation-mainpage"
import AerisDataValidationPageNotFound from  "./../lib/modules/aeris-datavalidation-pages/components/aeris-datavalidation-pagenotfound"

Vue.use(VueAxios, axios);
Vue.use(Vuex);
Vue.use(VueRouter);
Vue.config.productionTip = false

const store = new Vuex.Store({
  namespaced: true,
  modules: {
    common
  }
});

start_keycloack(store);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: AerisDataValidationHomePage
    },
    {
      path: "/data-validation-tool",
      /*beforeEnter(to, from, next) {
        if (keycloak.authenticated) {
            next();
        } else {
          next('/');
        }
      },*/
      component: AerisDataValidationMainPage
    },
    { path: "*", component: AerisDataValidationPageNotFound }
  ]
});

new Vue({
  el: "#app",
  router,
  store,
  vuetify,
  i18n,
  mixins: [myMixin],
  template: "<app/>",
  components: {
    app
  }
}).$mount("#app");