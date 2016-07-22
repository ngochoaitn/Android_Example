package com.example.ohia.testcontenprovider_searchcontacts;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView lvDanhSach;
    EditText txtTinKiem, txtKetQua;
    private SimpleCursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getLoaderManager().initLoader(0, null, null);
        lvDanhSach = (ListView)findViewById(R.id.list);
        txtTinKiem = (EditText)findViewById(R.id.txtTimKiem);
        txtKetQua = (EditText)findViewById(R.id.txtKetQua);
    }

    public String DanhSachDanhBa(String tukhoa)
    {
        ContentResolver cr = getContentResolver();

        String displayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                : ContactsContract.Contacts.DISPLAY_NAME;
        String[] projection = new String[]{ ContactsContract.Contacts._ID,
                displayName,
                ContactsContract.Contacts.HAS_PHONE_NUMBER};

        String selection = displayName + " LIKE ? ";
        //selection = ContactsContract.Contacts._ID + "=" + txtTinKiem.getText().toString();
        String[] args = new String[]{"%" + tukhoa + "%"};
        //Cursor c = cr.query(uriDanhBa, projection, selection, null, null);
        CursorLoader cl = new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, projection, selection, args, null);
        //Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null);
        Cursor c = cl.loadInBackground();
        c.moveToFirst();

        String s = "";
        txtKetQua.setText(s);
        int dem = 0;
        while(!c.isAfterLast())
        {
            //if(dem++ > 10)
             //   break;
            s += c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)) + ", ";
            s += c.getString(c.getColumnIndex(displayName)) + ", ";
            s += c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) + "\n";
            c.moveToNext();
        }
        return s;
    }

    public void btnTimKiemDanhBa_Click(View view)
    {
        txtKetQua.setText(this.DanhSachDanhBa(txtTinKiem.getText().toString()));
    }
}
