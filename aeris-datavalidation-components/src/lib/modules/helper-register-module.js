function registerVueComponents(Vue, components) {
    for (let component in components) {
        if (components[component]) {
            Vue.component(components[component]);
        }
    }
}

function registerStore(store, components) {
    components.forEach(component => (component.store = store));
}

export default {
    registerVueComponents,
    registerStore
};
