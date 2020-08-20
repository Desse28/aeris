import HelperRegisterModule from "./../../helper-register-module"

import AerisDataValidationMainPage from "./aeris-datavalidation-mainpage";

const components = [AerisDataValidationMainPage];

export default {
    install: (Vue, options) => {
        let timer = setInterval(() => {
            HelperRegisterModule.registerStore(options.store, components);
            HelperRegisterModule.registerVueComponents(Vue, components);
            clearInterval(timer);
        }, 2000);
    }
};
