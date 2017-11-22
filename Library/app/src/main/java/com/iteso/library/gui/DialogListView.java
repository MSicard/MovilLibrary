package com.iteso.library.gui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Maritza on 10/11/2017.
 */

public class DialogListView extends DialogFragment {
    ListView mBooksList;
    private ArrayList mDataSet = new ArrayList();

    public static DialogListView newInstance(){
        DialogListView dialog = new DialogListView();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
