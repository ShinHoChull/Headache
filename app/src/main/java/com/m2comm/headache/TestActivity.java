package com.m2comm.headache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.m2comm.headache.DTO.ListDTO;
import com.m2comm.headache.DTO.ListViewAdapter;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity  {

    GridView gridView;
    ListView listview;
    ArrayList<ListDTO> arrayList;
    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.gridView = findViewById(R.id.scheduleView);
        //this.gridView.setAdapter();
        this.listview = findViewById(R.id.listview);
        this.arrayList = new ArrayList<>();

        for ( int i = 0, j = 150; i < j ; i ++ ) {
            double dValue = Math.random();
            this.arrayList.add(new ListDTO( i , (int) (dValue * j) + 1 , "신용철"+i , "주주주주주ㅜㅈ소소ㅗ소소소소"));
        }

        this.listViewAdapter = new ListViewAdapter(this.arrayList , this , this);
        this.listview.setAdapter(this.listViewAdapter);

    }


}
