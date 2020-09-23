import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationChartsTab from "./aeris-datavalidation-chartstab";
import AerisDatavalidationMainChartTab from "./aeris-datavalidation-maincharttab";

const components = [
    AerisDatavalidationChartsTab,
    AerisDatavalidationMainChartTab,
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
