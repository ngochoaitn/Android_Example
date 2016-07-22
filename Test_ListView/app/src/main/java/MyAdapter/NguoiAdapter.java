package MyAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import DB.tbNguoi;
import com.example.ohia.test_listview.R;

import java.util.ArrayList;

/**
 * Created by ngoch on 17/07/2016.
 */
public class NguoiAdapter extends ArrayAdapter<tbNguoi>
{
    public NguoiAdapter(Context context, ArrayList<tbNguoi> nguois)
    {
        super(context, 0, nguois);
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup parent)
    {
        tbNguoi nguoi = getItem(pos);
        if(convertview == null)
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.list_item_nguoi, parent, false);
        }

        TextView txtHoTen = (TextView)convertview.findViewById(R.id.txtHoten);
        TextView txtQueQuan = (TextView)convertview.findViewById(R.id.txtQueQuan);
        Button btnXoaItem = (Button)convertview.findViewById(R.id.btnXoaItem);
        CheckBox chkDaChon = (CheckBox) convertview.findViewById(R.id.chkDaChon);
        Button btnSuaItem = (Button)convertview.findViewById(R.id.btnSuaItem);

        txtHoTen.setText(nguoi.HoTen);
        txtQueQuan.setText(nguoi.QueQuan);
        chkDaChon.setChecked(nguoi.DaChon);

        btnXoaItem.setTag(pos);
        chkDaChon.setTag(pos);
        btnSuaItem.setTag(pos);

        return convertview;
    }
}
