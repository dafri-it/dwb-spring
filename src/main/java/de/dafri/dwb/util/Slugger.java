package de.dafri.dwb.util;

public class Slugger {

    public static String slug(String text) {
        return text
                .replace("ö", "oe")
                .replace("ä", "ae")
                .replace("ü", "ue")
                .replace("Ö", "Oe")
                .replace("Ä", "Ae")
                .replace("Ü", "Ue")
                .replace("ß", "ss")
                .replaceAll("[^a-zA-Z0-9]+", "-")
                .toLowerCase()
                .trim();
    }

}
