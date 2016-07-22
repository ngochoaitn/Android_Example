package com.example.ohia.test_ormlite.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ohia.test_ormlite.R;
import com.example.ohia.test_ormlite.db.DbMonAnDatabaseHelper;
import com.example.ohia.test_ormlite.db.TbMonAn;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import hotro.CheDoMoActivity;
import hotro.ChuyenDoi;
import hotro.Constant;
import hotro.LayDuLieuTuUngDungKhac;

public class AcModifyMonAn extends AppCompatActivity
{
    TbMonAn _monAn;
    DbMonAnDatabaseHelper _db;
    Dao<TbMonAn, Integer> _tbMonAn;

    private EditText _inputTenMonAn, _inputNguyenLieu, _inputMoTa, _inputMucDo, _inputCachLam;
    private TextView _lblChuaCoHinhAnh;
    ImageView _ivHinhAnh;

    ArrayList<String> _cacHinhAnhDuocSuDung;//Nếu chọn ảnh nhiều lần => sẽ bị dư thừa, cần xóa đi những ảnh không cần thiết

    private CheDoMoActivity _cheDo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_modify_mon_an);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        _cacHinhAnhDuocSuDung = new ArrayList<>();

        this.getControl();
        this.loadData();

        if(_cheDo == CheDoMoActivity.Sua)
            getSupportActionBar().setTitle("Sửa " + _monAn.getTenMonAn());
        else
            getSupportActionBar().setTitle("Thêm món mới");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ac_modify_mon_an, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnLuu:
                try
                {
                    this.luu();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        switch (requestcode)
        {
            case LayDuLieuTuUngDungKhac.REQUEST_CHON_HINH_ANH:
                if (resultcode == Activity.RESULT_OK)
                {
                    this.chonAnh(data);
                }
                break;
            case LayDuLieuTuUngDungKhac.REQUES_CAMERA:
                if (resultcode == Activity.RESULT_OK)
                {
                    //Bitmap photo = (Bitmap) data.getExtras().get("data");
                    this.chonAnh(data);
                }
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        for(String s : _cacHinhAnhDuocSuDung)
        {
            //Nếu ảnh này không được sử dụng thì xóa đi
            if(s != null && s != _monAn.getHinhAnh())
            {
                File fileAnhCanXoa = new File(s);
                fileAnhCanXoa.delete();
            }
        }
        super.onBackPressed();
    }

    private  void chonAnh(Intent data)
    {
        try
        {
//                        Bitmap bitmap = LayDuLieuTuUngDungKhac.HinhAnh.LayHinhAnhSauKhiChon(this, data);
//                        _ivHinhAnh.setImageBitmap(bitmap);
//                        _monAn.setHinhAnh(ChuyenDoi.HinhAnh.AnhSangMangByte(bitmap, Bitmap.CompressFormat.JPEG));
            String duongDanAnhGiamDungLuong = LayDuLieuTuUngDungKhac.HinhAnh.compressImage(data.getData().toString(), this);
            _ivHinhAnh.setTag(duongDanAnhGiamDungLuong);
            _ivHinhAnh.setImageBitmap(ChuyenDoi.HinhAnh.layHinhAnhTuTenTep(duongDanAnhGiamDungLuong));
            _lblChuaCoHinhAnh.setText("");
            //Chưa vội lưu vào đối tượng vội vì có thể không lưu
            _cacHinhAnhDuocSuDung.add(duongDanAnhGiamDungLuong);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void getControl()
    {
        _inputTenMonAn = (EditText) findViewById(R.id.inputTenMonAn);
        _inputNguyenLieu = (EditText) findViewById(R.id.inputNguyenLieu);
        _inputMoTa = (EditText) findViewById(R.id.inputMoTa);
        _inputMucDo = (EditText) findViewById(R.id.inputMucDo);
        _inputCachLam = (EditText) findViewById(R.id.inputCachLam);
        _ivHinhAnh = (ImageView) findViewById(R.id.ivHinhAnh);
        _lblChuaCoHinhAnh = (TextView) findViewById(R.id.lblChuaCoHinhAnh);
    }

    private void loadData()
    {
        _monAn = (TbMonAn) getIntent().getSerializableExtra(Constant.EXTRA_THONG_TIN_MON_AN);
        //Nếu món ăn gửi đến là null thì là thêm mới
        _cheDo = _monAn == null ? CheDoMoActivity.ThemMoi : CheDoMoActivity.Sua;
        if (_cheDo == CheDoMoActivity.ThemMoi)
            _monAn = new TbMonAn();//Nếu thêm mới thì khởi tạo đối tượng mới
        //Hiển thị dữ liệu lên màn hình
        this.bindData(_monAn);
        _db = new DbMonAnDatabaseHelper(this);
        try
        {
            _tbMonAn = _db.getTbMonAnDao();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void bindData(TbMonAn monAn)
    {
        _inputTenMonAn.setText(monAn.getTenMonAn());
        _inputNguyenLieu.setText(monAn.getNguyenLieu());
        _inputMoTa.setText(monAn.getMoTa());
        _inputMucDo.setText(monAn.getMucDo());
        _inputCachLam.setText(monAn.getCachLam());
        if (monAn.getHinhAnh() != null)
        {
            _ivHinhAnh.setImageBitmap(ChuyenDoi.HinhAnh.layHinhAnhTuTenTep(monAn.getHinhAnh()));
            _lblChuaCoHinhAnh.setText("");
            _ivHinhAnh.setTag(monAn.getHinhAnh());
            _cacHinhAnhDuocSuDung.add(_monAn.getHinhAnh());
        }

    }

    private boolean thoaManCacTruongBatBuoc()
    {
        if (TextUtils.isEmpty(_inputTenMonAn.getText()))
        {
            _inputTenMonAn.setError("Trường này là bắt buộc");
            return false;
        }
        return true;
    }

    private void luu() throws Exception
    {
        if (!thoaManCacTruongBatBuoc())
            return;
        _monAn.setTenMonAn(_inputTenMonAn.getText().toString());
        _monAn.setMoTa(_inputMoTa.getText().toString());
        _monAn.setMucDo(_inputMucDo.getText().toString());
        _monAn.setNguyenLieu(_inputNguyenLieu.getText().toString());
        _monAn.setCachLam(_inputCachLam.getText().toString());
        //Ảnh hiện tại
        if(_ivHinhAnh.getTag() != null)
            _monAn.setHinhAnh(_ivHinhAnh.getTag().toString());

        switch (_cheDo)
        {
            case ThemMoi:
                _tbMonAn.create(_monAn);
                break;
            case Sua:
                _tbMonAn.update(_monAn);
                break;
            default:
                throw new Exception("Chưa định nghĩa");
        }
        Intent i = getIntent();
        //Chỉ trả về cho acitivity chi tiết
        i.putExtra(Constant.EXTRA_THONG_TIN_MON_AN_DA_SUA, _monAn);
        setResult(RESULT_OK, i);
        onBackPressed();
        finish();
    }

    public void ivHinhAnh_Click(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_chon_hinh_anh, popupMenu.getMenu());
        popupMenu.show();
    }

    public void btnChonHinhTuBoSuuTap_Click(MenuItem item)
    {
        LayDuLieuTuUngDungKhac.HinhAnh.ChonHinhAnhTuBoSuuTap(this);
    }


    public void btnXoaAnh_Click(MenuItem item) throws SQLException
    {
        String anhCu = _monAn.getHinhAnh();
        if (!TextUtils.isEmpty((anhCu)))
        {
            new File(anhCu).delete();
            _monAn.setHinhAnh(null);
            _tbMonAn.update(_monAn);
            _ivHinhAnh.setImageBitmap(null);
        }
    }

    public void btnChonHinhTuCamera_Click(MenuItem item)
    {
        LayDuLieuTuUngDungKhac.HinhAnh.chupAnhTuCamera(this);
    }
}
