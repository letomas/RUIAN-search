import axios from "axios";

const SERVER_URL = "http://localhost:8081/api";

const ruian_api = axios.create({
  baseURL: SERVER_URL,
  timeout: 1000
});

export default {
  getQueryResult: query => {
    return ruian_api.get("/addresses?search=" + query);
  },
  getFormQueryResult: query => {
    let params = "street=" + query.street;
    params += "&houseNumber=" + query.houseNumber;
    params += "&city=" + query.city;
    params += "&borough=" + query.borough;
    return ruian_api.get("/addresses/form-search?" + params);
  },
  getDetail: admCode => {
    return ruian_api.get("/addresses/" + admCode);
  },
  findByAdmCode: admCode => {
    return ruian_api.get("/addresses?admCode=" + admCode);
  },
  getHealthStatus: () => {
    return ruian_api.get("/health");
  }
};
