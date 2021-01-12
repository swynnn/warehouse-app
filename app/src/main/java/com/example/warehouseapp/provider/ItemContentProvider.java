package com.example.warehouseapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ItemContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "fit2081.app.SHERWYN";
    //Uniform Resource Identifier (URI) is a string of characters used to identify a resource
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    ItemDatabase db;
    private final String tableName = "items";

    public ItemContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        int deletionCount;
        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(tableName, selection, selectionArgs);
        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
        // for invalid/ inaccessable uri (checking purpose)
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");

        long rowId = db
                .getOpenHelper()                //lib, for android to access database
                .getWritableDatabase()          //chg information
                .insert(tableName, 0, values);  
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = ItemDatabase.getDatabase(getContext());        //which database the other app is allowed to access
        // return true if the provider was successfully loaded
        return true;                                        //can use this database to do other functions later (insert,delete, insert)
    }

    @Override //extend general uri with other table name eg. user
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();      //manipulate date, eg. need builder to execute query to access data
        builder.setTables(tableName);                               //let app know which table you want the builder to refer to
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null); //allow us to run the query to access data
        final Cursor cursor = db      //when successfully retrieve item, obj will put inside cursor
                                      // cursor return type of this method, get the object u query and put in cursor
                .getOpenHelper()
                .getReadableDatabase()      //read only
                .query(query, selectionArgs);
        return cursor;

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(tableName, 0, values, selection, selectionArgs);
        return updateCount;
    }
}
