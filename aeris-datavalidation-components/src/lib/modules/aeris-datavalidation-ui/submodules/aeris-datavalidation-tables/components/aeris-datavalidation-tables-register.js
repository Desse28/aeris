import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationSessionsTable from "./aeris-datavalidation-sessionstable";
import AerisDatavalidationSelectionsTable from "./aeris-datavalidation-selectionstable";

const components = [
    AerisDatavalidationSessionsTable,
    AerisDatavalidationSelectionsTable,
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
