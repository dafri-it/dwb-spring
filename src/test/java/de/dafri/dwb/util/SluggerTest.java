package de.dafri.dwb.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SluggerTest {

    @Test
    void simple() {
        String hallo = Slugger.slug("Hallo");
        assertEquals("hallo", hallo);
    }

    @Test
    void space() {
        String str = Slugger.slug("Hallo Welt");
        assertEquals("hallo-welt", str);
    }

    @Test
    void multipleSpacesBetween() {
        String str = Slugger.slug("Hallo   Welt");
        assertEquals("hallo-welt", str);
    }


    @Test
    void multipleSpacesBetween2() {
        String str = Slugger.slug("Hallo    Welt");
        assertEquals("hallo-welt", str);
    }

    @Test
    void specialCharacters() {
        String str = Slugger.slug("Hallo & Welt");
        assertEquals("hallo-welt", str);
    }

    @Test
    void umlauts() {
        String str = Slugger.slug("Hallo Wölt");
        assertEquals("hallo-woelt", str);
    }

}