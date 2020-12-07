package com.m2comm.headache;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Global {
    public static String dateFormat = "yyyy-MM-dd";
    Context c;

    public Global(Context c) {
        this.c = c;
    }
    public static int[] icon = {
            R.drawable.detail_icon1,
            R.drawable.detail_icon2,
            R.drawable.detail_icon3,
            R.drawable.detail_icon4
    };

    public static int[] icon_back = {
            R.drawable.detail_icon1_back,
            R.drawable.detail_icon2_back,
            R.drawable.detail_icon3_back,
            R.drawable.detail_icon4_back
    };

    public static int getIconNumberReturn (int num) {
        if ( num <= 3 ) {
            return 0;
        } else if ( num <= 6 ) {
            return 1;
        } else if ( num <= 9 ) {
            return 2;
        } else {
            return 3;
        }
    }

    public static float pxToDp(Context context, float px) {

        // 해상도 마다 다른 density 를 반환. xxxhdpi는 density = 4
        float density = context.getResources().getDisplayMetrics().density;

        if (density == 1.0)      // mpdi  (160dpi) -- xxxhdpi (density = 4)기준으로 density 값을 재설정 한다
            density *= 4.0;
        else if (density == 1.5) // hdpi  (240dpi)
            density *= (8 / 3);
        else if (density == 2.0) // xhdpi (320dpi)
            density *= 2.0;

        return px / density;     // dp 값 반환
    }

    public static Date getStrToDateTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getStrToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
    public static Date getTimeToDate(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);
        try {
            return dateFormat.parse(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTimeToStr(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTimeToStrYearMonth(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.KOREA);

            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String inputDateTimeToStr(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일-a h시mm분", Locale.KOREA);

            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String inputDateToStr(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA);

            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatChangeDate(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd(EE)");

            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static ArrayList<String> getFormatDates (Long sDateLong , Long eDateLong) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd(EE)");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = new Date(sDateLong);
        Date eDate = new Date(eDateLong);
        Date currentDate = sDate;
        ArrayList<String> dates = new ArrayList<>();

        if ( eDateLong == 0L ) {
            dates.add(sdf.format(sDate));
        }
        while ( currentDate.compareTo(eDate) <= 0 ) {
            dates.add(sdf.format(currentDate));

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = c.getTime();
        }

        return dates;
    }


    public static String getTimeToDateAndWeek(long time) {
        try {
            return new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.getDefault()).format(time);
        } catch (Exception e) {
            return "";
        }
    }


    public static String inputTimeToStr(Long time) {

        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);

        String[] cutTime = dateFormat.format(date).split(":");
        int hour = Integer.parseInt(cutTime[0]);
        int min = Integer.parseInt(cutTime[1]);

//        String am_pm = "오전";
//        if ( hour > 12 ) {
//            am_pm = "오후";
//            hour = hour - 12;
//        }

        return hour+":"+min;
        //return am_pm+" "+hour+"시 "+min+"분";
    }

    //px을 dp로 변환 (px을 입력받아 dp를 리턴)
    public static float convertPixelsToDp( Context c , float px ){
        Resources resources = c.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int getEffectColor(int num) {
        int color = -1;
        if ( num <= 3 ) {
            color = Color.parseColor("#d2e340");
        } else if ( num <= 6 ) {
            color = Color.parseColor("#e9aa00");
        } else if ( num <= 9 ) {
            color = Color.parseColor("#db6802");
        } else {
            color = Color.parseColor("#e02936");
        }
        return color;
    }

    public static int getEffectLineColor(int num) {
        int color = -1;
        if ( num <= 3 ) {
            color = Color.parseColor("#c1cf4c");
        } else if ( num <= 6 ) {
            color = Color.parseColor("#f4cd79");
        } else if ( num <= 9 ) {
            color = Color.parseColor("#f4a763");
        } else {
            color = Color.parseColor("#ffb1a2");
        }
        return color;
    }

    public static String formatChangeToStrDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        try {
            Date tmpDate = getStrToDate(date);

            return getTimeToDateAndWeek(tmpDate.getTime());
        } catch (Exception e) {
            return "";
        }
    }






}
