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
package fr.ybonnel.csvengine.sample.validation;

import fr.ybonnel.csvengine.CsvEngine;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import fr.ybonnel.csvengine.model.*;
import fr.ybonnel.csvengine.model.Error;

import java.io.IOException;
import java.io.InputStream;

public class ValidationExample {

    // Just a specific test class to simulate the InputStream with a String.
    private static class StringStream extends InputStream {
        private String string;
        private int count = 0;

        @Override
        public int read() throws IOException {
            if (count >= string.length()) {
                return -1;
            }
            return string.charAt(count++);
        }

        public StringStream(String string) {
            super();
            this.string = string;
        }
    }

    // Header for all csv.
    private final static String HEADER = "name,race,proprietary";

    // Creation of engine.
    private static CsvEngine engine = new CsvEngine(DogValid.class);

    public static void main(String[] args) throws CsvErrorsExceededException {
        readCorrectLine();

        readIncorrectLine();
    }

    private static void readCorrectLine() throws CsvErrorsExceededException {
        // With a Correct line
        String correctLine = "Sumo,Bichon Maltais,Bernadette Chirac";
        InputStream stream = new StringStream(HEADER + "\n" + correctLine);
        // No exception for that case
        System.out.println("**************");
        System.out.println("validation with no error");
        System.out.println("**************");
        for (DogValid dog : engine.parseInputStream(stream, DogValid.class).getObjects()) {
            System.out.println(dog);
        }
        System.out.println();
    }

    private static void readIncorrectLine() {
        // With an incorrect line
        // Not name,
        // Bad race
        // Too long proprietary
        String incorrectLine = ",Bad Race,Bad proprietary";
        InputStream stream =  new StringStream(HEADER + "\n" + incorrectLine);
        System.out.println("**************");
        System.out.println("validation with an incorrect line");
        System.out.println("**************");
        try {
            engine.parseInputStream(stream, DogValid.class).getObjects();
        } catch (CsvErrorsExceededException errors) {
            System.out.println("Exception message : " + errors.getMessage());
            for (Error error : errors.getErrors()) {
                System.out.println("CsvLine : " + error.getCsvLine());
                for (String message : error.getMessages()) {
                    System.out.println("\t" + message);
                }
            }
        }
        System.out.println();
    }
}
