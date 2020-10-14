import HelperRegisterModule from "./../../helper-register-module"

import AerisDataValidationHomePage from "./aeris-datavalidation-homepage";
import AerisDataValidationPageNotFound from "./aeris-datavalidation-pagenotfound";

const components = [
    AerisDataValidationHomePage,
    AerisDataValidationPageNotFound
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
