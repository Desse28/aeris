import HelperRegisterModule from "../../../../helper-register-module.js";

import AerisMetadataServices from "./aeris-datavalidation-services";

const components = [AerisMetadataServices];

export default {
    install: (Vue, options) => {
        let timer = setInterval(() => {
            HelperRegisterModule.registerStore(options.store, components);
            HelperRegisterModule.registerVueComponents(Vue, components);
            clearInterval(timer);
        }, 2000);
    }
};
