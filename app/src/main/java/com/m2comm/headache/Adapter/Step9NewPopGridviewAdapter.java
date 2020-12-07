package com.m2comm.headache.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class Step9NewPopGridviewAdapter extends BaseAdapter {

    ArrayList<Step9MainEtcDTO> arrayList;
    LayoutInflater inflater;

    public Step9NewPopGridviewAdapter(ArrayList<Step9MainEtcDTO> arrayList, LayoutInflater inflater) {
        this.arrayList = arrayList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Step9MainEtcDTO row = this.arrayList.get(position);

        convertView = this.inflater.inflate(R.layout.step4_grid_item, parent, false);
        ImageView type = convertView.findViewById(R.id.step3_type);
        LinearLayout type_check = convertView.findViewById(R.id.step3_type_check);
        TextView type_Txt = convertView.findViewById( R.id.step3_type_txt );

        type.setImageResource(R.drawable.step9_type_default2);
        if (row.getVal().equals("Y")) {
            type.setImageResource(R.drawable.step9_type_click2);
            type_check.setVisibility(View.VISIBLE);
        }

        if (row.getContent().equals("기타")) {
            type.setImageResource(R.drawable.step_type_etc);
        } else if (row.getContent().equals("모름")) {
            type.setImageResource(R.drawable.step9_type_default1);
        }

        type_Txt.setText(row.getContent());


        ViewGroup.LayoutParams param = convertView.getLayoutParams();
        if (param == null) {
            param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param.height = 390;
        convertView.setLayoutParams(param);


        return convertView;

    }
}
