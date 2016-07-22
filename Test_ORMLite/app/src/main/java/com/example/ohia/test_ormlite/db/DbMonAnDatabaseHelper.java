package com.example.ohia.test_ormlite.db;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.example.ohia.test_ormlite.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by ngoch on 19/07/2016.
 */
public class DbMonAnDatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "dbMonAn.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<TbMonAn, Integer> _tbMonAnDao;

    public DbMonAnDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, TbMonAn.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1)
    {
        try
        {
            TableUtils.dropTable(connectionSource, TbMonAn.class, false);
            onCreate(sqLiteDatabase, connectionSource);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Dao<TbMonAn, Integer> getTbMonAnDao() throws SQLException
    {
        if(_tbMonAnDao == null)
        {
            _tbMonAnDao = getDao(TbMonAn.class);
        }
        return _tbMonAnDao;
    }
}
