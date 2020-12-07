package com.m2comm.headache.module;

import java.util.HashMap;

public class Urls {

    public String mainUrl = "https://ezv.kr:4447/headache";

    public HashMap<String, String> getUrls = new HashMap<String, String>() {
        {
            put("changePw","/member/change_pwd.php");
            put("findPw","/member/find_pwd.php");
            put("setLogin", "/member/set_member.php");
            put("getLogin", "/member/get_member.php");
            put("getMonthDiary", "/diary/get_month_diary.php");
            put("getDiary", "/diary/get_diary.php");
            put("setDiary", "/diary/set_diary.php");
            put("getStatus","/diary/get_status.php");
            put("notice","/bbs/list.php?code=notice");
            put("news","/bbs/list.php?code=news");
            put("del","/diary/del_diary.php");
            put("getETC","/diary/get_etc_medicine.php");
            put("getNotiCount","/bbs/get_new_count.php");
            put("setMens","/diary/set_mens.php");
            put("getMens","/diary/get_mens.php");
            put("delMens","/diary/del_mens.php");
            put("getRecentDiary","/diary/get_recent_diary.php");
            put("del_etc","/diary/del_etc_code.php");
            put("getMensChk","/diary/get_mens_chk.php");
        }
    };


}
