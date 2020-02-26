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
