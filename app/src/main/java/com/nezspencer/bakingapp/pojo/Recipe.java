package com.nezspencer.bakingapp.pojo;

public class Recipe implements java.io.Serializable {
    private static final long serialVersionUID = -6415196650088006631L;
    private String image;
    private int servings;
    private String name;
    private RecipeIngredients[] ingredients;
    private int id;
    private RecipeSteps[] steps;

    public String getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeIngredients[] getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(RecipeIngredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeSteps[] getSteps() {
        return this.steps;
    }

    public void setSteps(RecipeSteps[] steps) {
        this.steps = steps;
    }
}
