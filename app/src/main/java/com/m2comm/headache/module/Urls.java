package com.m2comm.headache.module;

import java.util.HashMap;

public class Urls {

    public String mainUrl = "http://ezv.kr/headache";

    public HashMap<String, String> getUrls = new HashMap<String, String>() {
        {
            put("setLogin", "/member/set_member.php");
            put("getLogin", "/member/get_member.php");
            put("getMonthDiary", "/diary/get_month_diary.php");
            put("getDiary", "/diary/get_diary.php");
            put("setDiary", "/diary/set_diary.php");
            put("getStatus","/diary/get_status.php");
            put("notice","/bbs/list.php?code=notice");
            put("news","/bbs/list.php?code=news");
            put("del","/diary/del_diary.php");
        }
    };


}
