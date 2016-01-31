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
public class IdeasDBAdapter {

    public static abstract class IdeaEntry implements BaseColumns {
        public static final String TABLE_NAME = "idea";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IDEA = "idea";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    private Context context;
    private SQLiteDatabase database;
    private IdeasDatabaseHelper dbHelper;

    public IdeasDBAdapter(Context context) {
        this.context = context;
    }

    public IdeasDBAdapter open() throws SQLException {
        dbHelper = new IdeasDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String title,String idea,String description) {
        ContentValues values = new ContentValues();
        values.put(IdeaEntry.COLUMN_NAME_TITLE, title);
        values.put(IdeaEntry.COLUMN_NAME_IDEA, idea);
        values.put(IdeaEntry.COLUMN_NAME_DESCRIPTION, description);
        return values;
    }

    public long createIdea(String title, String idea, String description) {
        ContentValues initialValues = createContentValues(title,idea,description);

        long row = database.insert(IdeaEntry.TABLE_NAME, null, initialValues);
        Log.i("DATABASE", Long.toString(row));
        return row;
    }

    public Cursor fetchAllIdeas() {
        return database.query(IdeaEntry.TABLE_NAME, new String[] { IdeaEntry._ID,
                        IdeaEntry.COLUMN_NAME_IDEA,IdeaEntry.COLUMN_NAME_DESCRIPTION },
                null, null, null, null, null);
    }

    public Cursor fetchIdeasByTitleID( String title) throws java.sql.SQLException {
        Cursor mCursor = database.query(true, IdeaEntry.TABLE_NAME, new String[] {
                        IdeaEntry._ID,
                        IdeaEntry.COLUMN_NAME_IDEA,
                        IdeaEntry.COLUMN_NAME_DESCRIPTION,
                        IdeaEntry.COLUMN_NAME_TITLE },
                IdeaEntry.COLUMN_NAME_TITLE + "=?", new String[] { title } ,
                null, null, null, null);
        return mCursor;
    }
}
