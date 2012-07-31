package fr.ybo.csvengine.sample.basic;

import fr.ybo.csvengine.annotation.CsvColumn;
import fr.ybo.csvengine.annotation.CsvFile;

@CsvFile
public class Dog {
    @CsvColumn("name")
    private String name;
    @CsvColumn("race")
    private String race;
    @CsvColumn("proprietary")
    private String proprietary;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", proprietary='" + proprietary + '\'' +
                '}';
    }
}
