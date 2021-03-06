import HelperRegisterModule from "./../../../../helper-register-module"
import AerisDatavalidationTypography from "./aeris-datavalidation-typography";

const components = [
    AerisDatavalidationTypography,
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
