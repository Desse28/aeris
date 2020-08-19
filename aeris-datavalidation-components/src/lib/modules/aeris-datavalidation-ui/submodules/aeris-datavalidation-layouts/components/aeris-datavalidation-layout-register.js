import HelperRegisterModule from "./../../../../helper-register-module"

import AerisDatavalidationLandScapeLayaout from "./aeris-datavalidation-landscapelayout";
import AerisDatavalidationPortraitLayaout from "./aeris-datavalidation-potraitlayout";

const components = [
    AerisDatavalidationLandScapeLayaout,
    AerisDatavalidationPortraitLayaout
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
