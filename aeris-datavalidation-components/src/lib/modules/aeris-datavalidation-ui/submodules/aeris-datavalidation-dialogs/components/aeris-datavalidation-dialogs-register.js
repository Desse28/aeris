import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationDeleteDialog from "./aeris-datavalidation-deletedialog";
import AerisDatavalidationAccountDialog from "./aeris-datavalidation-accountdialog";
import AerisDatavalidationWorksFlowDialog from "./aeris-datavalidation-worksflowdialog";
import AerisDatavalidationSelectionsDialog from "./aeris-datavalidation-selectionsdialog";

const components = [
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationAccountDialog,
    AerisDatavalidationWorksFlowDialog,
    AerisDatavalidationSelectionsDialog,
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
