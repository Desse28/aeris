import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationLoginItem from "./aeris-datavalidation-loginitem";
import AerisDatavalidationLogoutItem from "./aeris-datavalidation-logoutitem";
import AerisDatavalidationAccountItem from "./aeris-datavalidation-accountitem";

const components = [
    AerisDatavalidationLoginItem,
    AerisDatavalidationLogoutItem,
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
