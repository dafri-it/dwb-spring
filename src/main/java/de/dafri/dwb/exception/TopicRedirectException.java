package de.dafri.dwb.exception;

import de.dafri.dwb.domain.TopicDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class TopicRedirectException extends RuntimeException {

    private final TopicDetail topicDetail;

    public TopicRedirectException(TopicDetail topicDetail) {
        this.topicDetail = topicDetail;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }
}
