package com.khantzawwin.ideagenerator;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

public class IdeaListActivity extends AppCompatActivity {

    private String rowId;
    private ListView dailyIdeaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dailyIdeaList=(ListView)findViewById(R.id.DailyideaList);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(IdeasDBAdapter.IdeaEntry.COLUMN_NAME_TITLE)) {
            rowId = extras.getString(IdeasDBAdapter.IdeaEntry.COLUMN_NAME_TITLE);
            try {
                getIdeas(rowId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void getIdeas(String title) throws SQLException {
        IdeasDBAdapter db=new IdeasDBAdapter(this);
        db.open();

        Cursor cursor=db.fetchIdeasByTitleID(title);
        SimpleCursorAdapter myadapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor,
                new String[] { IdeasDBAdapter.IdeaEntry.COLUMN_NAME_IDEA, IdeasDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION },
                new int[] { android.R.id.text1, android.R.id.text2}, 0);

        dailyIdeaList.setAdapter(myadapter);

        db.close();
    }

}
