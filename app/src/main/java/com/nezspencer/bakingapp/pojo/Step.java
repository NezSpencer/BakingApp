package com.nezspencer.bakingapp.pojo;

/**
 * Created by nezspencer on 6/7/17.
 */

public class Step {
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String shortDescription;

    public String getShortDescription() { return this.shortDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    private String videoURL;

    public String getVideoURL() { return this.videoURL; }

    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }

    private String thumbnailURL;

    public String getThumbnailURL() { return this.thumbnailURL; }

    public void setThumbnailURL(String thumbnailURL) { this.thumbnailURL = thumbnailURL; }
}

