package de.dotsource.wtf.service.impl;

import de.dotsource.wtf.client.api.FeedbackApi;
import de.dotsource.wtf.client.api.MetricsApi;
import de.dotsource.wtf.client.model.Feedback;
import de.dotsource.wtf.client.model.Line;
import de.dotsource.wtf.client.model.Metrics;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.data.LineFeedbackEntry;
import de.dotsource.wtf.service.FeedbackService;
import com.intellij.openapi.project.Project;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetricServerFeedbackService implements FeedbackService {
    private FeedbackApi feedbackApi;
    private MetricsApi metricsApi;

    public MetricServerFeedbackService(Project project) {
        feedbackApi = new FeedbackApi();
        metricsApi = new MetricsApi();
    }

    /**
     * Create REST client and invoke POST method.
     *
     * @param entry
     * @return
     */
    @Override
    public FileMetricEntry storeFeedback(FeedbackEntry entry) {

        // create feedback object from entry
        Feedback feedback = new Feedback();
        feedback.setComment(entry.getComment());
        feedback.setLine(entry.getLine());
        feedback.setPath(entry.getPath());
        feedback.setRepository(entry.getRepository());
        feedback.setRevision(entry.getRevision());
        feedback.setUsername(entry.getUserName());
        feedback.setTimestamp(entry.getTimeStamp());
        feedback.setRating(Feedback.RatingEnum.THUMBSDOWN);

        // invoke REST method to post feedback
        feedbackApi.postFeedback(feedback);

        System.out.println("Success");

        // TODO: get answer to process File Metric

        return null;
    }

    /**
     * Create REST client and invoke GET method.
     */
    @Override
    public FileMetricEntry getFeedbackForFile(String path, String repository, String revision) {
        final Map<Integer, Line> metrics = metricsApi.getMetrics(repository, path, revision);

        final Map<Integer, LineFeedbackEntry> tmp = (metrics != null ? metrics.entrySet().stream() : Stream.<Map.Entry<Integer, Line>>empty())
                .map(Map.Entry::getValue)
                .collect(Collectors.toMap(Line::getLineno, this::convertToLineEntry));

        FileMetricEntry fme = new FileMetricEntry();
        fme.putAll(tmp);
        return fme;
    }

    private LineFeedbackEntry convertToLineEntry(Line line) {
        return new LineFeedbackEntry(line.getAverage(), createAdditionalInformationString(line));
    }

    private String createAdditionalInformationString(Line line) {
        return String.format("Thumbsup: %s, Thumbsdown: %s", line.getThumbsup(), line.getThumbsdown());
    }
}
