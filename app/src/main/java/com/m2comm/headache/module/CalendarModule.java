package com.m2comm.headache.module;

import android.app.Activity;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarModule {

    private Context c;
    private Activity a;
    private Date date;
    private String strRealDate;
    private String strRealDateTime;
    private Calendar calendar;
    private ArrayList<String> dayList;

    //연,월,일을 따로 저장
    final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
    final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
    final SimpleDateFormat curDayHour = new SimpleDateFormat("HH", Locale.KOREA);
    final SimpleDateFormat curDayMin = new SimpleDateFormat("mm", Locale.KOREA);

    public CalendarModule(Context c, Activity a) {
        this.c = c;
        this.a = a;
        this.todayDate();
    }

    private void todayDate() {
        // 오늘 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        this.date = new Date(now);
        //현재 날짜 텍스트뷰에 뿌려줌
        this.strRealDate = this.curYearFormat.format(this.date) + "-" +
                this.curMonthFormat.format(this.date) + "-" +
                this.curDayFormat.format(this.date);

        this.strRealDateTime = this.curYearFormat.format(this.date) + "-" +
                this.curMonthFormat.format(this.date) + "-" +
                this.curDayFormat.format(this.date) + " " + this.curDayHour.format(this.date) + ":" +this.curDayMin.format(this.date);

    }

    public ArrayList<String> getCalendar( String date ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.KOREA);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(date));
            //cal.add(this.calendar.MONTH , temp);
            this.calendar = cal;

            //this.strRealDate = this.curYearFormat.format(cal.getTime()) + "-" + this.curMonthFormat.format(cal.getTime()) + "-" + this.curDayFormat.format(cal.getTime());
            this.calendar.set(Integer.parseInt(this.curYearFormat.format(cal.getTime())), Integer.parseInt(this.curMonthFormat.format(cal.getTime())) - 1, 1);
            int dayNum = this.calendar.get(this.calendar.DAY_OF_WEEK);

            this.dayList = new ArrayList<>();
            for (int i = 1; i < dayNum; i++) {
                this.dayList.add("");
            }
            setCalendarDate(this.calendar.get(this.calendar.MONTH) + 1);

        } catch ( Exception e ) {
            this.dayList = new ArrayList<>();
        }

        return this.dayList;
    }

    private void setCalendarDate(int month) {
        this.calendar.set(this.calendar.MONTH, month - 1);
        //getActualMaximum => 마지막 일 현재날짜 기준 최대수
        //getMaximum => 마지막 일 카렌더가 가진 최대수
        for (int i = 0; i < this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            this.dayList.add("" +  (i + 1));
        }
    }


    public String getStrRealDate() {
        return strRealDate;
    }

    public String getStrRealDateTime() {
        return strRealDateTime;
    }

    public Date getDate() {
        return date;
    }
}
