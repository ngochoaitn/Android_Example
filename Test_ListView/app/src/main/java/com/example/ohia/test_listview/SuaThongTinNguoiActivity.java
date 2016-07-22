package com.example.ohia.test_listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import DB.tbNguoi;

public class SuaThongTinNguoiActivity extends AppCompatActivity
{
    public static final String EXTRA_THONGTINNGUOI = "ThongTInNguoi";
    public static final int CODE_THEMMOI = 1;
    public static final int CODE_SUA = 2;

    private tbNguoi _thongTinNguoi;
    private EditText _inputHoTen, _inputQueQuan;
    private int _requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_nguoi);

        //Đọc thông tin chuyển sang
        _thongTinNguoi = (tbNguoi)getIntent().getSerializableExtra(EXTRA_THONGTINNGUOI);
        if(_thongTinNguoi == null)
        {
            _thongTinNguoi = new tbNguoi("", "");
            _requestCode = CODE_THEMMOI;
        }
        else
        {
            _requestCode = CODE_SUA;
        }

        _inputHoTen = (EditText)findViewById(R.id.inputHoten);
        _inputQueQuan = (EditText)findViewById(R.id.inputQueQuan);

        _inputHoTen.setText(_thongTinNguoi.HoTen);
        _inputQueQuan.setText(_thongTinNguoi.QueQuan);
    }

    public void btnXacNhan_Click(View view)
    {
        _thongTinNguoi.HoTen = _inputHoTen.getText().toString();
        _thongTinNguoi.QueQuan = _inputQueQuan.getText().toString();
        Intent iParent = getIntent();
        iParent.putExtra(EXTRA_THONGTINNGUOI, _thongTinNguoi);
        setResult(_requestCode, iParent);
        finish();
    }
}
