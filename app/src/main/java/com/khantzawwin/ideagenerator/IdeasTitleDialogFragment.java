package com.khantzawwin.ideagenerator;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khantzawwin on 30/1/16.
 */
public class IdeasTitleDialogFragment extends DialogFragment implements View.OnClickListener  {

    private EditText txtIdeaTitle;
    private Button btnAdd;
    private IdeasTitleDBAdapter db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_addtitle, container);

        db = new IdeasTitleDBAdapter(getContext());
        db.open();


        txtIdeaTitle = (EditText) view.findViewById(R.id.txtIdeaTitle);
        btnAdd=(Button)view.findViewById(R.id.addIdeaTitle);

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
        String _ideaTitle = txtIdeaTitle.getText().toString();
        if (_ideaTitle != null ) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();

            long newRowId;
            newRowId = db.createIdeaTitle(dateFormat.format(date),_ideaTitle);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());


        }
        getDialog().dismiss();
    }
}
