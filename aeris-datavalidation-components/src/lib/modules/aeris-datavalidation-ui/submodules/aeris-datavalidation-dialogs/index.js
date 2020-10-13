import Vue from "vue";

import AerisDatavalidationDeleteDialog from "./components/aeris-datavalidation-deletedialog";
import AerisDatavalidationAccountDialog from "./components/aeris-datavalidation-accountdialog";
import AerisDatavalidationWorksFlowDialog from "./components/aeris-datavalidation-worksflowdialog";

Vue.use(
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationAccountDialog,
    AerisDatavalidationWorksFlowDialog
);
