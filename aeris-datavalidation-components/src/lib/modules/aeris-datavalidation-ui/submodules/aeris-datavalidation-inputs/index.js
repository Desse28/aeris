import Vue from "vue";

import AerisDatavalidationSelect from "./components/submodules/aeris-datavalidation-selects/aeris-datavalidation-select";
import AerisDatavalidationTimePicker from "./components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-timepicker";
import AerisDatavalidationDateMounthPicker from "./components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-datemounthpicker";


Vue.use(
    AerisDatavalidationSelect,
    AerisDatavalidationTimePicker,
    AerisDatavalidationDateMounthPicker,
);
