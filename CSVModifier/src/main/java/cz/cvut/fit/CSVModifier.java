package cz.cvut.fit;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import java.io.*;

public class CSVModifier {
    public static void main(String[] args) throws IOException, FactoryException, TransformException {
        System.setProperty("org.geotools.referencing.forceYX", "true");
        Modifier modifier = new Modifier();
        String folderPath = "./temp";
        modifier.modifyAll(folderPath);
    }
}
