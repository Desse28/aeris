import axios from 'axios'

export default {
    getFlagData : ( serverUrl, flagDataUrl ) => {
        const instance = axios.create({
            baseURL: serverUrl,
            timeout: 1000
        });
        return (instance.get( flagDataUrl, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
    getGroundData : ( serverUrl, groundDataUrl ) => {
        const instance = axios.create({
            baseURL: serverUrl,
            timeout: 1000
        });

        return (instance.get( groundDataUrl, {
            transformResponse: [function (data) {
                return data? JSON.parse(data) : data;
            }]
        }))
    },
}