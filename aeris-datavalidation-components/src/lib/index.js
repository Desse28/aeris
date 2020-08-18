import Vue from "vue";
import Vuex from "vuex";
import VueCustomElement from "vue-custom-element";
import axios from "axios";
import VueAxios from "vue-axios";

import AerisDataValidationChart from "./modules/aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart";

Vue.use(Vuex);
Vue.use(VueCustomElement);
Vue.use(VueAxios, axios);
Vue.customElement("aeris-datavalidation-chart", AerisDataValidationChart);
