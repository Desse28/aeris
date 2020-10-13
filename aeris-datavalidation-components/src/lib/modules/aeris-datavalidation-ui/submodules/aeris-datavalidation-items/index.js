import Vue from "vue";

import AerisDatavalidationLoginItem from "./components/aeris-datavalidation-loginitem";
import AerisDatavalidationLogoutItem from "./components/aeris-datavalidation-loginitem";
import AerisDatavalidationAccountItem from "./components/aeris-datavalidation-accountitem";

Vue.use(
    AerisDatavalidationLoginItem,
    AerisDatavalidationLogoutItem,
    AerisDatavalidationAccountItem,
);
