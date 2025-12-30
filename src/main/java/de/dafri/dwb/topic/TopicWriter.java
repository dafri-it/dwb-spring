package de.dafri.dwb.topic;

import de.dafri.dwb.converter.DocConverter;
import de.dafri.dwb.data.model.TopicDetailModel;
import de.dafri.dwb.data.repository.TopicRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TopicWriter {

    private final static Logger logger = LoggerFactory.getLogger(TopicWriter.class);

    @Value("${dwb.static.topics}")
    private String topicDir;

    private final TopicRepository topicRepository;
    private final DocConverter docConverter;

    public TopicWriter(TopicRepository topicRepository, DocConverter docConverter) {
        this.topicRepository = topicRepository;
        this.docConverter = docConverter;
    }

    @PostConstruct
    private void writeTopics() {
        File dir = new File(topicDir);
        logger.info("writing static topics to {}", dir.getAbsolutePath());
        topicRepository.findAll().forEach(topic -> writeTopic(topic, dir));
        logger.info("writing static topics finished");
    }

    private void writeTopic(TopicDetailModel topic, File dir) {
        docConverter.convertHtmlToMarkdown(topic.text(), dir, topic.nr() + ".md");
    }
}
