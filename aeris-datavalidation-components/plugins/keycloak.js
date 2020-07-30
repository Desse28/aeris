import Keycloak from "keycloak-js";
import axios from "axios";
import "babel-polyfill";

export default {
    async start(store) {
        const keycloak = Keycloak({
            url: process.env.KEYCLOAK_URL,
            realm: process.env.KEYCLOAK_REALM,
            clientId: process.env.KEYCLOAK_CLIENT_ID
        });
        addTokenToRequest(keycloak);
        await initKeycloak(keycloak, store);
        updateSSoToken(keycloak);
    }
};

async function initKeycloak(keycloak, store) {
    await keycloak
        .init({
            onLoad: "check-sso",
            promiseType: "native"
        })
        .then(function(authenticated) {
            if (authenticated && keycloak.tokenParsed) {
                axios.get(`${process.env.API_URL}/users/current`).then(response => {
                    store.commit("updateUser", response.data);
                });
            }
        });
}

function addTokenToRequest(keycloak) {
    axios.interceptors.request.use(
        config => {
            if (keycloak.token) {
                config.headers.Authorization = "Bearer " + keycloak.token;
            }
            return config;
        },
        error => Promise.reject(error)
    );
}

function updateSSoToken(keycloak) {
    const REFRESH_TOKEN_TIMEOUT = 200000;
    setTimeout(function() {
        keycloak.updateToken(REFRESH_TOKEN_TIMEOUT);
        updateSSoToken(keycloak);
    }, REFRESH_TOKEN_TIMEOUT);
}