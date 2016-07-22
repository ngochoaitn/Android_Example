package com.example.ohia.test_listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import DB.tbNguoi;
import MyAdapter.NguoiAdapter;

public class ListNguoiActivity extends AppCompatActivity
{
    ListView lvDanhSach;
    ArrayList<tbNguoi> _datas;
    NguoiAdapter nguoiAdapter;
    EditText txtHoTen, txtQueQuan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi);

        txtHoTen = (EditText)findViewById(R.id.inputHoten);
        txtQueQuan = (EditText)findViewById(R.id.inputQueQuan);

        lvDanhSach = (ListView)findViewById(R.id.lstDanhSachNguoi);
        _datas = new ArrayList<>();
        //Không cần truyền cái layout vào nữa vì trong lúc khởi tạo NguoiAdapter đã có rồi
        nguoiAdapter = new NguoiAdapter(this, _datas);

        lvDanhSach.setAdapter(nguoiAdapter);
    }

    public void btnThemNguoi_Click(View view)
    {
        tbNguoi nguoi = null;

        Intent iThemNguoi = new Intent(this, SuaThongTinNguoiActivity.class);
        iThemNguoi.putExtra(SuaThongTinNguoiActivity.EXTRA_THONGTINNGUOI, nguoi);
        startActivityForResult(iThemNguoi, SuaThongTinNguoiActivity.CODE_THEMMOI);
    }

    public void btnXoaItem_Click(View view)
    {
        int id = 1;
        Button btn = (Button)view;
        tbNguoi nguoi = _datas.get((int)btn.getTag());
        if(nguoi != null)
            nguoiAdapter.remove(nguoi);
    }

    public void btnDemDaChon_Click(View view)
    {
        lvDanhSach.invalidate();
        int dem = 0;
        for(int i = 0; i < nguoiAdapter.getCount(); i++)
        {
            if(nguoiAdapter.getItem(i).DaChon)
                dem++;
        }
        Toast.makeText(this, String.format("Đã chọn %s người", dem), Toast.LENGTH_SHORT).show();
    }

    public void btnLuuDanhSach_Click(View view)
    {

    }

    public void chkDaChon_Click(View view)
    {
        CheckBox chk = (CheckBox)view;
        tbNguoi nguoi = _datas.get((int)chk.getTag());
        nguoi.DaChon = chk.isChecked();
    }

    public void btnChonTatCa_Click(View view)
    {
        for(int i = 0; i < nguoiAdapter.getCount(); i++)
            nguoiAdapter.getItem(i).DaChon = true;
        lvDanhSach.invalidateViews();
    }

    tbNguoi _curentNguoi;
    public void btnSuaItem_Click(View view)
    {
        Button btn = (Button)view;
        int pos = (int)btn.getTag();
        tbNguoi nguoi = _datas.get(pos);
        _curentNguoi = nguoi;
        Intent iSuaThongTinNguoi = new Intent(this, SuaThongTinNguoiActivity.class);
        iSuaThongTinNguoi.putExtra(SuaThongTinNguoiActivity.EXTRA_THONGTINNGUOI, nguoi);
        startActivityForResult(iSuaThongTinNguoi, SuaThongTinNguoiActivity.CODE_SUA);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if(data == null)
            return;
        switch (resultcode)
        {
            case SuaThongTinNguoiActivity.CODE_SUA:
                tbNguoi daSua = (tbNguoi)data.getSerializableExtra(SuaThongTinNguoiActivity.EXTRA_THONGTINNGUOI);
                tbNguoi.clone(daSua, _curentNguoi);
                break;
            case SuaThongTinNguoiActivity.CODE_THEMMOI:
                tbNguoi daThem = (tbNguoi)data.getSerializableExtra(SuaThongTinNguoiActivity.EXTRA_THONGTINNGUOI);
                nguoiAdapter.add(daThem);
                break;
            default:
                throw new IllegalArgumentException(String.format("Tao yêu cầu %d mà lại trả về %d LÀ SAO!!!", requestcode, resultcode));
        }
        //Cập nhật lại danh sách
        lvDanhSach.invalidateViews();
    }
}
