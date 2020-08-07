import { User } from "../models/user.js";

export default {
    state: {
        user: null,
        authenticated: false
    },
    mutations: {
        updateUser(state, user) {
            state.user = new User(user);
        },
        updateAuthenticated(state, authenticated) {
            state.authenticated = authenticated;
        }
    }
};
