package hotro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by ngoch on 19/07/2016.
 */
public class ThongBao
{
    private static KetQuaThongBao _ketQuaThongBao;
    public static KetQuaThongBao HopThoaiThongBao(Context context, String noidung)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(noidung);

        _ketQuaThongBao = KetQuaThongBao.Cancel;
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                _ketQuaThongBao = KetQuaThongBao.Ok;
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                _ketQuaThongBao = KetQuaThongBao.Cancel;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        return _ketQuaThongBao;
    }
}
