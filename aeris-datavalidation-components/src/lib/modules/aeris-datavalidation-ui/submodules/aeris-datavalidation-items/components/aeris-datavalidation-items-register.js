import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationLoginItem from "./aeris-datavalidation-loginitem";
import AerisDatavalidationAccountItem from "./aeris-datavalidation-accountitem";

const components = [
    AerisDatavalidationLoginItem,
    AerisDatavalidationAccountItem
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
