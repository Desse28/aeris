import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationChart from "./aeris-datavalidation-chart";

const components = [
    AerisDatavalidationChart,
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
