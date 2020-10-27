import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationToolbar from "./aeris-datavalidation-toolbar";

const components = [
    AerisDatavalidationToolbar,
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
