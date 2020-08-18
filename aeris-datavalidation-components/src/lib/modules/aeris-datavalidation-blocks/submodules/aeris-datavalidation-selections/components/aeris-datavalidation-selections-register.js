import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationSelection from "./aeris-datavalidation-selection";

const components = [
    AerisDatavalidationSelection,
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
