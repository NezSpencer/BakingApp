package com.nezspencer.bakingapp.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nezspencer on 6/24/17.
 */

public class RecipeContract {

    public static final String AUTHORITY ="com.nezspencer.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_RECIPE = TableRecipe.TABLE_NAME;
    public static final String PATH_STEP = TableStep.TABLE_NAME;
    public static final String PATH_INGREDIENT =TableIngredient.TABLE_NAME;

    public static final class TableRecipe implements BaseColumns{
        public static final String TABLE_NAME="recipe";
        public static final String COLUMN_NAME ="recipeName";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String SORT_ORDER_DEFAULT = _ID+" ASC";

    }

    public static final class TableStep implements BaseColumns{
        public static final String TABLE_NAME="step";
        public static final String COLUMN_NAME ="recipeName";
        public static final String COLUMN_VIDEOURL = "videoUrl";
        public static final String COLUMN_SHORTDESC = "shortDescription";
        public static final String COLUMN_DESC ="description";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String SORT_ORDER_DEFAULT = _ID+" ASC";
    }

    public static final class TableIngredient implements BaseColumns {
        public static final String TABLE_NAME="ingredients";
        public static final String COLUMN_QUANTITY ="quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_NAME ="recipeName";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;
        public static final String SORT_ORDER_DEFAULT = _ID+" ASC";

    }
}
