package com.m2comm.headache.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.m2comm.headache.contentStepView.Step9;
import com.m2comm.headache.sendDTO.Send9PixDTO;
import com.m2comm.headache.sendDTO.Step4SendDTO;
import com.m2comm.headache.sendDTO.Step9SendDTO;

import java.util.ArrayList;

public class StepParentDTO {

    String user_sid;
    //step1
    Long sdate;
    Long edate;
    String address;
    private double pressure;
    private double humidity;
    private double temp;
    private String weather_icon;

    //step2
    int ache_power;
//
//    //step3
    String ache_location1;
    String ache_location2;
    String ache_location3;
    String ache_location4;
    String ache_location5;
    String ache_location6;
    String ache_location7;
    String ache_location8;
    String ache_location9;
    String ache_location10;
    String ache_location11;
    String ache_location12;
    String ache_location13;
    String ache_location14;
    String ache_location15;
    String ache_location16;
    String ache_location17;
    String ache_location18;
//
//    //step4
    String ache_type1;
    String ache_type2;
    String ache_type3;
    String ache_type4;
    String ache_type5;
    ArrayList<Step4SendDTO> ache_type_etc;
//
//    //step5
    String ache_realize_yn;
    int ache_realize_hour;
    int ache_realize_minute;
    String ache_realize1;
    String ache_realize2;
    String ache_realize3;
    String ache_realize4;
    String ache_realize5;
    String ache_realize6;
    String ache_realize7;
    String ache_realize8;
    ArrayList<Step4SendDTO> ache_realize_etc;
//
//    //step6
    String ache_sign_yn;
    String ache_sign1;
    String ache_sign2;
    String ache_sign3;
    String ache_sign4;
    String ache_sign5;
    String ache_sign6;
    String ache_sign7;
//
//    //step7
    String ache_with1;
    String ache_with2;
    String ache_with3;
    String ache_with4;
    String ache_with5;
    String ache_with6;
    String ache_with7;
    String ache_with8;
    String ache_with9;
    String ache_with10;
    String ache_with11;
    ArrayList<Step4SendDTO> ache_with_etc;
//
//    //step8
    String ache_factor_yn;
    String ache_factor1;
    String ache_factor2;
    String ache_factor3;
    String ache_factor4;
    String ache_factor5;
    String ache_factor6;
    String ache_factor7;
    String ache_factor8;
    String ache_factor9;
    String ache_factor10;
    String ache_factor11;
    String ache_factor12;
    String ache_factor13;
    String ache_factor14;
    String ache_factor15;
    ArrayList<Step4SendDTO> ache_factor_etc;
//
//    //step9
    ArrayList<Step9MainDTO> ache_medicine;
//    Send9PixDTO ache_medicine1;
//    Send9PixDTO ache_medicine2;
//    Send9PixDTO ache_medicine3;
//    Send9PixDTO ache_medicine4;
//    Send9PixDTO ache_medicine5;
//    Send9PixDTO ache_medicine6;
//    Send9PixDTO ache_medicine7;
//    Send9PixDTO ache_medicine8;
//    Send9PixDTO ache_medicine9;
//    Send9PixDTO ache_medicine10;
//    ArrayList<Step9SendDTO> ache_medicine_etc;
//    String ache_medicine_effect;
//
//    //step10
    ArrayList<Step10MainDTO> ache_effect;
//    String ache_effect1;
//    String ache_effect2;
//    String ache_effect3;
//    String ache_effect4;
//    String ache_effect5;
//    ArrayList<Step9Dates> ache_effect_date_val;
//    ArrayList<Step4SendDTO> ache_effect_etc;
//
//    //step11
    Long mens_sdate;
    Long mens_edate;
//
//    //step12
    String memo;

    String diary_sid;

