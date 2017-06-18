package com.nezspencer.bakingapp.pojo;

import java.util.ArrayList;

/**
 * Created by nezspencer on 6/7/17.
 */

public class Recipe {
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String name;

    public String getName() { return this.name; }


    private ArrayList<Ingredient> ingredients;

    public ArrayList<Ingredient> getIngredients() { return this.ingredients; }

    private ArrayList<Step> steps;

    public ArrayList<Step> getSteps() { return this.steps; }

    private int servings;

    public int getServings() { return this.servings; }

    private String image;

    public String getImage() { return this.image; }

}
