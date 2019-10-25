package de.dotsource.wtf.data;

import java.time.LocalDateTime;

public class FeedbackEntry {

    private Integer Line;
    private String Path;
    private String Comment;
    private String UserName;
    private LocalDateTime TimeStamp;
    private String Repository;
    private String Revision;

    public Integer getLine() {
        return Line;
    }

    public void setLine(Integer line) {
        Line = line;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public LocalDateTime getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getRepository() {
        return Repository;
    }

    public void setRepository(String repository) {
        Repository = repository;
    }

    public String getRevision() {
        return Revision;
    }

    public void setRevision(String revision) {
        Revision = revision;
    }
}
