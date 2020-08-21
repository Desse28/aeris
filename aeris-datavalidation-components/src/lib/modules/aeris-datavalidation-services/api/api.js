import { instance } from '../../../../../plugins/keycloak'

export default {
    getData : ( url ) => {
        return (instance.get( url, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
}