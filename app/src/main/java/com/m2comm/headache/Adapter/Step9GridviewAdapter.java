package com.m2comm.headache.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class Step9GridviewAdapter extends BaseAdapter {

    ArrayList<Step9DTO> arrayList;
    LayoutInflater inflater;

    public Step9GridviewAdapter(ArrayList<Step9DTO> arrayList, LayoutInflater inflater) {
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

        Step9DTO row = this.arrayList.get(position);

        convertView = this.inflater.inflate(R.layout.step4_grid_item, parent, false);
        ImageView type = convertView.findViewById(R.id.step3_type);
        LinearLayout type_check = convertView.findViewById(R.id.step3_type_check);
        TextView type_Txt = convertView.findViewById(R.id.step3_type_txt);
        TextView drugCount = convertView.findViewById(R.id.drugCount);

        type.setImageResource(row.getDefault_icon());

        if (row.getClick()) {
            type.setImageResource(row.getClick_icon());
            type_check.setVisibility(View.VISIBLE);
        } else {
            type.setImageResource(row.getDefault_icon());
        }
        type_Txt.setText(row.getName());

        int drugCountNum = 0;
        if ( row.getDrugArray() != null ) {
            for ( int i = 0 , j = row.getDrugArray().size(); i < j; i++) {
                Step9Dates dateRow = row.getDrugArray().get(i);
                if ( dateRow.getVal().equals("Y") ) {
                    drugCountNum += 1;
                }
            }
        }

        if (row.getDrugArray() != null && drugCountNum > 1) {
            //drugCount.setVisibility(View.VISIBLE);
            drugCount.setText(""+drugCountNum);
        }

        ViewGroup.LayoutParams param = convertView.getLayoutParams();
        if (param == null) {
            param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param.height = 430;
        convertView.setLayoutParams(param);


        return convertView;

    }
}
