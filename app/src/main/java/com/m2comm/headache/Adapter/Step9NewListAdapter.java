package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;

import java.util.ArrayList;
import java.util.Date;

public class Step9NewListAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private Step1SaveDTO step1SaveDTO;
    private ArrayList<Step9MainDTO> step9ArrayList;
    private LayoutInflater inflater;


    public Step9NewListAdapter(Context context, Activity activity, Step1SaveDTO step1SaveDTO, ArrayList<Step9MainDTO> step9ArrayList, LayoutInflater inflater) {
        this.context = context;
        this.activity = activity;
        this.step1SaveDTO = step1SaveDTO;
        this.step9ArrayList = step9ArrayList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return step9ArrayList.size();
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
        convertView = this.inflater.inflate(R.layout.step9_new_item , parent , false);

        Step9MainDTO row = this.step9ArrayList.get(position);

        TextView dateStr = convertView.findViewById(R.id.date);
        TextView drugText = convertView.findViewById(R.id.drugText);
        TextView effectText = convertView.findViewById(R.id.effectText);

        //약 복용 및 약물 효과가 없으면 해당 부모뷰를 조작함.
        LinearLayout drugParent = convertView.findViewById(R.id.drugParent);
        LinearLayout effectParent= convertView.findViewById(R.id.effectParent);
        LinearLayout line = convertView.findViewById(R.id.line);


        //서버 에서 가져오는 데이터 포맷이 달라 바꿔주어야함.
        Date date = Global.getStrToDate(row.getDate());
        dateStr.setText("·"+Global.formatChangeDate(date.getTime()));

        if ( ! this.drugCheck(row) ) {
            drugParent.setVisibility(View.INVISIBLE);
            effectParent.setVisibility(View.INVISIBLE);
        } else {
            if ( row.getEffect() != null && !row.getEffect().equals("")  ) {
                if ( !row.getEffect().equals("0") ) {
                    //effectText.setText(this.effectCheck(Integer.parseInt(row.getEffect())));
                    effectText.setText("효과있음");
                } else {
                    effectParent.setVisibility(View.INVISIBLE);
                }
            }
        }

        return convertView;
    }


    //약물 복용 했는지 체크
    private boolean drugCheck(Step9MainDTO row) {
        boolean isCheck = false;

        if ( row.getAche_medicine1().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine2().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine3().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine4().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine5().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine6().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine7().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine8().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine9().equals("Y") ) isCheck =true;
        else if ( row.getAche_medicine10().equals("Y") ) isCheck =true;

        for ( int i = 0 , j = row.getAche_medicine_etc().size(); i < j; i ++ ) {
            Step9MainEtcDTO etcRow = row.getAche_medicine_etc().get(i);
            if ( etcRow.getVal().equals("Y") ) {
                isCheck = true;
                break;
            }
        }

        return isCheck;
    }

    private String effectCheck (int num) {
        String returnData = "";
        switch (num) {
            case 0:
                returnData = "효과없음";
                break;
            case 1:
                returnData = "1시간 이내";
                break;
            case 2:
                returnData = "2시간 이내";
                break;
            case 3:
                returnData = "4시간 이내";
                break;

        }
        return returnData;
    }





}
