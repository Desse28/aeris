import Vue from 'vue'
import Vuex from "vuex";
import VueRouter from "vue-router"
import vuetify from './../../plugins/vuetify'
import app from "./App.vue";
import catalogue from "./modules/aeris-datavalidation-ui/submodules/catalogue-ground-data";
import HelloWorld from "./modules/aeris-datavalidation-ui/submodules/HelloWorld";

Vue.use(Vuex);
Vue.use(VueRouter);
Vue.config.productionTip = false

const store = new Vuex.Store({
  namespaced: true,
  modules: {}
});

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: () => HelloWorld
    },
    {
      path: "/data-validation-tool",
      component: () => catalogue
    },
    {
      path: "/settings",
      name: 'HelloWorld',
      component: () => HelloWorld
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
