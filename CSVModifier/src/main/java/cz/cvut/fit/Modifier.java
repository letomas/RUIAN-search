package cz.cvut.fit;

import org.geotools.geometry.DirectPosition2D;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import java.io.*;

import static cz.cvut.fit.CoordinatesConverter.convert;

public class Modifier {
    public void modifyAll(String path) throws IOException, FactoryException, TransformException {
        File folder = new File(path);
        File[] files = folder.listFiles();
        for(File file : files) {
            modifyFile(file);
        }
    }

    public void modifyFile(File file) throws IOException, FactoryException, TransformException {
        File csvFile = file;
        File output = new File("./data/" + csvFile.getName());
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(output));
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row = csvReader.readLine();
        csvWriter.write(row + ";Identifikace;Coordinates_lat_lon\n");
        
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            Double x = null;
            Double y = null;
            String houseNumber = data[12];
            String orientationalNumber = data[13];
            String orientationalNumberLetter = data[14];
            String transformedCoordinates = "";
            String identification = "";

            if(orientationalNumber.equals(null) && orientationalNumber.isEmpty()) {
                identification = houseNumber + "/"
                        + orientationalNumber
                        + orientationalNumberLetter;
            } else {
                identification = houseNumber;
            }

            try {
                x = Double.parseDouble(data[17]);
                y = Double.parseDouble(data[16]);

                DirectPosition2D transformation = convert(x, y);
                Double transformedX = transformation.x;
                Double transformedY = transformation.y;

                transformedCoordinates = transformedX + "," + transformedY;
            } catch(Exception e) {
                System.err.println("Can't transform record without coordinates");
            } finally {
                csvWriter.write(row + ";"
                        + identification + ";"
                        + transformedCoordinates + "\n");
            }
        }
        
        csvWriter.close();
        csvReader.close();
        csvFile.delete();
    }
}
