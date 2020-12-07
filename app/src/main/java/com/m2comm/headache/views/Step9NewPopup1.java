package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.Adapter.Step9GridviewAdapter;
import com.m2comm.headache.Adapter.Step9NewPopGridviewAdapter;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step9;
import com.m2comm.headache.contentStepView.Step9_New;
import com.m2comm.headache.databinding.ActivityStep9NewPopup1Binding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONObject;

import java.util.ArrayList;

public class Step9NewPopup1 extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener , AdapterView.OnItemLongClickListener {

    public static int STEP9_NEW_DRUG_TYPE = 9898;

    ActivityStep9NewPopup1Binding binding;
    private Step9MainDTO step9MainDTO;
    private ArrayList<Step9MainDTO> step9DayArray;
    private ArrayList<Step9MainEtcDTO> step9GridItems;
    private Step9NewPopGridviewAdapter adapter;
    private Urls urls;
    private Custom_SharedPreferences csp;

    int radioClickNum = 0;
    int[] radioBts = {
            R.id.radio1_img,
            R.id.radio2_img,
            R.id.radio3_img,
            R.id.radio4_img,
    };
    private boolean isTimeStep = false;
    private int rowPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step9_new_popup1);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_step9_new_popup1);
        this.binding.setStep9NewPop(this);
        this.init();
    }

    private void regObj() {
        this.binding.step9PopGridV.setOnItemLongClickListener(this);
        this.binding.radio1.setOnClickListener(this);
        this.binding.radio2.setOnClickListener(this);
        this.binding.radio3.setOnClickListener(this);
        this.binding.radio4.setOnClickListener(this);
    }

    private void init() {

        this.urls = new Urls();
        this.csp = new Custom_SharedPreferences(this);
        Intent intent = getIntent();
        this.step9MainDTO = (Step9MainDTO) intent.getSerializableExtra("row");
        this.rowPosition = intent.getIntExtra("position", -1);
        this.step9DayArray = (ArrayList<Step9MainDTO>) intent.getSerializableExtra("arr");
        Log.d("step9Arr=",this.step9DayArray.size()+"_");

        this.settingData();
        this.binding.step9PopGridV.setOnItemClickListener(this);

        this.binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("arr",step9DayArray);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.binding.cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isTimeStep) {
                    if (!countCheck()) {
                        //약물을 선택하지 않으면 종료함.
/*
                     Intent intent = new Intent();
                        intent.putExtra("arr",step9DayArray);
                        setResult(RESULT_CANCELED, intent);
                        finish();
                        return;*/

                        step9DayArray.set(rowPosition , step9MainDTO);

                        Intent intent = new Intent();
                        intent.putExtra("row", step9MainDTO);
                        intent.putExtra("position", rowPosition);
                        intent.putExtra("arr",step9DayArray);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    isTimeStep = true;
                    binding.step9PopGridV.setVisibility(View.GONE);
                    binding.choiceTime.setVisibility(View.VISIBLE);
                    binding.title.setText("약물 복용 후 효과 본 시간을 선택해 주세요.");
                    binding.cancelBt.setText("확인");
                    checkRadio(Integer.parseInt(step9MainDTO.getEffect()));
                } else {
                    step9DayArray.set(rowPosition , step9MainDTO);

                    Intent intent = new Intent();
                    intent.putExtra("row", step9MainDTO);
                    intent.putExtra("position", rowPosition);
                    intent.putExtra("arr",step9DayArray);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        this.regObj();
    }

    private boolean countCheck() {

        if (step9MainDTO.getAche_medicine1().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine2().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine3().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine4().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine5().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine6().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine7().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine8().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine9().equals("Y")) return true;
        else if (step9MainDTO.getAche_medicine10().equals("Y")) return true;

        for (int k = 0, l = step9MainDTO.getAche_medicine_etc().size(); k < l; k++) {
            Step9MainEtcDTO row_etc = step9MainDTO.getAche_medicine_etc().get(k);
            if (row_etc.getVal().equals("Y")) return true;
        }

        return false;

    }

    private void settingData() {
        this.step9GridItems = new ArrayList<>();
        // 1~ 10번째 순서대로.
        this.step9GridItems.add(new Step9MainEtcDTO("0", "이미그란", this.step9MainDTO.getAche_medicine1()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "수마트란", this.step9MainDTO.getAche_medicine2()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "슈그란", this.step9MainDTO.getAche_medicine3()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "마이그란", this.step9MainDTO.getAche_medicine4()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "조믹", this.step9MainDTO.getAche_medicine5()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "나라믹", this.step9MainDTO.getAche_medicine6()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "알모그란", this.step9MainDTO.getAche_medicine7()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "미가드", this.step9MainDTO.getAche_medicine8()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "크래밍", this.step9MainDTO.getAche_medicine9()));
        this.step9GridItems.add(new Step9MainEtcDTO("0", "모름", this.step9MainDTO.getAche_medicine10()));

        for (int i = 0, j = this.step9MainDTO.getAche_medicine_etc().size(); i < j; i++) {
            Step9MainEtcDTO row = this.step9MainDTO.getAche_medicine_etc().get(i);
            this.step9GridItems.add(new Step9MainEtcDTO(row.getKey(), row.getContent(), row.getVal()));
        }

        this.step9GridItems.add(new Step9MainEtcDTO("0", "기타", "N"));

        this.adapter = new Step9NewPopGridviewAdapter(this.step9GridItems, getLayoutInflater());
        this.binding.step9PopGridV.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    private void checkRadio(int num) {
        this.radioClickNum = num;
        for (int i = 0, j = radioBts.length; i < j; i++) {
            ImageView img = findViewById(radioBts[i]);
            if (num == i) {
                img.setImageResource(R.drawable.step9_radio_check);
            } else {
                img.setImageResource(R.drawable.step9_radio_not_check);
            }
        }
        this.step9MainDTO.setEffect(String.valueOf(num));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        final Step9MainEtcDTO long_row = this.step9GridItems.get(position);
        if (position <= 9) return true;

        new AlertDialog.Builder(this).setTitle("안내").setMessage("해당 항목을 삭제 하시겠습니까?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0, j = step9MainDTO.getAche_medicine_etc().size(); i < j; i++) {
                            Step9MainEtcDTO row = step9MainDTO.getAche_medicine_etc().get(i);
                            if ( long_row.getContent().equals(row.getContent()) ) {
                                step9MainDTO.getAche_medicine_etc().remove(i);

                                removeETC(Integer.parseInt(row.getKey()));
                                break;
                            }
                        }
                        step9DayArray.set(rowPosition , step9MainDTO);
                        settingData();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        return true;
    }

    public void removeETC(final int num) {

        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("del_etc"))
                .addBodyParameter("user_sid",this.csp.getValue("user_sid",""))
                .addBodyParameter("medicine_sid",String.valueOf(num))
                .addBodyParameter("device","android")
                .addBodyParameter("deviceid",csp.getValue("deviceid",""))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response!=",response.toString());

                for ( int i = 0 , j = step9DayArray.size(); i < j ; i ++ ) {
                    Step9MainDTO row = step9DayArray.get(i);
                    for ( int k = 0 , l = row.getAche_medicine_etc().size(); k < l ; k++ ) {
                        Step9MainEtcDTO row_etc = row.getAche_medicine_etc().get(k);
                        if ( row_etc.getKey().equals(String.valueOf(num)) ) {
                            row.getAche_medicine_etc().remove(k);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onError(ANError anError) {
                Log.d("anError=",anError.getErrorDetail());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Step9MainEtcDTO row = this.step9GridItems.get(position);

        if (position == 0) {
            this.step9MainDTO.setAche_medicine1(this.step9MainDTO.getAche_medicine1().equals("Y") ? "N" : "Y");
        } else if (position == 1) {
            this.step9MainDTO.setAche_medicine2(this.step9MainDTO.getAche_medicine2().equals("Y") ? "N" : "Y");
        } else if (position == 2) {
            this.step9MainDTO.setAche_medicine3(this.step9MainDTO.getAche_medicine3().equals("Y") ? "N" : "Y");
        } else if (position == 3) {
            this.step9MainDTO.setAche_medicine4(this.step9MainDTO.getAche_medicine4().equals("Y") ? "N" : "Y");
        } else if (position == 4) {
            this.step9MainDTO.setAche_medicine5(this.step9MainDTO.getAche_medicine5().equals("Y") ? "N" : "Y");
        } else if (position == 5) {
            this.step9MainDTO.setAche_medicine6(this.step9MainDTO.getAche_medicine6().equals("Y") ? "N" : "Y");
        } else if (position == 6) {
            this.step9MainDTO.setAche_medicine7(this.step9MainDTO.getAche_medicine7().equals("Y") ? "N" : "Y");
        } else if (position == 7) {
            this.step9MainDTO.setAche_medicine8(this.step9MainDTO.getAche_medicine8().equals("Y") ? "N" : "Y");
        } else if (position == 8) {
            this.step9MainDTO.setAche_medicine9(this.step9MainDTO.getAche_medicine9().equals("Y") ? "N" : "Y");
        } else if (position == 9) {
            this.step9MainDTO.setAche_medicine10(this.step9MainDTO.getAche_medicine10().equals("Y") ? "N" : "Y");
        } else {
            if (row.getContent().equals("기타")) {

                Intent intent = new Intent(this, DrugInput.class);
                intent.putExtra("isStep9New", true);
                startActivityForResult(intent, STEP9_NEW_DRUG_TYPE);
            } else {
                int index = position - 10;
                this.step9MainDTO.getAche_medicine_etc().get(index).setVal(this.step9MainDTO.getAche_medicine_etc().get(index).getVal().equals("Y") ? "N" : "Y");
            }
        }
        this.settingData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                this.checkRadio(0);
                break;
            case R.id.radio2:
                this.checkRadio(1);
                break;
            case R.id.radio3:
                this.checkRadio(2);
                break;
            case R.id.radio4:
                this.checkRadio(3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            if (requestCode == STEP9_NEW_DRUG_TYPE) {
                boolean isCheck = false;
                for ( int i = 0 , j = this.step9MainDTO.getAche_medicine_etc().size(); i < j ; i ++ ) {
                    Step9MainEtcDTO row_etc = this.step9MainDTO.getAche_medicine_etc().get(i);
                    if ( row_etc.getContent().equals(data.getStringExtra("input1")) ) isCheck = true;
                }
                if ( !isCheck ) {
                    // 동일은 약물 이름은 추가하지 않음.
                    this.step9MainDTO.getAche_medicine_etc().add(new Step9MainEtcDTO("", data.getStringExtra("input1"), "Y"));
                    //전체적으로 전부 추가합니다.
                    Log.d("rowPosition=",rowPosition+"_");
                    for ( int i = 0 , j = this.step9DayArray.size(); i < j ; i ++ ) {
                        Step9MainDTO row = this.step9DayArray.get(i);
                        row.getAche_medicine_etc().add(new Step9MainEtcDTO("", data.getStringExtra("input1"), "N"));
                    }
                    this.step9DayArray.set(rowPosition , this.step9MainDTO);
                }
                this.settingData();
            }
        }
    }


}