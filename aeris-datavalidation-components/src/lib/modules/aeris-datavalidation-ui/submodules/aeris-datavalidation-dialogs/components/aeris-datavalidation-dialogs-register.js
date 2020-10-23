import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationDeleteDialog from "./aeris-datavalidation-deletedialog";
import AerisDatavalidationWorksFlowDialog from "./aeris-datavalidation-worksflowdialog";
import AerisDatavalidationSelectionsDialog from "./aeris-datavalidation-selectionsdialog";
import AerisDatavalidationParametersDialog from "./aeris-datavalidation-selectionsdialog";
import AerisDatavalidationChartsTabsDialog from "./aeris-datavalidation-selectionsdialog";

const components = [
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationWorksFlowDialog,
    AerisDatavalidationSelectionsDialog,
    AerisDatavalidationParametersDialog,
    AerisDatavalidationChartsTabsDialog
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
