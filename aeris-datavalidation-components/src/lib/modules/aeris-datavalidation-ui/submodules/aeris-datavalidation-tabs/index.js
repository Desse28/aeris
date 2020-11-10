import Vue from "vue";

import AerisDatavalidationChartsTab from "./components/aeris-datavalidation-chartstab";
import AerisDatavalidationTabHandler from "./components/aeris-datavalidation-tabhandler";
import AerisDatavalidationMainChartTab from "./components/aeris-datavalidation-maincharttab";

Vue.use(
    AerisDatavalidationChartsTab,
    AerisDatavalidationTabHandler,
    AerisDatavalidationMainChartTab
);
