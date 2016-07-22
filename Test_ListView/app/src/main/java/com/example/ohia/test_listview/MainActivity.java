package com.example.ohia.test_listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    ListView lv1;
    ArrayAdapter<String> adapter;
    int _dem = 0;
    ArrayList<String> _datas;
    EditText txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _datas = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, _datas);

        lv1 = (ListView)findViewById(R.id.lst1);
        lv1.setAdapter(adapter);

        txt1 = (EditText)findViewById(R.id.txt1);
    }

    public void btnThem_Click(View view)
    {
        //_datas[_dem++] = txt1.getText().toString();
        adapter.add(txt1.getText().toString());
    }

    public void btnXoa_Click(View view)
    {
        adapter.remove(txt1.getText().toString());
    }

    public void btnAc2_Click(View view)
    {
        Intent intent = new Intent(this, ListNguoiActivity.class);
        startActivity(intent);
    }
}
