package de.dafri.dwb.exception;

import de.dafri.dwb.domain.TopicDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class TopicRedirectException extends RuntimeException {

    private final TopicDetail topicDetail;
    private final boolean api;

    public TopicRedirectException(TopicDetail topicDetail, boolean api) {
        this.topicDetail = topicDetail;
        this.api = api;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }

    public boolean isApi() {
        return api;
    }
}
