package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step2SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.views.ContentStepActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Step2 implements View.OnClickListener, View.OnTouchListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    ContentStepActivity parentActivity;
    private Custom_SharedPreferences csp;
    private View view;

    private Step2SaveDTO step2SaveDTO;
    //step2
    TextView nextBt, backBt;
    private Urls urls;

    int nextStepNum = 3;
    int backStepNum = 1;

    LinearLayout cursor;
    FrameLayout cursor_frame, no10, no9, no8, no7, no6, no5, no4, no3, no2, no1;
    ImageView curosr_icon;
    int cursorHeight = 0;

    int[] cursorID = {
            R.id.no0,
            R.id.no1,
            R.id.no2,
            R.id.no3,
            R.id.no4,
            R.id.no5,
            R.id.no6,
            R.id.no7,
            R.id.no8,
            R.id.no9,
            R.id.no10
    };

    ImageView icon;
    TextView icon_txt;

    public Step2(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity, Step2SaveDTO step2SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step2SaveDTO = step2SaveDTO;
        this.init();
    }

    private void regObj() {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
//        this.no9 = this.view.findViewById(R.id.no9);
//        this.no9.setOnTouchListener(this);

        for (int i = 0, j = cursorID.length; i < j; i++) {
            FrameLayout bt = this.view.findViewById(cursorID[i]);
            bt.setTag(i);
            bt.setOnTouchListener(this);
        }
    }

    private void init() {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();

        this.view = inflater.inflate(R.layout.step2, this.parent, true);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.cursor = this.view.findViewById(R.id.cursor);
        this.cursor_frame = this.view.findViewById(R.id.cursor_frame);
        this.curosr_icon = this.view.findViewById(R.id.cursor_icon);
        this.no2 = this.view.findViewById(R.id.no2);

        this.icon = this.view.findViewById(R.id.icon);
        this.icon_txt = this.view.findViewById(R.id.icon_text);
        this.csp = new Custom_SharedPreferences(this.context);
        this.urls = new Urls();

        this.regObj();
        this.cursor.post(new Runnable() {
            @Override
            public void run() {
                cursorHeight = cursor_frame.getHeight();
                cursorHeight();
            }
        });
        this.cursor_frame.post(new Runnable() {
            @Override
            public void run() {
                //cursorFrameHeight = cursor_frame.getHeight();
                //cursorHeight();
            }
        });
    }

    private void cursorHeight() {

//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = (int) (cursorFrameHeight / 9.5);
//        cursor.setLayoutParams(layoutParams);

        if (step2SaveDTO == null) {
            step2SaveDTO = new Step2SaveDTO(0);

            //지난일기 불러오기.
            AndroidNetworking.post(urls.mainUrl + urls.getUrls.get("getRecentDiary"))
                    .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("step2_response=", response.toString());
                            if (response.isNull("rows")) {
                                parentActivity.dataProgress(response, true);

                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            Log.d("responseErr=", anError.getErrorDetail());
                        }
                    });

        }
//
        this.getHeight(step2SaveDTO.getAche_power());

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 30);
//        this.cursor.setLayoutParams(params);

    }

    private void setIconAndText(int num) {
        if (num <= 3) {
            this.icon.setImageResource(R.drawable.step2_1);
            this.icon_txt.setText("약한 통증");
        } else if (num <= 6) {
            this.icon.setImageResource(R.drawable.step2_2);
            this.icon_txt.setText("보통 통증");
        } else if (num <= 9) {
            this.icon.setImageResource(R.drawable.step2_3);
            this.icon_txt.setText("심한 통증");
        } else {
            this.icon.setImageResource(R.drawable.step2_4);
            this.icon_txt.setText("상상할 수 있는\n가장 심한 통증");
        }
    }

    private void getHeight(int num) {
        this.step2SaveDTO.setAche_power(num);
        this.parentActivity.save2(this.step2SaveDTO);

        Log.d("step2=", num + "_");
        Log.d("step2222=", no2.getHeight() + "_");

        if (num == 0) {
            this.curosr_icon.setY(this.cursorHeight - (no2.getHeight() + Global.pxToDp(this.context, 130)));
        } else if (num == 10) {
            this.curosr_icon.setY(Global.pxToDp(this.context, 20));
        } else if (num == 9) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 10) + Global.pxToDp(this.context, 210)));
        } else if (num == 8) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 9) + Global.pxToDp(this.context, 200)));
        } else if (num == 7) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 8) + Global.pxToDp(this.context, 190)));
        } else if (num == 6) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 7) + Global.pxToDp(this.context, 180)));
        } else if (num == 5) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 6) + Global.pxToDp(this.context, 170)));
        } else if (num == 4) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 5) + Global.pxToDp(this.context, 160)));
        } else if (num == 3) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 4) + Global.pxToDp(this.context, 150)));
        } else if (num == 2) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 3) + Global.pxToDp(this.context, 140)));
        } else if (num == 1) {
            this.curosr_icon.setY(this.cursorHeight - ((no2.getHeight() * 2) + Global.pxToDp(this.context, 130)));
        }

        setIconAndText(num);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getHeight((int) v.getTag());
        }

        return false;
    }
}
