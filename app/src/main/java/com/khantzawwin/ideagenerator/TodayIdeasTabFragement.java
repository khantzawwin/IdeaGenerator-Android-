package com.khantzawwin.ideagenerator;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayIdeasTabFragement extends Fragment {

    private IdeasTitleDBAdapter db;
    private TextView IdeaTitle;
    private FloatingActionButton fab;
    private ListView IdeaList;

    private final int SETIDEATITLE=1;
    private final int ADDIDEA=2;

    public TodayIdeasTabFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_today_ideas_tab_fragement, container, false);

        IdeaTitle=(TextView)v.findViewById(R.id.lblTodayTitle);
        fab=(FloatingActionButton)v.findViewById(R.id.addIdea);
        IdeaList=(ListView)v.findViewById(R.id.ideaList);

        db = new IdeasTitleDBAdapter(getContext());
        db.open();

        String _ideaTitle= null;
        try {
            _ideaTitle = this.checkTodayTitle();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(_ideaTitle==null) {
            IdeaTitle.setText("Today Idea Title");
            this.ShowIdeaTitleDialog();
            Log.i("Idea Title","null");
        }
        else {
            Log.i("Idea Title",_ideaTitle);
            IdeaTitle.setText(_ideaTitle);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowIdeaDialog();
            }
        });

//Fill Idea List for today
        FillData();
        //

        // Inflate the layout for this fragment
        return v;

    }

    public void ShowIdeaTitleDialog()
    {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        IdeasTitleDialogFragment itdf = new IdeasTitleDialogFragment();
        itdf.setTargetFragment(this,SETIDEATITLE);
        itdf.show(fm, "Add Title");
    }

    public void ShowIdeaDialog()
    {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        AddIdeaDialogFragment aidf = new AddIdeaDialogFragment();
        aidf.setTargetFragment(this,ADDIDEA);
        aidf.show(fm, "Add Idea");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case SETIDEATITLE:

                if (resultCode == Activity.RESULT_OK) {
                    String _ideaTitle=null;

                    try {
                        _ideaTitle = this.checkTodayTitle();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(_ideaTitle!=null) {
                        Log.i("Idea Title",_ideaTitle);
                        IdeaTitle.setText(_ideaTitle);
                    }

                }

                break;

            case ADDIDEA:
                if (resultCode == Activity.RESULT_OK) {
                    FillData();

                }

        }
    }

    public void FillData()
    {

        IdeasDBAdapter database;

        database=new IdeasDBAdapter(getContext());
        database.open();
        Cursor cursor=database.fetchAllIdeas();
        SimpleCursorAdapter myadapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_2, cursor,
                new String[] { IdeasDBAdapter.IdeaEntry.COLUMN_NAME_IDEA, IdeasDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION },
                new int[] { android.R.id.text1, android.R.id.text2}, 0);

        IdeaList.setAdapter(myadapter);

        database.close();
    }

    protected String checkTodayTitle() throws SQLException {

        Date date=new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Cursor cursor=db.fetchTodayIdeaTitle(dateFormat.format(date));
        if(cursor.getCount()<=0) {
            return null;
        }
        else {
            return cursor.getString(cursor
                    .getColumnIndexOrThrow(IdeasTitleDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION));
        }
    }

}
