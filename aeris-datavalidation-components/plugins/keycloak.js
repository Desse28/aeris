import Keycloak from "keycloak-js";
import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:9001',
    //timeout: 1000
});

const keycloak = Keycloak({
    url: "https://sso.aeris-data.fr/auth",
    realm: "test",
    clientId: "datavalidation-vjs",
});

/*const keycloak = Keycloak({
    url: "http://localhost:8180/auth",
    realm: "SpringBootKeycloak",
    clientId: "test-vjs",
});*/

async function start_keycloack(store) {
    addTokenToRequest(keycloak);
    await initKeycloak(keycloak, store);
    updateSSoToken(keycloak);
}

async function initKeycloak(keycloak, store) {
    await keycloak
        .init({
            onLoad: "check-sso",
            promiseType: "native",
            checkLoginIframe: false
        })
        .then(function(authenticated) {
            if (authenticated && keycloak.tokenParsed) {
                store.commit("updateUser", keycloak.tokenParsed);
                store.commit("updateAuthenticated", keycloak.authenticated);
            }
        }).catch(error => {
            console.log("Error *** : ", error)
        });
}

function addTokenToRequest(keycloak) {
    instance.interceptors.request.use(
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

export {
    start_keycloack,
    instance,
    keycloak
};