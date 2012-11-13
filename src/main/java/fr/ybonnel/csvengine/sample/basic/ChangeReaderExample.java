package fr.ybonnel.csvengine.sample.basic;


import fr.ybonnel.csvengine.CsvEngine;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import fr.ybonnel.csvengine.factory.*;
import fr.ybonnel.csvengine.model.InsertObject;

import java.io.*;
import java.util.List;

public class ChangeReaderExample {

    /**
     * Create the CsvEngine for the class Dog.
     */
    public static CsvEngine engine;
    static {
        engine = new CsvEngine(Dog.class);
        engine.setFactory(new DefaultCsvManagerFactory(){
            @Override
            public AbstractCsvReader createReaderCsv(Reader reader, char separator) {
                return new OpenCsvReader(reader, separator){
                    @Override
                    public String[] readLine() throws IOException {
                        String[] nextLine = super.readLine();
                        if (isLineAComment(nextLine)) {
                            nextLine = readLine();
                        }
                        return nextLine;
                    }

                    private boolean isLineAComment(String[] line) {
                        return line != null && line.length > 0 && line[0].startsWith("#");
                    }
                };
            }
        });
    }

    public static void main(String[] args) throws CsvErrorsExceededException {
        List<Dog> dogs = readDogs();

        writeDogs(dogs);
    }

    private static void writeDogs(List<Dog> dogs) {
        // Write dogs into CSV File.
        System.out.println("**************");
        System.out.println("write dogs");
        System.out.println("**************");
        StringWriter writer = new StringWriter();
        engine.writeFile(writer, dogs, Dog.class);
        // Display the content of writer.
        System.out.println(writer.toString());
        System.out.println();
    }

    private static List<Dog> readDogs() throws CsvErrorsExceededException {
        // Read the dogs from a CSV File and put it into a list of objects
        System.out.println("**************");
        System.out.println("read dogs");
        System.out.println("**************");
        InputStream csvFile = getCsvFile();
        List<Dog> dogs = engine.parseInputStream(csvFile, Dog.class).getObjects();
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }
        System.out.println();
        return dogs;
    }

    private static InputStream getCsvFile() {
        return ChangeReaderExample.class.getResourceAsStream("/fr/ybonnel/csvengine/sample/basic/dogsWithComment.csv");
    }

}