    public StepParentDTO(String user_sid, Long sdate, Long edate, String address, double pressure, double humidity, double temp, String weather_icon, int ache_power, String ache_location1, String ache_location2, String ache_location3, String ache_location4, String ache_location5, String ache_location6, String ache_location7, String ache_location8, String ache_location9, String ache_location10, String ache_location11, String ache_location12, String ache_location13, String ache_location14, String ache_location15, String ache_location16, String ache_location17, String ache_location18, String ache_type1, String ache_type2, String ache_type3, String ache_type4, String ache_type5, ArrayList<Step4SendDTO> ache_type_etc, String ache_realize_yn, int ache_realize_hour, int ache_realize_minute, String ache_realize1, String ache_realize2, String ache_realize3, String ache_realize4, String ache_realize5, String ache_realize6, String ache_realize7, String ache_realize8, ArrayList<Step4SendDTO> ache_realize_etc, String ache_sign_yn, String ache_sign1, String ache_sign2, String ache_sign3, String ache_sign4, String ache_sign5, String ache_sign6, String ache_sign7, String ache_with1, String ache_with2, String ache_with3, String ache_with4, String ache_with5, String ache_with6, String ache_with7, String ache_with8, String ache_with9, String ache_with10, String ache_with11, ArrayList<Step4SendDTO> ache_with_etc, String ache_factor_yn, String ache_factor1, String ache_factor2, String ache_factor3, String ache_factor4, String ache_factor5, String ache_factor6, String ache_factor7, String ache_factor8, String ache_factor9, String ache_factor10, String ache_factor11, String ache_factor12, String ache_factor13, String ache_factor14, String ache_factor15, ArrayList<Step4SendDTO> ache_factor_etc, ArrayList<Step9MainDTO> ache_medicine, ArrayList<Step10MainDTO> ache_effect, Long mens_sdate, Long mens_edate, String memo, String diary_sid) {
        this.user_sid = user_sid;
        this.sdate = sdate;
        this.edate = edate;
        this.address = address;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp = temp;
        this.weather_icon = weather_icon;
        this.ache_power = ache_power;
        this.ache_location1 = ache_location1;
        this.ache_location2 = ache_location2;
        this.ache_location3 = ache_location3;
        this.ache_location4 = ache_location4;
        this.ache_location5 = ache_location5;
        this.ache_location6 = ache_location6;
        this.ache_location7 = ache_location7;
        this.ache_location8 = ache_location8;
        this.ache_location9 = ache_location9;
        this.ache_location10 = ache_location10;
        this.ache_location11 = ache_location11;
        this.ache_location12 = ache_location12;
        this.ache_location13 = ache_location13;
        this.ache_location14 = ache_location14;
        this.ache_location15 = ache_location15;
        this.ache_location16 = ache_location16;
        this.ache_location17 = ache_location17;
        this.ache_location18 = ache_location18;
        this.ache_type1 = ache_type1;
        this.ache_type2 = ache_type2;
        this.ache_type3 = ache_type3;
        this.ache_type4 = ache_type4;
        this.ache_type5 = ache_type5;
        this.ache_type_etc = ache_type_etc;
        this.ache_realize_yn = ache_realize_yn;
        this.ache_realize_hour = ache_realize_hour;
        this.ache_realize_minute = ache_realize_minute;
        this.ache_realize1 = ache_realize1;
        this.ache_realize2 = ache_realize2;
        this.ache_realize3 = ache_realize3;
        this.ache_realize4 = ache_realize4;
        this.ache_realize5 = ache_realize5;
        this.ache_realize6 = ache_realize6;
        this.ache_realize7 = ache_realize7;
        this.ache_realize8 = ache_realize8;
        this.ache_realize_etc = ache_realize_etc;
        this.ache_sign_yn = ache_sign_yn;
        this.ache_sign1 = ache_sign1;
        this.ache_sign2 = ache_sign2;
        this.ache_sign3 = ache_sign3;
        this.ache_sign4 = ache_sign4;
        this.ache_sign5 = ache_sign5;
        this.ache_sign6 = ache_sign6;
        this.ache_sign7 = ache_sign7;
      //  this.ache_with_yn = ache_with_yn;
        this.ache_with1 = ache_with1;
        this.ache_with2 = ache_with2;
        this.ache_with3 = ache_with3;
        this.ache_with4 = ache_with4;
        this.ache_with5 = ache_with5;
        this.ache_with6 = ache_with6;
        this.ache_with7 = ache_with7;
        this.ache_with8 = ache_with8;
        this.ache_with9 = ache_with9;
        this.ache_with10 = ache_with10;
        this.ache_with11 = ache_with11;
        this.ache_with_etc = ache_with_etc;
        this.ache_factor_yn = ache_factor_yn;
        this.ache_factor1 = ache_factor1;
        this.ache_factor2 = ache_factor2;
        this.ache_factor3 = ache_factor3;
        this.ache_factor4 = ache_factor4;
        this.ache_factor5 = ache_factor5;
        this.ache_factor6 = ache_factor6;
        this.ache_factor7 = ache_factor7;
        this.ache_factor8 = ache_factor8;
        this.ache_factor9 = ache_factor9;
        this.ache_factor10 = ache_factor10;
        this.ache_factor11 = ache_factor11;
        this.ache_factor12 = ache_factor12;
        this.ache_factor13 = ache_factor13;
        this.ache_factor14 = ache_factor14;
        this.ache_factor15 = ache_factor15;
        this.ache_factor_etc = ache_factor_etc;
        this.ache_medicine = ache_medicine;
        this.ache_effect = ache_effect;
        this.mens_sdate = mens_sdate;
        this.mens_edate = mens_edate;
        this.memo = memo;
        this.diary_sid = diary_sid;
    }

