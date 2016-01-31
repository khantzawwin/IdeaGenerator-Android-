package com.khantzawwin.ideagenerator;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khantzawwin on 31/1/16.
 */
public class AddIdeaDialogFragment extends DialogFragment implements View.OnClickListener  {

    private EditText txtIdea;
    private EditText txtIdeaDesc;
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_addidea, container);
        txtIdea = (EditText) view.findViewById(R.id.txtIdea);
        txtIdeaDesc = (EditText) view.findViewById(R.id.txtIdeaDesc);
        btnAdd=(Button)view.findViewById(R.id.addIdea);

        getDialog().setTitle("Add Your Idea");
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        btnAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
            int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClick(View v) {
        try{
            addIdea();
        } catch (SQLException e) {
            Log.i("AddIdeaDialogFragment Add",e.getMessage());
            e.printStackTrace();
        }
    }

    private void addIdea() throws SQLException {
        String _idea = txtIdea.getText().toString();
        String _ideaDesc = txtIdeaDesc.getText().toString();
        String _titleID = this.checkTodayTitle();

        if(_idea!=null && _ideaDesc!=null && _titleID!=null) {
            IdeasDBAdapter db = new IdeasDBAdapter(getContext());
            db.open();
            long newRowId;

            newRowId = db.createIdea(_titleID, _idea, _ideaDesc);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
        }
        getDialog().dismiss();
    }

    private String checkTodayTitle() throws SQLException {

        IdeasTitleDBAdapter db = new IdeasTitleDBAdapter(getContext());
        db.open();

        Date date=new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Cursor cursor=db.fetchTodayIdeaTitle(dateFormat.format(date));
        db.close();
        if(cursor.getCount()<=0) {
            return null;
        }
        else {
            return cursor.getString(cursor
                    .getColumnIndexOrThrow(IdeasTitleDBAdapter.IdeaEntry._ID));
        }
    }
}
