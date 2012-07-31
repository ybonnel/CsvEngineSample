package fr.ybo.csvengine.sample.basic;

import fr.ybo.csvengine.CsvEngine;
import fr.ybo.csvengine.exception.CsvErrorsExceededException;
import fr.ybo.csvengine.model.InsertObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

/**
 * This is the BasicExample for CsvEngine
 */
public class BasicExample {

    /**
     * Create the CsvEngine for the class Dog.
     */
    public static CsvEngine engine = new CsvEngine(Dog.class);

    public static void main(String[] args) throws CsvErrorsExceededException {
        System.out.println("**************");
        System.out.println("read dogs");
        InputStream csvFile = BasicExample.class.getResourceAsStream("/fr/ybo/csvengine/sample/basic/dogs.csv");
        List<Dog> dogs = engine.parseInputStream(csvFile, Dog.class).getObjects();
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }

        // Write dogs into CSV File.
        System.out.println("**************");
        System.out.println("write dogs");
        StringWriter writer = new StringWriter();
        engine.writeFile(writer, dogs, Dog.class);
        // Display the content of writer.
        System.out.println(writer.toString());

        // Use handler on row.
        System.out.println("**************");
        System.out.println("read dogs with handler");
        InputStream stream = BasicExample.class.getResourceAsStream("/fr/ybo/csvengine/sample/basic/dogs.csv");
        engine.parseFileAndInsert(new InputStreamReader(stream), Dog.class, new InsertObject<Dog>() {
            @Override
            public void insertObject(Dog dog) {
                System.out.println(dog.toString());
            }
        });

    }

}
