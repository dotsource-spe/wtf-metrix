package de.dotsource.wtf.service.impl;

import de.dotsource.wtf.client.api.FeedbackApi;
import de.dotsource.wtf.client.api.MetricsApi;
import de.dotsource.wtf.client.model.Feedback;
import de.dotsource.wtf.client.model.Metrics;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.service.FeedbackService;
import com.intellij.openapi.project.Project;

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

        // invoke REST method to post feedback
        feedbackApi.postFeedback(feedback);

        System.out.println("Success");

        // TODO: get answer to process File Metric

        return null;
    }

    /**
     * Create REST client and invoke GET method.
     *
     * @param Path
     * @return
     */
    @Override
    public FileMetricEntry getFeedbackForFile(String Path) {
        // TODO: invoke REST method to get metrics
        //metricsApi.getMetrics(...);

        Metrics metrics = new Metrics();

        // create metrics entry from values returned from the REST client
        FileMetricEntry fileMetricEntry = new FileMetricEntry();
        //fileMetricEntry.put()

        // TODO: process metrics

        return null;
    }
}
