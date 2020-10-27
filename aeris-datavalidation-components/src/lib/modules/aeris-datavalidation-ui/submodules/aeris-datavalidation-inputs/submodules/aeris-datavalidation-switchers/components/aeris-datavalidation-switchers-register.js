import HelperRegisterModule from "../../../../../../helper-register-module"

import AerisDatavalidationLangSwitcher from "./aeris-datavalidation-langswitcher";

const components = [
    AerisDatavalidationLangSwitcher,
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
