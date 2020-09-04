import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationSessionList from "./aeris-datavalidation-selectionslist";
import AerisDatavalidationSelectionsList from "./aeris-datavalidation-selectionslist";

const components = [
    AerisDatavalidationSessionList,
    AerisDatavalidationSelectionsList,
]

export default {
    install: (Vue, options) => {
        let timer = setInterval(() => {
            HelperRegisterModule.registerStore(options.store, components);
            HelperRegisterModule.registerVueComponents(Vue, components);
            clearInterval(timer);
        }, 2000);
    }
}
