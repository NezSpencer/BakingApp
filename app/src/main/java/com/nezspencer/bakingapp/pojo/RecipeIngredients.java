package com.nezspencer.bakingapp.pojo;

public class RecipeIngredients implements java.io.Serializable {
    private static final long serialVersionUID = -4161157190999415102L;
    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
