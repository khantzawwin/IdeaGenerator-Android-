package com.khantzawwin.ideagenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by khantzawwin on 30/1/16.
 */
public class IdeasTitleDBAdapter  {

    /* Inner class that defines the table contents */
    public static class IdeaEntry implements BaseColumns {
        public static final String TABLE_NAME = "ideatitle";
        public static final String COLUMN_DATE_DESCRIPTION = "date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    private Context context;
    private SQLiteDatabase database;
    private IdeasDatabaseHelper dbHelper;

    public IdeasTitleDBAdapter(Context context) {
        this.context = context;
    }

    public IdeasTitleDBAdapter open() throws SQLException {
        dbHelper = new IdeasDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String date,String description) {
        ContentValues values = new ContentValues();
        values.put(IdeaEntry.COLUMN_DATE_DESCRIPTION, date);
        values.put(IdeaEntry.COLUMN_NAME_DESCRIPTION, description);
        return values;
    }

    public long createIdeaTitle(String date,String description) {
        ContentValues initialValues = createContentValues(date,description);

        long row = database.insert(IdeaEntry.TABLE_NAME, null, initialValues);
        Log.i("DATABASE", Long.toString(row));
        return row;
    }

    public Cursor fetchTodayIdeaTitle( String date) throws java.sql.SQLException {
        Cursor mCursor = database.query(true, IdeaEntry.TABLE_NAME, new String[] {
                        IdeaEntry._ID,
                        IdeaEntry.COLUMN_NAME_DESCRIPTION,
                        IdeaEntry.COLUMN_DATE_DESCRIPTION },
                IdeaEntry.COLUMN_DATE_DESCRIPTION + "=?", new String[] { date } ,
                null, null, null, null);
        if (mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public Cursor fetchAllIdeaTitles() {
        return database.query(IdeaEntry.TABLE_NAME, new String[] { IdeaEntry._ID,
                        IdeaEntry.COLUMN_DATE_DESCRIPTION,IdeaEntry.COLUMN_NAME_DESCRIPTION },
                null, null, null, null, null);
    }
}
