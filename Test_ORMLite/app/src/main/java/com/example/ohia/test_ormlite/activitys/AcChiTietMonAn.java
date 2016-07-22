package com.example.ohia.test_ormlite.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ohia.test_ormlite.*;
import com.example.ohia.test_ormlite.db.DbMonAnDatabaseHelper;
import com.example.ohia.test_ormlite.db.TbMonAn;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

import hotro.ChuyenDoi;
import hotro.Constant;

public class AcChiTietMonAn extends AppCompatActivity
{
    private final int _REQUEST_SUA_THONG_TIN_MON_AN = 1;

    TbMonAn _monAnCanHienThi;

    TextView _lblTenMonAn, _lblMucDo, _lblMoTa, _lblNguyenLieu, _lblCachLam;
    ImageView _ivHinhAnh;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chi_tiet_mon_an);

        //Hiển thị bar để thêm menu vào
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        //Hiển thị nút back
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTextOnBar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        _lblTenMonAn = (TextView)findViewById(R.id.lblTenMonAn);
        _lblMucDo = (TextView)findViewById(R.id.lblMucDo);
        _lblMoTa = (TextView)findViewById(R.id.lblMoTa);
        _lblNguyenLieu = (TextView)findViewById(R.id.lblNguyenLieu);
        _lblCachLam = (TextView)findViewById(R.id.lblCachLam);
        _ivHinhAnh = (ImageView)findViewById(R.id.ivHinhAnh);

        _monAnCanHienThi = (TbMonAn) getIntent().getSerializableExtra(Constant.EXTRA_THONG_TIN_MON_AN);
        this.binData(_monAnCanHienThi);
        getSupportActionBar().setTitle("Thông tin món");

    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        switch (requestcode)
        {
            case _REQUEST_SUA_THONG_TIN_MON_AN:
                if(resultcode == AcModifyMonAn.RESULT_OK && data != null)
                {
                    Intent i = getIntent();
                    TbMonAn monAnDaChinhSua = (TbMonAn)data.getSerializableExtra(Constant.EXTRA_THONG_TIN_MON_AN_DA_SUA);
                    //Cập nhật lại thông tin
                    monAnDaChinhSua.cloneValue(_monAnCanHienThi);
                    //Hiển thị thông tin mới
                    this.binData(monAnDaChinhSua);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_ac_chi_tiet_mon_an, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnSuaThongTinMonAn:
                Intent i = new Intent(this, AcModifyMonAn.class);
                i.putExtra(Constant.EXTRA_THONG_TIN_MON_AN, _monAnCanHienThi);
                startActivityForResult(i, _REQUEST_SUA_THONG_TIN_MON_AN);
                return true;
            case R.id.btnXoaMonAn:
                this.xoaMonAn();
                return true;
            case R.id.btnChiaSeMonAn:
                return true;
            default:
                onBackPressed();
                return true;
        }
    }

    private void xoaMonAn()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format("Xóa %s", _monAnCanHienThi.getTenMonAn()));
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                try
                {
                    xoa();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void xoa() throws SQLException
    {
        DbMonAnDatabaseHelper db = new DbMonAnDatabaseHelper(this);
        Dao<TbMonAn, Integer> tbMonAnIntegerDao = db.getTbMonAnDao();

        if(_monAnCanHienThi.getHinhAnh()!=null)
        {
            File f = new File(_monAnCanHienThi.getHinhAnh());
            if (f.exists())
                f.delete();
        }
        tbMonAnIntegerDao.delete(_monAnCanHienThi);
        finish();
    }

    private void binData(TbMonAn monAn)
    {
        if(monAn == null)
            return;
        _lblTenMonAn.setText(monAn.getTenMonAn().toUpperCase());
        _lblMucDo.setText(monAn.getMucDo());
        _lblMoTa.setText(Html.fromHtml(String.format("\t<div style=\"text-align: justify\"><b><i>%s</i></b></div>",monAn.getMoTa().replace("\n", "<br>"))));
        _lblMucDo.setText(Html.fromHtml(String.format("Mức độ: <b>%s</b>", monAn.getMucDo())));
        if(!TextUtils.isEmpty(monAn.getNguyenLieu()))
            _lblNguyenLieu.setText("+ " + monAn.getNguyenLieu().replace("\n", "\n+ "));

        _lblCachLam.setText(monAn.getCachLam());
        if(monAn.getHinhAnh() != null)
        {
            _ivHinhAnh.setImageBitmap(ChuyenDoi.HinhAnh.layHinhAnhTuTenTep(monAn.getHinhAnh()));
            _ivHinhAnh.setTag(monAn.getHinhAnh());
        }
        else
            _ivHinhAnh.setImageBitmap(null);

    }


    public void btnSuaMonAn_Click(View view)
    {
        Intent i = new Intent(this, AcModifyMonAn.class);
        i.putExtra(Constant.EXTRA_THONG_TIN_MON_AN, _monAnCanHienThi);
        startActivityForResult(i, _REQUEST_SUA_THONG_TIN_MON_AN);
    }

    public void btnXoaMonAn_Click(View view)
    {

    }

    public void btnChiaSe_Click(View view)
    {

    }
}

