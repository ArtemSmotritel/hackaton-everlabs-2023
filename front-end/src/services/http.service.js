import axios from "axios";

class HttpService {
  _axiosClient;

  constructor() {
    this._axiosClient = axios.create({
      baseURL: "http://localhost:8080/api/",
      withCredentials: true,
    });

    this._axiosClient.interceptors.request.use(function (config) {
      config.headers.Authorization = +sessionStorage.getItem("userId") || 1;
      return config;
    });
  }

  get(url, config) {
    return this._axiosClient.get(url, config);
  }

  put(url, data, config) {
    return this._axiosClient.put(url, data, config);
  }

  post(url, data, config) {
    return this._axiosClient.post(url, data, config);
  }

  delete(url, config) {
    return this._axiosClient.delete(url, config);
  }
}

export default new HttpService();
