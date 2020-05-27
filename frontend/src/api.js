import axios from "axios";

const SERVER_URL = "/api";

const ruian_api = axios.create({
  baseURL: SERVER_URL,
  timeout: 3500
});

export default {
  getFormQueryResult: (query, page) => {
    let params = "street=" + query.street;
    params += "&houseNumber=" + query.houseNumber;
    params += "&city=" + query.city;
    params += "&district=" + query.district;
    params += "&page=" + page;
    return ruian_api.get("/addresses/search?" + params);
  },
  getDetail: admCode => {
    return ruian_api.get("/addresses/" + admCode);
  },
  findByAdmCode: (admCode, page) => {
    let params = "admCode=" + admCode;
    params += "&page=" + page;
    return ruian_api.get("/addresses?" + params);
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
  getNearbyAddresses: (x, y, distance) => {
    let params = "x=" + x;
    params += "&y=" + y;
    params += "&distance=" + distance;
    return ruian_api.get("/addresses/nearby?" + params);
  },
  getHealthStatus: () => {
    return ruian_api.get("/health");
  }
};
