package com.nezspencer.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            Recipe var = new Recipe();
            var.image = source.readString();
            var.servings = source.readInt();
            var.name = source.readString();
            var.ingredients = source.createTypedArray(RecipeIngredients.CREATOR);
            var.id = source.readInt();
            var.steps = source.createTypedArray(RecipeSteps.CREATOR);
            return var;
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    private String image;
    private int servings;
    private String name;
    private RecipeIngredients[] ingredients;
    private int id;
    private RecipeSteps[] steps;

    public String getImage() {
        return this.image;
    }

    public int getServings() {
        return this.servings;
    }

    public String getName() {
        return this.name;
    }

    public RecipeIngredients[] getIngredients() {
        return this.ingredients;
    }

    public int getId() {
        return this.id;
    }

    public RecipeSteps[] getSteps() {
        return this.steps;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeInt(this.servings);
        dest.writeString(this.name);
        dest.writeTypedArray(this.ingredients, flags);
        dest.writeInt(this.id);
        dest.writeTypedArray(this.steps, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
