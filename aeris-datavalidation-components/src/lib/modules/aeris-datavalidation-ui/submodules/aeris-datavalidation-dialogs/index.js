import Vue from "vue";

import AerisDatavalidationDeleteDialog from "./components/aeris-datavalidation-deletedialog";
import AerisDatavalidationWorksFlowDialog from "./components/aeris-datavalidation-worksflowdialog";
import AerisDatavalidationParametersDialog from "./components/aeris-datavalidation-parametersdialog";
import AerisDatavalidationChartsTabsDialog from "./components/aeris-datavalidation-chartstabsdialog";

Vue.use(
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationWorksFlowDialog,
    AerisDatavalidationParametersDialog,
    AerisDatavalidationChartsTabsDialog
);
