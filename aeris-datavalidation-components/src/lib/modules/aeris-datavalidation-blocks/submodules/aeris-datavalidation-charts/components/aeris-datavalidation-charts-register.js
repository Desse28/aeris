import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationChart from "./aeris-datavalidation-chart";
import AerisDatavalidationHeatMap from "./aeris-datavalidation-heatmap";
import AerisDatavalidationTimeSeries from "./aeris-datavalidation-timeseries";
import AerisDatavalidationDefaultChart from "./aeris-datavalidation-defaultchart";
import AerisDatavalidationChartscontroller from "./aeris-datavalidation-chartscontroller";

const components = [
    AerisDatavalidationChart,
    AerisDatavalidationHeatMap,
    AerisDatavalidationTimeSeries,
    AerisDatavalidationDefaultChart,
    AerisDatavalidationChartscontroller,
];

export default {
    install: (Vue, options) => {
        let timer = setInterval(() => {
            HelperRegisterModule.registerStore(options.store, components);
            HelperRegisterModule.registerVueComponents(Vue, components);
            clearInterval(timer);
        }, 2000);
    }
};
