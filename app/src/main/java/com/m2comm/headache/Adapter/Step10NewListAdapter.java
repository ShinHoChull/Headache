package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.Step10MainDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.contentStepView.Step10_New;

import java.util.ArrayList;
import java.util.Date;

public class Step10NewListAdapter extends BaseAdapter  {

    private Context context;
    private Step10_New activity;
    private Step1SaveDTO step1SaveDTO;
    private ArrayList<Step10MainDTO> step10MainDTOArrayList;
    private LayoutInflater inflater;



    public Step10NewListAdapter(Context context, Step10_New activity, Step1SaveDTO step1SaveDTO, ArrayList<Step10MainDTO> step10MainDTOArrayList, LayoutInflater inflater) {
        this.context = context;
        this.activity = activity;
        this.step1SaveDTO = step1SaveDTO;
        this.step10MainDTOArrayList = step10MainDTOArrayList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return step10MainDTOArrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = this.inflater.inflate(R.layout.step10_new_item , parent , false);


        final Step10MainDTO row = this.step10MainDTOArrayList.get(position);

        TextView dateStr = convertView.findViewById(R.id.date);

        final LinearLayout img1,img2,img3,img4,img5;
        img1 = convertView.findViewById(R.id.step10_img1);
        img2 = convertView.findViewById(R.id.step10_img2);
        img3 = convertView.findViewById(R.id.step10_img3);
        img4 = convertView.findViewById(R.id.step10_img4);
        img5 = convertView.findViewById(R.id.step10_img5);

        //ClickEvent
        FrameLayout bt1 = convertView.findViewById(R.id.step10NewBt1);
        FrameLayout bt2 = convertView.findViewById(R.id.step10NewBt2);
        FrameLayout bt3 = convertView.findViewById(R.id.step10NewBt3);
        FrameLayout bt4 = convertView.findViewById(R.id.step10NewBt4);
        FrameLayout bt5 = convertView.findViewById(R.id.step10NewBt5);


        Date date = Global.getStrToDate(row.getDate());
        dateStr.setText("·"+Global.formatChangeDate(date.getTime()));
        if ( row.getAche_effect1().equals("Y") ) img1.setVisibility(View.VISIBLE);
        if ( row.getAche_effect2().equals("Y") ) img2.setVisibility(View.VISIBLE);
        if ( row.getAche_effect3().equals("Y") ) img3.setVisibility(View.VISIBLE);
        if ( row.getAche_effect4().equals("Y") ) img4.setVisibility(View.VISIBLE);
        if ( row.getAche_effect5().equals("Y") ) img5.setVisibility(View.VISIBLE);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.getAche_effect1().equals("Y") ) {
                    row.setAche_effect1("N");
                    img1.setVisibility(View.INVISIBLE);
                } else {
                    row.setAche_effect1("Y");
                    img1.setVisibility(View.VISIBLE);

                    row.setAche_effect2("N");
                    img2.setVisibility(View.INVISIBLE);
                }
                changeRow(row , position);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.getAche_effect2().equals("Y") ) {
                    row.setAche_effect2("N");
                    img2.setVisibility(View.INVISIBLE);
                } else {
                    row.setAche_effect2("Y");
                    img2.setVisibility(View.VISIBLE);

                    row.setAche_effect1("N");
                    img1.setVisibility(View.INVISIBLE);
                }
                changeRow(row , position);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.getAche_effect3().equals("Y") ) {
                    row.setAche_effect3("N");
                    img3.setVisibility(View.INVISIBLE);
                } else {
                    row.setAche_effect3("Y");
                    img3.setVisibility(View.VISIBLE);

                    row.setAche_effect4("N");
                    img4.setVisibility(View.INVISIBLE);
                }
                changeRow(row , position);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.getAche_effect4().equals("Y") ) {
                    row.setAche_effect4("N");
                    img4.setVisibility(View.INVISIBLE);
                } else {
                    row.setAche_effect4("Y");
                    img4.setVisibility(View.VISIBLE);

                    row.setAche_effect3("N");
                    img3.setVisibility(View.INVISIBLE);
                }
                changeRow(row , position);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.getAche_effect5().equals("Y") ) {
                    row.setAche_effect5("N");
                    img5.setVisibility(View.INVISIBLE);
                } else {
                    row.setAche_effect5("Y");
                    img5.setVisibility(View.VISIBLE);
                }
                changeRow(row , position);
            }
        });

        return convertView;
    }

    private void changeRow( Step10MainDTO row , int position) {
        Log.d("position=",position+"_");
        this.activity.changeRow(row , position);
    }



}
