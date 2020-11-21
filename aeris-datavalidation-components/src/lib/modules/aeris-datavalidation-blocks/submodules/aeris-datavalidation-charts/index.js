import Vue from "vue";

import AerisDatavalidationChart from "./components/aeris-datavalidation-chart";
import AerisDatavalidationHeatMap from "./components/aeris-datavalidation-chart";
import AerisDatavalidationTimeSeries from "./components/aeris-datavalidation-timeseries";
import AerisDatavalidationDefaultChart from "./components/aeris-datavalidation-defaultchart";
import AerisDatavalidationChartscontroller from "./components/aeris-datavalidation-chartscontroller";

Vue.use(
    AerisDatavalidationChart,
    AerisDatavalidationHeatMap,
    AerisDatavalidationTimeSeries,
    AerisDatavalidationDefaultChart,
    AerisDatavalidationChartscontroller,
);
