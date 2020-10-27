import HelperRegisterModule from "../../../../../../helper-register-module"

import AerisDatavalidationTimePicker from "./aeris-datavalidation-timepicker";
import AerisDatavalidationDateMounthPicker from "./aeris-datavalidation-datemounthpicker";

const components = [
    AerisDatavalidationTimePicker,
    AerisDatavalidationDateMounthPicker
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
