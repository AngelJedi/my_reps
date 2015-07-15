package com.angeljedi.myreps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RepAdapter extends ArrayAdapter<Rep> {

    public RepAdapter(Context context, int resource, List<Rep> repList) {
        super(context, resource, repList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rep rep = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_rep, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_name);
        tvName.setText(rep.getName());
        return convertView;
    }
}
