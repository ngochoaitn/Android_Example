package ThaoTacDanhBa;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoch on 17/07/2016.
 */
public class DanhBa
{
    static String _contactsDisplayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? Contacts.DISPLAY_NAME : Contacts.DISPLAY_NAME_PRIMARY;
    static String[] _projection = new String[]{
            Contacts._ID,
            _contactsDisplayName,
            Contacts.SEND_TO_VOICEMAIL
    };

    //Lấy thông tin danh bạ khi đã biết id
    public static ThongTinDanhBa LayThongTinDanhBa(Context context, int id)
    {
        String selecttion = Contacts._ID + " = ";

        String[] args = new String[]{String.valueOf(id)};

        CursorLoader cl = new CursorLoader(context, Contacts.CONTENT_URI, _projection, selecttion, args, Contacts._ID);
        Cursor c = cl.loadInBackground();

        c.moveToFirst();

        int dem = 0;
        int idDanhBa = 0;
        String tenHienThi = "", email = "";
        while(c.isBeforeFirst())
        {
            idDanhBa = Integer.parseInt(c.getString(c.getColumnIndex(Contacts._ID)));
            tenHienThi = c.getString(c.getColumnIndex(_contactsDisplayName));
            email = c.getString(c.getColumnIndex(Contacts.SEND_TO_VOICEMAIL));
        }

        return new ThongTinDanhBa(idDanhBa, tenHienThi, email);
    }

}
