import HelperRegisterModule from "./../../helper-register-module"

import AerisDataValidationMainPage from "./aeris-datavalidation-mainpage";
import AerisDataValidationHomePage from "./aeris-datavalidation-homepage";

const components = [
    AerisDataValidationMainPage,
    AerisDataValidationHomePage
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
