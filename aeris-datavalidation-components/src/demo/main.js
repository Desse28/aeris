import Vue from 'vue'
import Vuex from "vuex"
import VueRouter from "vue-router"
import vuetify from './../../plugins/vuetify'
import axios from "axios"
import VueAxios from 'vue-axios'
import {start_keycloack} from "../../plugins/keycloak.js"
import app from "./App.vue"
import common from "./../lib/modules/aeris-datavalidation-common/store/index"
import catalogue from "./modules/aeris-datavalidation-ui/submodules/catalogue-ground-data"
import AerisDataValidationChart from "../lib/modules/aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"

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
      component: catalogue
    },
    {
      path: "/data-validation-tool",
      component: AerisDataValidationChart
    },
  ]
});

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