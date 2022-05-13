import APIUtil from '../utils/APIUtils'

export default class MovieModel {

    static find(page, pageSize) {
        return new Promise((resolve, reject) => {
            APIUtil.getJSONWithCredentials(process.env.REACT_APP_DOMAIN + `/api/movie/find?page=${page}&pageSize=${pageSize}`, resolve, reject);
        });
    }

    static share(url) {
        return new Promise((resolve, reject) => {
            APIUtil.getJSONWithCredentials(process.env.REACT_APP_DOMAIN + `/api/movie/share?link=${encodeURIComponent(url)}`, resolve, reject);
        });
    }


}