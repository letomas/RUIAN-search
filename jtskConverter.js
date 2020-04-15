function jtsk_to_wgs(X, Y) {
  let coord = {
    lat: 0,
    lon: 0
  };

  /* Přepočet vstupích údajů - vychazi z nejakeho skriptu, ktery jsem nasel na Internetu - nejsem autorem prepoctu. */

  /*Vypocet zemepisnych souradnic z rovinnych souradnic*/
  let a = 6377397.15508;
  let e = 0.081696831215303;
  let n = 0.97992470462083;
  let konst_u_ro = 12310230.12797036;
  let sinUQ = 0.863499969506341;
  let cosUQ = 0.504348889819882;
  let sinVQ = 0.420215144586493;
  let cosVQ = 0.907424504992097;
  let alfa = 1.000597498371542;
  let k = 1.003419163966575;
  let ro = Math.sqrt(X * X + Y * Y);
  let epsilon = 2 * Math.atan(Y / (ro + X));
  let D = epsilon / n;
  let S =
    2 * Math.atan(Math.exp((1 / n) * Math.log(konst_u_ro / ro))) - Math.PI / 2;
  let sinS = Math.sin(S);
  let cosS = Math.cos(S);
  let sinU = sinUQ * sinS - cosUQ * cosS * Math.cos(D);
  let cosU = Math.sqrt(1 - sinU * sinU);
  let sinDV = (Math.sin(D) * cosS) / cosU;
  let cosDV = Math.sqrt(1 - sinDV * sinDV);
  let sinV = sinVQ * cosDV - cosVQ * sinDV;
  let cosV = cosVQ * cosDV + sinVQ * sinDV;
  let Ljtsk = (2 * Math.atan(sinV / (1 + cosV))) / alfa;
  let t = Math.exp((2 / alfa) * Math.log((1 + sinU) / cosU / k));
  let pom = (t - 1) / (t + 1);
  do {
    var sinB = pom;
    pom = t * Math.exp(e * Math.log((1 + e * sinB) / (1 - e * sinB)));
    pom = (pom - 1) / (pom + 1);
  } while (Math.abs(pom - sinB) > 1e-15);

  let Bjtsk = Math.atan(pom / Math.sqrt(1 - pom * pom));

  /* Pravoúhlé souřadnice ve S-JTSK */

  let f_1 = 299.152812853;
  let e2 = 1 - (1 - 1 / f_1) * (1 - 1 / f_1);
  ro = a / Math.sqrt(1 - e2 * Math.sin(Bjtsk) * Math.sin(Bjtsk));
  let x = ro * Math.cos(Bjtsk) * Math.cos(Ljtsk);
  let y = ro * Math.cos(Bjtsk) * Math.sin(Ljtsk);
  let z = (1 - e2) * ro * Math.sin(Bjtsk);

  /* Pravoúhlé souřadnice v WGS-84*/
  let dx = 570.69;
  let dy = 85.69;
  let dz = 462.84;
  let wz = ((-5.2611 / 3600) * Math.PI) / 180;
  let wy = ((-1.58676 / 3600) * Math.PI) / 180;
  let wx = ((-4.99821 / 3600) * Math.PI) / 180;
  let m = 3.543e-6;
  let xn = dx + (1 + m) * (x + wz * y - wy * z);
  let yn = dy + (1 + m) * (-wz * x + y + wx * z);
  let zn = dz + (1 + m) * (wy * x - wx * y + z);

  /* Geodetické souřadnice v systému WGS-84*/
  a = 6378137.0;
  f_1 = 298.257223563;
  let a_b = f_1 / (f_1 - 1);
  let p = Math.sqrt(xn * xn + yn * yn);
  e2 = 1 - (1 - 1 / f_1) * (1 - 1 / f_1);
  let theta = Math.atan((zn * a_b) / p);
  let st = Math.sin(theta);
  let ct = Math.cos(theta);
  t = (zn + e2 * a_b * a * st * st * st) / (p - e2 * a * ct * ct * ct);
  let B = Math.atan(t);
  let L = 2 * Math.atan(yn / (p + xn));

  /* Formát výstupních hodnot */
  coord.lat = (B / Math.PI) * 180;
  coord.lon = (L / Math.PI) * 180;

  return coord.lat + ", " + coord.lon;
}

let myArgs = process.argv.slice(2);
const x = parseFloat(myArgs[0]);
const y = parseFloat(myArgs[1]);
console.log(jtsk_to_wgs(x, y));
