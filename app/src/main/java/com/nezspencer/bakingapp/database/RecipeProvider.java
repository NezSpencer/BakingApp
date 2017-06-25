package com.nezspencer.bakingapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by nezspencer on 6/24/17.
 */

public class RecipeProvider extends ContentProvider {


    private static final int RECIPE = 100;
    private static final int RECIPE_WITH_ID = 101;
    private static final int STEP = 200;
    private static final int STEP_WITH_ID =201;
    private static final int INGREDIENT =300;
    private static final int INGREDIENT_WITH_ID =301;


    private RecipeDatabase recipeDatabase;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_RECIPE,RECIPE);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_RECIPE+"/#",RECIPE_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_STEP,STEP);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_STEP+"/#",STEP_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_INGREDIENT,INGREDIENT);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_INGREDIENT+"/#",INGREDIENT_WITH_ID);

    }

    @Override
    public boolean onCreate() {
        recipeDatabase = new RecipeDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = recipeDatabase.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)){

            case RECIPE:
                builder.setTables(RecipeContract.TableRecipe.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = RecipeContract.TableRecipe.SORT_ORDER_DEFAULT;
                break;

            case RECIPE_WITH_ID:
                builder.setTables(RecipeContract.TableRecipe.TABLE_NAME);
                builder.appendWhere(RecipeContract.TableRecipe._ID+" = "+uri.getLastPathSegment());
                break;

            case STEP:
                builder.setTables(RecipeContract.TableStep.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = RecipeContract.TableStep.SORT_ORDER_DEFAULT;
                break;

            case STEP_WITH_ID:
                builder.setTables(RecipeContract.TableStep.TABLE_NAME);
                builder.appendWhere(RecipeContract.TableStep._ID+" = "+uri.getLastPathSegment());
                break;

            case INGREDIENT:
                builder.setTables(RecipeContract.TableIngredient.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = RecipeContract.TableIngredient.SORT_ORDER_DEFAULT;
                break;

            case INGREDIENT_WITH_ID:
                builder.setTables(RecipeContract.TableIngredient.TABLE_NAME);
                builder.appendWhere(RecipeContract.TableIngredient._ID+" = "+ uri.getLastPathSegment());
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }

        Cursor cursor = builder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){

            case RECIPE:
                return RecipeContract.TableRecipe.CONTENT_TYPE;

            case RECIPE_WITH_ID:
                return RecipeContract.TableRecipe.CONTENT_ITEM_TYPE;

            case STEP:
                return RecipeContract.TableStep.CONTENT_TYPE;

            case STEP_WITH_ID:
                return RecipeContract.TableStep.CONTENT_ITEM_TYPE;

            case INGREDIENT:
                return RecipeContract.TableIngredient.CONTENT_TYPE;

            case INGREDIENT_WITH_ID:
                return RecipeContract.TableIngredient.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase db = recipeDatabase.getWritableDatabase();
        long id;
        Uri returnUri;

        switch (uriMatcher.match(uri)){

            case RECIPE:
                id  = db.insert(RecipeContract.TableRecipe.TABLE_NAME,
                        null,contentValues);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(uri,+id);
                }
                else throw new SQLException("Unable to insert row into "+uri);
                break;

            case STEP:
                id  = db.insert(RecipeContract.TableStep.TABLE_NAME,
                        null,contentValues);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(uri,+id);
                }
                else throw new SQLException("Unable to insert row into "+uri);
                break;

            case INGREDIENT:
                id  = db.insert(RecipeContract.TableIngredient.TABLE_NAME,
                        null,contentValues);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(uri,+id);
                }
                else throw new SQLException("Unable to insert row into "+uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = recipeDatabase.getWritableDatabase();
        int numOfRowsDeleted;
        String where;

        switch (uriMatcher.match(uri)){

            case RECIPE:
                numOfRowsDeleted = db.delete(RecipeContract.TableRecipe.TABLE_NAME,
                        whereClause,selectionArgs);
                break;
            case RECIPE_WITH_ID:
                where = RecipeContract.TableRecipe._ID +" = "+uri.getLastPathSegment();
                if (!TextUtils.isEmpty(whereClause))
                    where += " AND "+whereClause;
                numOfRowsDeleted = db.delete(RecipeContract.TableRecipe.TABLE_NAME,where,selectionArgs);
                break;

            case STEP:
                numOfRowsDeleted = db.delete(RecipeContract.TableStep.TABLE_NAME,
                        whereClause,selectionArgs);
                break;
            case STEP_WITH_ID:
                where = RecipeContract.TableStep._ID +" = "+uri.getLastPathSegment();
                if (!TextUtils.isEmpty(whereClause))
                    where += " AND "+whereClause;
                numOfRowsDeleted = db.delete(RecipeContract.TableStep.TABLE_NAME,where,selectionArgs);
                break;

            case INGREDIENT:
                numOfRowsDeleted = db.delete(RecipeContract.TableIngredient.TABLE_NAME,
                        whereClause,selectionArgs);
                break;
            case INGREDIENT_WITH_ID:
                where = RecipeContract.TableIngredient._ID +" = "+uri.getLastPathSegment();
                if (!TextUtils.isEmpty(whereClause))
                    where += " AND "+whereClause;
                numOfRowsDeleted = db.delete(RecipeContract.TableIngredient.TABLE_NAME,where,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);

        }

        if (numOfRowsDeleted > 0)
        getContext().getContentResolver().notifyChange(uri,null);

        return numOfRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        SQLiteDatabase db = recipeDatabase.getWritableDatabase();
        int numOfRowsInserted = 0;

        switch (uriMatcher.match(uri)){

            case RECIPE:
                db.beginTransaction();
                try{
                    for (ContentValues value: values){

                        long id = db.insert(RecipeContract.TableRecipe.TABLE_NAME,null,value);

                        if (id != -1)
                            numOfRowsInserted++;
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }

                if (numOfRowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri,null);
                return numOfRowsInserted;

            case STEP:
                db.beginTransaction();
                try{
                    for (ContentValues value: values){
                        long id = db.insert(RecipeContract.TableStep.TABLE_NAME,
                                null,value);

                        if (id != -1)
                            numOfRowsInserted++;
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }

                if (numOfRowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri,null);
                return numOfRowsInserted;

            case INGREDIENT:

                db.beginTransaction();

                try{
                    for (ContentValues value: values){
                        long id = db.insert(RecipeContract.TableIngredient.TABLE_NAME,
                                null,value);
                        if (id != -1)
                            numOfRowsInserted++;
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }

                if (numOfRowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri,null);

                return numOfRowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }


    }
}
