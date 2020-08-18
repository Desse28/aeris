import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationChartSteppers from "./aeris-datavalidation-chartsteppers";

const components = [
    AerisDatavalidationChartSteppers,
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
