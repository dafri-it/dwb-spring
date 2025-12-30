package de.dafri.dwb.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@Component
public class DocConverter {

    private final static Logger logger = LoggerFactory.getLogger(DocConverter.class);

    public void convertHtmlToMarkdown(String html, File dir, String outName) {
        try {
            File htmlFile = writeHtmlToFile(html);

            File outFile = new File(dir, outName);

            runPandoc(htmlFile, outFile);
        } catch (IOException e) {
            logger.error("Markdown-File {} not writable", outName, e);
            throw new RuntimeException(e);
        }
    }

    private void runPandoc(File htmlFile, File outfile) throws IOException {
        String[] command = {"pandoc", "-f", "html", htmlFile.getAbsolutePath(), "-o", outfile.getAbsolutePath()};
        logger.info("command {}", Arrays.stream(command).toList());
        new ProcessBuilder(command).start();
    }

    private File writeHtmlToFile(String html) throws IOException {
        return Files.writeString(Files.createTempFile("html", "file"), html).toFile();
    }

}
