package com.example.ohia.test_ormlite.db;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by ngoch on 18/07/2016.
 */
@DatabaseTable
public class TbMonAn implements Serializable
{
    @DatabaseField(generatedId=true)
    private int idMonAn;

    @DatabaseField(defaultValue = "")
    private String tenMonAn;

    @DatabaseField(defaultValue = "")
    private String moTa;

    @DatabaseField(defaultValue = "")
    private String nguyenLieu;

    @DatabaseField(defaultValue = "")
    private String cachLam;

    @DatabaseField(defaultValue = "")
    private String mucDo;

    @DatabaseField
    private String hinhAnh;

    public String getHinhAnh()
    {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh)
    {
        this.hinhAnh = hinhAnh;
    }

    public TbMonAn()
    {
    }

    public int getIdMonAn()
    {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn)
    {
        this.idMonAn = idMonAn;
    }

    public String getTenMonAn()
    {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn)
    {
        this.tenMonAn = tenMonAn;
    }

    public String getMoTa()
    {
        return moTa;
    }

    public void setMoTa(String moTa)
    {
        this.moTa = moTa;
    }

    public String getNguyenLieu()
    {
        return nguyenLieu;
    }

    public void setNguyenLieu(String nguyenLieu)
    {
        this.nguyenLieu = nguyenLieu;
    }

    public String getCachLam()
    {
        return cachLam;
    }

    public void setCachLam(String cachLam)
    {
        this.cachLam = cachLam;
    }

    public String getMucDo()
    {
        return mucDo;
    }

    public void setMucDo(String mucDo)
    {
        this.mucDo = mucDo;
    }

    public TbMonAn(String tenMonAn, String moTa, String nguyenLieu, String cachLam, String mucDo, String hinhAnh)
    {
        this.tenMonAn = tenMonAn;
        this.moTa = moTa;
        this.nguyenLieu = nguyenLieu;
        this.cachLam = cachLam;
        this.mucDo = mucDo;
        this.hinhAnh = hinhAnh;
    }

    public void cloneValue(TbMonAn des)
    {
        if(des == null)
            return;
        des.setTenMonAn(this.tenMonAn);
        des.setMoTa(this.moTa);
        des.setCachLam(this.cachLam);
        des.setNguyenLieu(this.nguyenLieu);
        des.setHinhAnh(this.hinhAnh);
        des.setMucDo(this.mucDo);
    }
}
