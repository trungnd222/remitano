import APIUtil from '../utils/APIUtils'

export default class UserModel {

    static get() {
        return new Promise((resolve, reject) => {
            APIUtil.getJSONWithCredentials(process.env.REACT_APP_DOMAIN + `/api/user/get`, resolve, reject);
        });
    }

    static login(form) {
        return new Promise((resolve, reject) => {
            APIUtil.postJSONWithCredentials(process.env.REACT_APP_DOMAIN + `/api/user/login`, JSON.stringify(form), resolve, reject);
        });
    }

    static register(form) {
        return new Promise((resolve, reject) => {
            APIUtil.postJSONWithCredentials(process.env.REACT_APP_DOMAIN + `/api/user/register`, JSON.stringify(form), resolve, reject);
        });
    }


}