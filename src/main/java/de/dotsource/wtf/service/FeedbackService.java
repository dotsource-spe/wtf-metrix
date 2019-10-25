package de.dotsource.wtf.service;

import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;

public interface FeedbackService {

    FileMetricEntry storeFeedback(FeedbackEntry entry);

    FileMetricEntry getFeedbackForFile(String Path);

}