    //    public StepParentDTO(
//            String user_sid,
//                         Long sdate, Long edate, String address,
//                         int ache_power,
//                         String ache_location1, String ache_location2, String ache_location3, String ache_location4, String ache_location5, String ache_location6, String ache_location7, String ache_location8, String ache_location9, String ache_location10, String ache_location11, String ache_location12, String ache_location13, String ache_location14,
//                         String ache_type1, String ache_type2, String ache_type3, String ache_type4, String ache_type5, ArrayList<Step4SendDTO> ache_type_etc,
//                         String ache_realize_yn, int ache_realize_hour, int ache_realize_minute, String ache_realize1, String ache_realize2, String ache_realize3, String ache_realize4, String ache_realize5, String ache_realize6, String ache_realize7, String ache_realize8, ArrayList<Step4SendDTO> ache_realize_etc,
//                         String ache_sign_yn, String ache_sign1, String ache_sign2, String ache_sign3, String ache_sign4, String ache_sign5, String ache_sign6, String ache_sign7,
//                         String ache_with_yn, String ache_with1, String ache_with2, String ache_with3, String ache_with4, String ache_with5, String ache_with6, String ache_with7, String ache_with8, String ache_with9, String ache_with10, String ache_with11, String ache_with12, ArrayList<Step4SendDTO> ache_with_etc,
//                         String ache_factor_yn, String ache_factor1, String ache_factor2, String ache_factor3, String ache_factor4, String ache_factor5, String ache_factor6, String ache_factor7, String ache_factor8, String ache_factor9, String ache_factor10, String ache_factor11, String ache_factor12, String ache_factor13, String ache_factor14, String ache_factor15, ArrayList<Step4SendDTO> ache_factor_etc,
//                        Send9PixDTO ache_medicine1, Send9PixDTO ache_medicine2, Send9PixDTO ache_medicine3, Send9PixDTO ache_medicine4, Send9PixDTO ache_medicine5, Send9PixDTO ache_medicine6, Send9PixDTO ache_medicine7, Send9PixDTO ache_medicine8, Send9PixDTO ache_medicine9, Send9PixDTO ache_medicine10, Send9PixDTO ache_medicine11, ArrayList<Step9SendDTO> ache_medicine_etc,
//                         int ache_medicine_effect, String ache_effect1, String ache_effect2, String ache_effect3, String ache_effect4, String ache_effect5, ArrayList<Step9Dates> ache_effect_date_val, ArrayList<Step4SendDTO> ache_effect_etc,
//                         String mens_sdate, String mens_edate,
//                         String memo) {
//        this.user_sid = user_sid;
//        this.sdate = sdate;
//        this.edate = edate;
//        this.address = address;
//        this.ache_power = ache_power;
//        this.ache_location1 = ache_location1;
//        this.ache_location2 = ache_location2;
//        this.ache_location3 = ache_location3;
//        this.ache_location4 = ache_location4;
//        this.ache_location5 = ache_location5;
//        this.ache_location6 = ache_location6;
//        this.ache_location7 = ache_location7;
//        this.ache_location8 = ache_location8;
//        this.ache_location9 = ache_location9;
//        this.ache_location10 = ache_location10;
//        this.ache_location11 = ache_location11;
//        this.ache_location12 = ache_location12;
//        this.ache_location13 = ache_location13;
//        this.ache_location14 = ache_location14;
//        this.ache_type1 = ache_type1;
//        this.ache_type2 = ache_type2;
//        this.ache_type3 = ache_type3;
//        this.ache_type4 = ache_type4;
//        this.ache_type5 = ache_type5;
//        this.ache_type_etc = ache_type_etc;
//        this.ache_realize_yn = ache_realize_yn;
//        this.ache_realize_hour = ache_realize_hour;
//        this.ache_realize_minute = ache_realize_minute;
//        this.ache_realize1 = ache_realize1;
//        this.ache_realize2 = ache_realize2;
//        this.ache_realize3 = ache_realize3;
//        this.ache_realize4 = ache_realize4;
//        this.ache_realize5 = ache_realize5;
//        this.ache_realize6 = ache_realize6;
//        this.ache_realize7 = ache_realize7;
//        this.ache_realize8 = ache_realize8;
//        this.ache_realize_etc = ache_realize_etc;
//        this.ache_sign_yn = ache_sign_yn;
//        this.ache_sign1 = ache_sign1;
//        this.ache_sign2 = ache_sign2;
//        this.ache_sign3 = ache_sign3;
//        this.ache_sign4 = ache_sign4;
//        this.ache_sign5 = ache_sign5;
//        this.ache_sign6 = ache_sign6;
//        this.ache_sign7 = ache_sign7;
//        this.ache_with_yn = ache_with_yn;
//        this.ache_with1 = ache_with1;
//        this.ache_with2 = ache_with2;
//        this.ache_with3 = ache_with3;
//        this.ache_with4 = ache_with4;
//        this.ache_with5 = ache_with5;
//        this.ache_with6 = ache_with6;
//        this.ache_with7 = ache_with7;
//        this.ache_with8 = ache_with8;
//        this.ache_with9 = ache_with9;
//        this.ache_with10 = ache_with10;
//        this.ache_with11 = ache_with11;
//        this.ache_with12 = ache_with12;
//        this.ache_with_etc = ache_with_etc;
//        this.ache_factor_yn = ache_factor_yn;
//        this.ache_factor1 = ache_factor1;
//        this.ache_factor2 = ache_factor2;
//        this.ache_factor3 = ache_factor3;
//        this.ache_factor4 = ache_factor4;
//        this.ache_factor5 = ache_factor5;
//        this.ache_factor6 = ache_factor6;
//        this.ache_factor7 = ache_factor7;
//        this.ache_factor8 = ache_factor8;
//        this.ache_factor9 = ache_factor9;
//        this.ache_factor10 = ache_factor10;
//        this.ache_factor11 = ache_factor11;
//        this.ache_factor12 = ache_factor12;
//        this.ache_factor13 = ache_factor13;
//        this.ache_factor14 = ache_factor14;
//        this.ache_factor15 = ache_factor15;
//        this.ache_factor_etc = ache_factor_etc;
//        this.ache_medicine1 = ache_medicine1;
//        this.ache_medicine2 = ache_medicine2;
//        this.ache_medicine3 = ache_medicine3;
//        this.ache_medicine4 = ache_medicine4;
//        this.ache_medicine5 = ache_medicine5;
//        this.ache_medicine6 = ache_medicine6;
//        this.ache_medicine7 = ache_medicine7;
//        this.ache_medicine8 = ache_medicine8;
//        this.ache_medicine9 = ache_medicine9;
//        this.ache_medicine10 = ache_medicine10;
//        this.ache_medicine11 = ache_medicine11;
//        this.ache_medicine_etc = ache_medicine_etc;
//        this.ache_medicine_effect = ache_medicine_effect;
//        this.ache_effect1 = ache_effect1;
//        this.ache_effect2 = ache_effect2;
//        this.ache_effect3 = ache_effect3;
//        this.ache_effect4 = ache_effect4;
//        this.ache_effect5 = ache_effect5;
//        this.ache_effect_date_val = ache_effect_date_val;
//        this.ache_effect_etc = ache_effect_etc;
//        this.mens_sdate = mens_sdate;
//        this.mens_edate = mens_edate;
//        this.memo = memo;
//    }
}
