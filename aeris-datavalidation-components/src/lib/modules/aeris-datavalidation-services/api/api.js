import { instance } from '../../../../../plugins/keycloak'

export default {
    create :
        (url, data) => instance.post(url, data)
    ,
    getData : ( url ) => {
        return (instance.get( url, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
    update :
        (url, data) => instance.put(url, data),
    delete :
        (url) => instance.delete( url )
}