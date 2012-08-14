/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     ybonnel - initial API and implementation
 */
package fr.ybonnel.csvengine.sample.batch;

import fr.ybonnel.csvengine.CsvEngine;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import fr.ybonnel.csvengine.model.InsertBatch;
import fr.ybonnel.csvengine.sample.basic.Dog;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This is a sample for batch process with CsvEngine.
 */
public class BatchSample {

    /**
     * Create the CsvEngine for the class Dog.
     */
    public static CsvEngine engine = new CsvEngine(Dog.class);

    public static void main(String[] args) throws CsvErrorsExceededException {
        readFirstLines();

        readBatch();
    }

    private static void readFirstLines() throws CsvErrorsExceededException {
        System.out.println("**************");
        System.out.println("read the first two lines of dogs.csv");
        System.out.println("**************");
        List<Dog> dogs = engine.parseFirstLinesOfInputStream(getCsvFile(), Dog.class, 2).getObjects();
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }
    }

    private static void readBatch() throws CsvErrorsExceededException {
        System.out.println("**************");
        System.out.println("read dogs.csv by batch of 2 lines");
        System.out.println("**************");
        engine.parseFileAndHandleBatch(new InputStreamReader(getCsvFile()), Dog.class, new InsertBatch<Dog>() {

            int count = 1;

            public void handleBatch(List<Dog> objects) {
                System.out.println("Content of batch " + count);
                for (Dog dog : objects) {
                    System.out.println(dog.toString());
                }
                count++;
            }
        }, 2);
    }

    private static InputStream getCsvFile() {
        return BatchSample.class.getResourceAsStream("/fr/ybonnel/csvengine/sample/basic/dogs.csv");
    }
}
