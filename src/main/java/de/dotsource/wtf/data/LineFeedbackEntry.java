package de.dotsource.wtf.data;

public class LineFeedbackEntry {
    private Double Rating;
    private String AdditionalInformation;

    public LineFeedbackEntry(Double rating, String additionalInformation) {
        Rating = rating;
        AdditionalInformation = additionalInformation;
    }

    public Double getRating() {
        return Rating;
    }

    public String getAdditionalInformation() {
        return AdditionalInformation;
    }
}
