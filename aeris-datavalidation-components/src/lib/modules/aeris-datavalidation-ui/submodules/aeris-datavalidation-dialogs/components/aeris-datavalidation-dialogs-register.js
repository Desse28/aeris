import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationDeleteDialog from "./aeris-datavalidation-deletedialog";
import AerisDatavalidationWorksFlowDialog from "./aeris-datavalidation-worksflowdialog";

const components = [
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationWorksFlowDialog,
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
