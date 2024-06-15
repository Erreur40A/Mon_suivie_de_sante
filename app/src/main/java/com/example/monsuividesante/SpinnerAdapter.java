package com.example.monsuividesante;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> items;
    private String explication;
    private int layout;

    public SpinnerAdapter(Context context, ArrayList<String> items, int layout, String explication) {
        super(context, layout, items);
        this.context=context;
        this.items=items;
        this.explication=explication;
        this.layout=layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            TextView textView = new TextView(getContext());
            textView.setText(explication);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
            textView.setPadding(10, 0, 0, 0);
            return textView;
        }
        return createDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createDropDownView(position, convertView, parent);
    }

    private View createDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_liste_deroulante, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.name);
        textView.setText(items.get(position));

        return convertView;
    }
}
