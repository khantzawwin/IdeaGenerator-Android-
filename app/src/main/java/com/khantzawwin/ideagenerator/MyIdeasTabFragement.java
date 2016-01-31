package com.khantzawwin.ideagenerator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyIdeasTabFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyIdeasTabFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyIdeasTabFragement extends Fragment {

    private ListView titleList;

    public MyIdeasTabFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_my_ideas_tab_fragement, container, false);

        titleList=(ListView)v.findViewById(R.id.IdeaTitleList);
        FillData();

        return v;
    }

    public void FillData()
    {

        IdeasTitleDBAdapter database;

        database=new IdeasTitleDBAdapter(getContext());
        database.open();
        Cursor cursor=database.fetchAllIdeaTitles();
        SimpleCursorAdapter myadapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_2, cursor,
                new String[] { IdeasTitleDBAdapter.IdeaEntry.COLUMN_NAME_DESCRIPTION, IdeasTitleDBAdapter.IdeaEntry.COLUMN_DATE_DESCRIPTION },
                new int[] { android.R.id.text1, android.R.id.text2}, 0);

        titleList.setAdapter(myadapter);
        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), IdeaListActivity.class);
                i.putExtra(IdeasDBAdapter.IdeaEntry.COLUMN_NAME_TITLE, Long.toString(id));
                startActivity(i);
//                startActivityForResult(i, ACTIVITY_EDIT);
            }
        });
        database.close();
    }

}
