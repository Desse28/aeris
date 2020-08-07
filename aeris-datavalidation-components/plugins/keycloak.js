import Keycloak from "keycloak-js";
import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:9001',
    timeout: 1000
});

const keycloak = Keycloak({
    url: "http://localhost:8180/auth",
    realm: "SpringBootKeycloak",
    clientId: "test-vjs"
});

async function start_keycloack(store) {
    console.log("Test before connect : ", keycloak.authenticated)
    addTokenToRequest(keycloak);
    await initKeycloak(keycloak, store);
    updateSSoToken(keycloak);
    console.log("Test after connect : ", keycloak.authenticated)
}

async function initKeycloak(keycloak, store) {
    await keycloak
        .init({
            onLoad: "check-sso",
            promiseType: "native"
        })
        .then(function(authenticated) {
            if (authenticated && keycloak.tokenParsed) {
                let username = keycloak.tokenParsed.preferred_username;
                console.log("Test UserName : ");
                console.log(username);
                // Le rôle est porté par le back-end (app-spring)
                if (keycloak.tokenParsed.resource_access["app-spring"]) {
                    let resourceRoles = keycloak.tokenParsed.resource_access["app-spring"].roles;

                    let realmRoles = keycloak.tokenParsed.realm_access.roles;
                    let roles = [];
                    if (realmRoles) {
                        roles = roles.concat(realmRoles)
                        console.log("Test roles : ");
                        console.log(roles);
                    }
                    if (resourceRoles) {
                        roles = roles.concat(resourceRoles)
                        console.log("Test roles : ");
                        console.log(roles);
                    }
                }
                store.commit("updateAuthenticated", keycloak.authenticated);
            }
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