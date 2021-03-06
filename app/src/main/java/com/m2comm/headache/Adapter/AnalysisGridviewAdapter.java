package com.m2comm.headache.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.AnalyReallizeDTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class AnalysisGridviewAdapter extends BaseAdapter {

    ArrayList<AnalyReallizeDTO> arrayList;
    LayoutInflater inflater;

    public AnalysisGridviewAdapter(ArrayList<AnalyReallizeDTO> arrayList, LayoutInflater inflater) {
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

            AnalyReallizeDTO row = this.arrayList.get(position);

            convertView = this.inflater.inflate(R.layout.analy_grid_item,parent,false);
            ImageView type = convertView.findViewById(R.id.step3_type);
            LinearLayout type_check = convertView.findViewById(R.id.step3_type_check);
            TextView type_Txt = convertView.findViewById(R.id.step3_type_txt);
            TextView fer_Txt = convertView.findViewById(R.id.step3_txt);

            type.setImageResource(row.getIcon());
            type_Txt.setText(row.getKey_txt());
            fer_Txt.setText(row.getVal());
//            if( row.getClick() ) {
//                type.setImageResource(row.getClick_icon());
//                type_check.setVisibility(View.VISIBLE);
//            } else {
//                type.setImageResource(row.getDefault_icon());
//            }

            ViewGroup.LayoutParams param = convertView.getLayoutParams();
            if(param == null) {
                param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            param.height = 450;
            convertView.setLayoutParams(param);


        return convertView;

    }
}
