package com.filoware.soundcolorcode;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArrayAdapterExplore extends ArrayAdapter<ExploreRow> {
    private  Context context;
    private  List<ExploreRow> ExploreRowList;

    public ArrayAdapterExplore(@NonNull Context context, ArrayList<ExploreRow> arrayList) {
            super(context, 0, arrayList);
            this.context = context;
            this.ExploreRowList = arrayList;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.row_explore,parent,false);

        ExploreRow currentExploreRow = ExploreRowList.get(position);

        TextView textView = (TextView)listItem.findViewById(R.id.label);
        textView.setText(currentExploreRow.getColorName());
        textView.setBackgroundColor(Color.parseColor(currentExploreRow.getBgColor()));
        if (currentExploreRow.getBgColor().equals("#000000")) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (currentExploreRow.getBgColor().equals("#FFFFFF")) {
            textView.setTextColor(Color.parseColor("#000000"));
        }

        return listItem;
    }
}
