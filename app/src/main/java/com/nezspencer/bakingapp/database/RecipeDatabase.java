package com.nezspencer.bakingapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nezspencer on 6/24/17.
 */

public class RecipeDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME ="recipe.db";
    private static final int DB_VERSION = 2;

    public RecipeDatabase(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_RECIPE = "CREATE TABLE "+RecipeContract.TableRecipe.TABLE_NAME+
                " ( "+ RecipeContract.TableRecipe._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                RecipeContract.TableRecipe.COLUMN_NAME +" TEXT NOT NULL );";

        final String CREATE_TABLE_STEP = "CREATE TABLE "+ RecipeContract.TableStep.TABLE_NAME+" ( "+
                RecipeContract.TableStep._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                RecipeContract.TableStep.COLUMN_NAME+" TEXT NOT NULL, "+
                RecipeContract.TableStep.COLUMN_VIDEOURL+" TEXT, "+
                RecipeContract.TableStep.COLUMN_DESC+" TEXT NOT NULL, "+
                RecipeContract.TableStep.COLUMN_SHORTDESC+" TEXT NOT NULL );";

        final String CREATE_TABLE_INGREDIENT = "CREATE TABLE "+ RecipeContract.TableIngredient
                .TABLE_NAME+" ( "+ RecipeContract.TableIngredient._ID+" INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, "+ RecipeContract.TableIngredient.COLUMN_NAME+" TEXT NOT NULL, "+
                RecipeContract.TableIngredient.COLUMN_MEASURE+" TEXT NOT NULL, "+
                RecipeContract.TableIngredient.COLUMN_QUANTITY+" REAL NOT NULL, "+
                RecipeContract.TableIngredient.COLUMN_INGREDIENT+" TEXT NOT NULL );";

        sqLiteDatabase.execSQL(CREATE_TABLE_RECIPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_STEP);
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ RecipeContract.TableRecipe.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ RecipeContract.TableStep.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ RecipeContract.TableIngredient.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
