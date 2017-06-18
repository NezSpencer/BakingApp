package com.nezspencer.bakingapp.pojo;

/**
 * Created by nezspencer on 6/7/17.
 */

public class Ingredient
{
    private double quantity;

    public double getQuantity() { return this.quantity; }

    public void setQuantity(double quantity) { this.quantity = quantity; }

    private String measure;

    public String getMeasure() { return this.measure; }

    public void setMeasure(String measure) { this.measure = measure; }

    private String ingredient;

    public String getIngredient() { return this.ingredient; }

    public void setIngredient(String ingredient) { this.ingredient = ingredient; }
}

