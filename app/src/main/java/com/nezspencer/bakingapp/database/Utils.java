package com.nezspencer.bakingapp.database;

import android.content.Context;
import android.database.Cursor;

import com.nezspencer.bakingapp.pojo.RecipeIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nezspencer on 6/25/17.
 */

public class Utils {

    public static List<RecipeIngredients> getIngredients(String name, Context context){

        List<RecipeIngredients> ingredients = new ArrayList<>();
        String where = RecipeContract.TableIngredient.COLUMN_NAME+" = ?";
        String[] arg = {name};
        Cursor cursor = context.getContentResolver().query(RecipeContract.TableIngredient
                        .CONTENT_URI,
                null,where,arg, RecipeContract.TableIngredient.SORT_ORDER_DEFAULT);

        while ( cursor != null && cursor.moveToNext()){
            String measure = cursor.getString(cursor.getColumnIndex
                    (RecipeContract.TableIngredient.COLUMN_MEASURE));
            double quantity = cursor.getDouble(cursor.getColumnIndex
                    (RecipeContract.TableIngredient.COLUMN_QUANTITY));
            String ingred = cursor.getString(cursor.getColumnIndex(RecipeContract
                    .TableIngredient.COLUMN_INGREDIENT));

            RecipeIngredients ingredient = new RecipeIngredients();

            ingredient.setIngredient(ingred);
            ingredient.setMeasure(measure);
            ingredient.setQuantity(quantity);

            ingredients.add(ingredient);
        }
        if(cursor != null)
            cursor.close();

        return ingredients;
    }
}
