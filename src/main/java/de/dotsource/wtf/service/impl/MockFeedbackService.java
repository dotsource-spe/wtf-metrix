package de.dotsource.wtf.service.impl;

import com.intellij.openapi.project.Project;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.data.LineFeedbackEntry;
import de.dotsource.wtf.service.FeedbackService;

public class MockFeedbackService implements FeedbackService {
    public MockFeedbackService(Project project) {
        // nothing
    }

    @Override
    public FileMetricEntry storeFeedback(FeedbackEntry entry) {
        return null;
    }

    @Override
    public FileMetricEntry getFeedbackForFile(String Path) {
        FileMetricEntry result = new FileMetricEntry();
        result.put(1, new LineFeedbackEntry(5.0, "Who fucked this up?"));
        return result;
    }
}
