import axios from "axios";

const SERVER_URL = "http://localhost:8081/api";

const ruian_api = axios.create({
  baseURL: SERVER_URL,
  timeout: 10000
});

export default {
  getQueryResult: query => {
    return ruian_api.get("/addresses?search=" + query);
  },
  getFormQueryResult: query => {
    let params = "street=" + query.street;
    params += "&houseNumber=" + query.houseNumber;
    params += "&city=" + query.city;
    params += "&district=" + query.district;
    return ruian_api.get("/addresses/search?" + params);
  },
  getDetail: admCode => {
    return ruian_api.get("/addresses/" + admCode);
  },
  findByAdmCode: admCode => {
    return ruian_api.get("/addresses?admCode=" + admCode);
  },
  getCitySuggestions: city => {
    return ruian_api.get("/suggestions/city?city=" + city);
  },
  getDistrictSuggestions: (city, district) => {
    let params = "city=" + city;
    params += "&district=" + district;
    return ruian_api.get("/suggestions/district?" + params);
  },
  getStreetSuggestions: (city, district, street) => {
    let params = "city=" + city;
    params += "&district=" + district;
    params += "&street=" + street;
    return ruian_api.get("/suggestions/street?" + params);
  },
  getHouseNumberSuggestions: (city, district, street, houseNumber) => {
    let params = "city=" + city;
    params += "&district=" + district;
    params += "&street=" + street;
    params += "&houseNumber=" + houseNumber;
    return ruian_api.get("/suggestions/houseNumber?" + params);
  },
  getHealthStatus: () => {
    return ruian_api.get("/health");
  }
};
