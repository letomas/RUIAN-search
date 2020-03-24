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
        let buildingType =
          address.buildingType == "č.ev " ? " č. ev. " : "č. p. ";
        result.firstRow = buildingType + address.houseNumber;
      } else {
        result.firstRow = address.districtName;
        result.firstRow += " " + module.exports.buildHouseNumber(address);
      }

      result.firstRow += module.exports.buildOrientationalNumber(address);
      module.exports.buildLastRow(result, address);

      return result;
    }

    result.firstRow = address.streetName;
    result.firstRow += " " + module.exports.buildHouseNumber(address);
    result.firstRow += module.exports.buildOrientationalNumber(address);
    module.exports.buildLastRow(result, address);

    return result;
  },
  buildHouseNumber: address => {
    if (address.buildingType === "č.ev.") {
      return "č. ev. " + address.houseNumber;
    }

    return address.houseNumber;
  },
  buildOrientationalNumber: address => {
    if (address.orientationalNumber) {
      return (
        "/" + address.orientationalNumber + address.orientationalNumberLetter
      );
    }
    return "";
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
