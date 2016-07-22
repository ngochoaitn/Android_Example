package com.example.ohia.test_ormlite.MonAnAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ohia.test_ormlite.R;
import com.example.ohia.test_ormlite.db.TbMonAn;

import java.util.ArrayList;

import hotro.ChuyenDoi;
import hotro.Constant;
import hotro.RoundedImageView;

/**
 * Created by ngoch on 19/07/2016.
 */
public class TbMonAnAdapter extends ArrayAdapter<TbMonAn>
{

    public TbMonAnAdapter(Context context, ArrayList<TbMonAn> datas)
    {
        super(context, R.layout.list_item, datas);
    }

    @Override
    public View getView(int pos, View convertviewm, ViewGroup parent)
    {
        TbMonAn monAn = getItem(pos);

        if(convertviewm == null)
        {
            convertviewm = LayoutInflater.from(getContext()).inflate(R.layout.list_item2, parent, false);
        }


//        if(pos % 2 == 0)
//            convertviewm.setBackgroundColor(Color.parseColor("#E6E5E5"));
//        else
//            convertviewm.setBackgroundColor(Color.parseColor("#EEEEEE"));

        convertviewm.setTag(monAn);
        ImageView imgHinhAnh = (ImageView)convertviewm.findViewById(R.id.ivHinhAnh);
        TextView lblTenMonAn = (TextView)convertviewm.findViewById(R.id.lblTenMonAn);
        TextView lblMucDo = (TextView)convertviewm.findViewById(R.id.lblMucDo);
        TextView lblMoTa = (TextView)convertviewm.findViewById(R.id.lblMoTa);
        TextView lblSoNguyenLieu = (TextView)convertviewm.findViewById(R.id.lblSoLuongNguyenLieu);
        if(monAn.getHinhAnh()!=null)
            imgHinhAnh.setImageBitmap(RoundedImageView.getCroppedBitmap(ChuyenDoi.HinhAnh.layHinhAnhTuTenTep(monAn.getHinhAnh()), 100));
        else
            //imgHinhAnh.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.no_image));
            imgHinhAnh.setImageBitmap(null);
        lblTenMonAn.setText(monAn.getTenMonAn().toUpperCase());
        lblMucDo.setText(Html.fromHtml(String.format("Mức độ: <b>%s</b>", monAn.getMucDo())));
        lblMoTa.setText(monAn.getMoTa());
        String[] cacNguyenLieu = monAn.getNguyenLieu() != "" && monAn.getNguyenLieu() != null ? monAn.getNguyenLieu().split("\n") : null;
        if(cacNguyenLieu != null && !TextUtils.isEmpty(monAn.getNguyenLieu()))
            lblSoNguyenLieu.setText(String.format("%d nguyên liệu", cacNguyenLieu.length));
        else
            lblSoNguyenLieu.setText("0 nguyên liệu");
        return convertviewm;
    }
}
