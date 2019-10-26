package de.dotsource.wtf.service.impl;

import com.intellij.openapi.project.Project;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.service.FeedbackService;

public class GitFeedbackService implements FeedbackService {
    public GitFeedbackService(Project project) {
    }


    @Override
    public FileMetricEntry storeFeedback(FeedbackEntry entry) {
        return null;
    }

    @Override
    public FileMetricEntry getFeedbackForFile(String Path) {
        return null;
    }
}
