package com.iteso.library.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.iteso.library.R;

import java.util.ArrayList;

/**
 * Created by Maritza on 25/10/2017.
 */

public class AdapterListDialog extends ArrayAdapter {
    private Context context;
    private ArrayList dataSet;

    public AdapterListDialog(Context context, ArrayList<String> dataSet) {
        super(context, R.layout.item_list_dialog, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.item_list_dialog, parent, false);
        TextView text = (TextView)rootView.findViewById(R.id.item_dialog_list_text);
        Button delete = (Button)rootView.findViewById(R.id.item_dialog_delete);
        TextView pos = (TextView)rootView.findViewById(R.id.item_dialog_position);

        text.setText(dataSet.get(position).toString());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.remove(position);
                notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }
}
