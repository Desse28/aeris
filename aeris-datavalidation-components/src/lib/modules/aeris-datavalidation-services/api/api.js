import {instance} from '../../../../../plugins/keycloak'

export default {
    getFlagData : ( flagDataUrl ) => {
        return (instance.get( flagDataUrl, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
    getGroundData : ( groundDataUrl ) => {

        return (instance.get( groundDataUrl, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
    getDataInfo : ( dataInfoUrl ) => {

        return (instance.get( dataInfoUrl, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
}