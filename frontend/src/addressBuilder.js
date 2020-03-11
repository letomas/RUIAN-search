module.exports = {
  buildInline: address => {
    let result = {};
    const addressInRows = module.exports.build(address);

    result = addressInRows.firstRow + ", " + addressInRows.secondRow;
    if (addressInRows.thirdRow) {
      result += ", " + addressInRows.thirdRow;
    }

    return result;
  },
  build: address => {
    let result = {};
    if (
      address.streetName !== "" &&
      !module.exports.districtEqualsCity(address)
    ) {
      result.secondRow = address.districtName;
    }

    if (address.streetName === "") {
      if (address.cityName === address.districtName) {
        result.firstRow = address.buildingType + " " + address.houseNumber;
      } else {
        result.firstRow = address.districtName;
        module.exports.addBuildingTypeAndHouseNumber(result, address);
      }

      module.exports.addOrientationalNumberAndLetter(result, address);
      module.exports.buildLastRow(result, address);

      return result;
    }

    result.firstRow = address.streetName;
    module.exports.addBuildingTypeAndHouseNumber(result, address);
    module.exports.addOrientationalNumberAndLetter(result, address);
    module.exports.buildLastRow(result, address);

    return result;
  },
  addBuildingTypeAndHouseNumber(result, address) {
    if (address.buildingType === "č.ev.") {
      result.firstRow += " " + address.buildingType + " " + address.houseNumber;
    } else {
      result.firstRow += " " + address.houseNumber;
    }
  },
  addOrientationalNumberAndLetter: (result, address) => {
    if (address.orientationalNumber) {
      result.firstRow +=
        "/" + address.orientationalNumber + address.orientationalNumberLetter;
    }
  },
  buildLastRow: (result, address) => {
    if (!result.secondRow) {
      if (address.cityName === "Praha") {
        result.secondRow = address.postalCode + " " + address.pragueBoroughName;
      } else {
        result.secondRow = address.postalCode + " " + address.cityName;
      }
    } else {
      if (address.cityName === "Praha") {
        result.thirdRow = address.postalCode + " " + address.pragueBoroughName;
      } else {
        result.thirdRow = address.postalCode + " " + address.cityName;
      }
    }
  },
  districtEqualsCity: address => {
    return address.districtName === address.cityName;
  }
};
