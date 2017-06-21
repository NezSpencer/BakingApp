package com.nezspencer.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeSteps implements Parcelable {
    public static final Creator<RecipeSteps> CREATOR = new Creator<RecipeSteps>() {
        @Override
        public RecipeSteps createFromParcel(Parcel source) {
            RecipeSteps var = new RecipeSteps();
            var.videoURL = source.readString();
            var.description = source.readString();
            var.id = source.readInt();
            var.shortDescription = source.readString();
            var.thumbnailURL = source.readString();
            return var;
        }

        @Override
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };
    private String videoURL;
    private String description;
    private int id;
    private String shortDescription;
    private String thumbnailURL;

    public String getVideoURL() {
        return this.videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL() {
        return this.thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
