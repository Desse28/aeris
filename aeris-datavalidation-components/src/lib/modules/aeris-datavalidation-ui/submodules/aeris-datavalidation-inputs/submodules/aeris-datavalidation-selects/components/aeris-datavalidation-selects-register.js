import HelperRegisterModule from "../../../../../../helper-register-module"

import AerisDatavalidationSelect from "./aeris-datavalidation-select";

const components = [
    AerisDatavalidationSelect,
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
