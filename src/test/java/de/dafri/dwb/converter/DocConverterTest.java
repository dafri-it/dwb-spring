package de.dafri.dwb.converter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DocConverterTest {

    @Test
    void convertHtmlToMarkdown() {
        new DocConverter().convertHtmlToMarkdown("<h1>hi</h1>", Path.of("/home/david/tmp").toFile(), "test.md");
    }
}