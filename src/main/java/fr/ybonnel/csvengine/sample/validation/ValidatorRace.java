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

import fr.ybonnel.csvengine.validator.ValidateException;
import fr.ybonnel.csvengine.validator.ValidatorCsv;

import java.util.HashSet;
import java.util.Set;

/**
 * This is a sample of a specific validator
 */
public class ValidatorRace extends ValidatorCsv {

    private final static Set<String> POSSIBLE_RACES = new HashSet<String>() {{
        this.add("Bichon Maltais");
        this.add("Cocker");
        this.add("Fox Terrier");
        this.add("Berger Allemand");
    }};

    @Override
    public void validate(String field) throws ValidateException {
        if (!POSSIBLE_RACES.contains(field)) {
            throw new ValidateException("The race \"" + field + "\" isn't correct");
        }
    }
}
