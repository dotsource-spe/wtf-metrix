package de.dotsource.wtf.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import org.jetbrains.annotations.NotNull;

public interface FeedbackService {
    static FeedbackService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, FeedbackService.class);
    }

    FileMetricEntry storeFeedback(FeedbackEntry entry);

    FileMetricEntry getFeedbackForFile(String Path);
}
