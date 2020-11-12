import Vue from "vue";

import AerisDatavalidationChart from "./components/aeris-datavalidation-chart";
import AerisDatavalidationHeatMap from "./components/aeris-datavalidation-chart";
import AerisDatavalidationChartscontroller from "./components/aeris-datavalidation-chartscontroller";

Vue.use(
    AerisDatavalidationChart,
    AerisDatavalidationHeatMap,
    AerisDatavalidationChartscontroller,
);
