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

import fr.ybonnel.csvengine.annotation.*;
import fr.ybonnel.csvengine.validator.ValidatorRegExp;
import fr.ybonnel.csvengine.validator.ValidatorSize;

@CsvFile
public class DogValid {
    // The column "name" is mandatory.
    @CsvColumn(value = "name", mandatory = true)
    private String name;

    // The column "race" is validate with a specific adaptor (ValidatorRace).
    @CsvValidation(ValidatorRace.class)
    @CsvColumn("race")
    private String race;

    // The column proprietary has two validator
    // ValidatorSize to validate proprietary on size of it :
    //      minimum : 2 characters,
    //      maximum : 30 characters.
    // ValidatorRegExp to validate proprietary with a regexp :
    //      the proprietary must contain a list of words separate with a whitespace,
    //      each word begin with a upper characters and and follow by at least 1 lower characters.
    @CsvValidations({
            @CsvValidation(value = ValidatorSize.class, params = {
                    @CsvParam(name = ValidatorSize.PARAM_MIN_SIZE, value = "2"),
                    @CsvParam(name = ValidatorSize.PARAM_MAX_SIZE, value = "30")
            }),
            @CsvValidation(value = ValidatorRegExp.class, params = {
                    @CsvParam(name = ValidatorRegExp.PARAM_PATTERN,
                            value = "\\p{javaUpperCase}\\p{javaLowerCase}+(\\p{javaWhitespace}\\p{javaUpperCase}\\p{javaLowerCase}+)*")
            })
    })
    @CsvColumn("proprietary")
    private String proprietary;

    @Override
    public String toString() {
        return "DogValid{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", proprietary='" + proprietary + '\'' +
                '}';
    }
}
