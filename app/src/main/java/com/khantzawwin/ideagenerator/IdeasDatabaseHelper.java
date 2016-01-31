package com.khantzawwin.ideagenerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by khantzawwin on 30/1/16.
 */
public class IdeasDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ideasDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE=" INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_IDEATILTETABLE =
            "CREATE TABLE " + IdeasTitleDBAdapter.IdeaEntry.TABLE_NAME + " (" +
                    IdeasTitleDBAdapter.IdeaEntry._ID + " INTEGER PRIMARY KEY," +
                    IdeasTitleDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                    IdeasTitleDBAdapter.IdeaEntry.COLUMN_DATE_DESCRIPTION + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_IDEATABLE =
            "CREATE TABLE " + IdeasDBAdapter.IdeaEntry.TABLE_NAME + " (" +
                    IdeasDBAdapter.IdeaEntry._ID + " INTEGER PRIMARY KEY," +
                    IdeasDBAdapter.IdeaEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    IdeasDBAdapter.IdeaEntry.COLUMN_NAME_IDEA + TEXT_TYPE + COMMA_SEP +
                    IdeasDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE  +
                    " )";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table Customer "
            + "(_id integer primary key autoincrement, "
            + "Name text not null, Company text not null, "
            + "Address text not null, CreditLimit real);";

    public IdeasDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_IDEATILTETABLE);
        database.execSQL(SQL_CREATE_IDEATABLE);

    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(IdeasDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS Customer");
        onCreate(database);
    }
}
