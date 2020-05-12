package com.example.newsreader;

import androidx.annotation.NonNull;

/**
 * News class for data coming from the RSS Feed
 */

public class News {

    private String title;
    private String description;
    private String publicationDate;

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + this.title + "\n" +
                "Desc: " + this.description + "\n" +
                "Date: " + this.publicationDate;
    }
}
