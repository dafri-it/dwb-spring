package de.dafri.dwb.frontend;

import de.dafri.dwb.domain.Category;
import de.dafri.dwb.domain.TopicDetail;
import de.dafri.dwb.exception.CategoryRedirectException;
import de.dafri.dwb.exception.TopicRedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CategoryRedirectException.class)
    public String handleCategoryRedirectException(CategoryRedirectException ex) {
        Category category = ex.getCategory();

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

        log.info("redirecting from {} to category {} (api={})", currentUrl, category.slug(), ex.isApi());

        if (ex.isApi()) {
            return "redirect:/api/category/" + category.slug();
        }
        return "redirect:/category/" + category.slug();
    }

    @ExceptionHandler(TopicRedirectException.class)
    public String handleTopicRedirectException(TopicRedirectException ex) {
        TopicDetail topicDetail = ex.getTopicDetail();

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

        log.info("redirecting from {} to topic {} (api={})", currentUrl, topicDetail.slug(), ex.isApi());

        if (ex.isApi()) {
            return "redirect:/api/topic/" + topicDetail.slug();
        }

        return "redirect:/topic/" + topicDetail.slug();
    }
    
}
