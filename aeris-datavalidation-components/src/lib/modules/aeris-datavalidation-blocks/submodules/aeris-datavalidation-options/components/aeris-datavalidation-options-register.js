import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationOption from "./aeris-datavalidation-option";

const components = [
    AerisDatavalidationOption,
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
