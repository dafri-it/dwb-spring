package de.dafri.dwb.util;

public class Slugger {

    public static String slug(String text) {
        String replaced = text
                .replaceAll("ö", "oe")
                .replaceAll("ä", "ae")
                .replaceAll("ü", "ue")
                .replaceAll("Ö", "Oe")
                .replaceAll("Ä", "Ae")
                .replaceAll("Ü", "Ue")
                .replaceAll("ß", "ss")
                .replaceAll("[^a-zA-Z0-9]", " ").toLowerCase()
                ;

        replaced = replaced.trim();
        replaced = replaced.replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ");
        replaced = replaced.replaceAll(" ", "-");

        return replaced;
    }

}
