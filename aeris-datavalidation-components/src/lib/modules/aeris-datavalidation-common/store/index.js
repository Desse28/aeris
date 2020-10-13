import { User } from "../models/user.js";

export default {
    state: {
        user: null,
        authenticated: false
    },
    mutations: {
        updateUser(state, data) {
            state.user = new User(data);
        },
        updateAuthenticated(state, authenticated) {
            state.authenticated = authenticated;
        },
    },
    actions: {

    }
};
