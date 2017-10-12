package se.chalmers.bookreview.model;

/**
 * Created by Wissam on 2017-10-12.
 */

public class Language {
    private int id;
    private String name;

    public Language(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
