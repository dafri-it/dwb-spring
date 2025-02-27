package de.dafri.dwb.util;

public class Slugger {

    public static String slug(String text) {
        return text
                .replaceAll("ö", "oe")
                .replaceAll("ä", "ae")
                .replaceAll("ü", "ue")
                .replaceAll("Ö", "Oe")
                .replaceAll("Ä", "Ae")
                .replaceAll("Ü", "Ue")
                .replaceAll("ß", "ss")
                .replaceAll("[^a-zA-Z0-9]", "-").toLowerCase()
                .replaceAll("---", "-")
                .replaceAll("--", "-");
    }

}
