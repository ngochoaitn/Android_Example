package com.example.ohia.test_ormlite.db;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ngoch on 19/07/2016.
 */
public class DbMonAnDatabaseConfigUtil extends OrmLiteConfigUtil
{
    private static final Class<?>[] classes = new Class[] { TbMonAn.class };
    public static void main(String[] args) throws IOException, SQLException
    {
        String curDirectoty = "user.dir";
        String confPath = "/app/src/main/res/raw/ormlite_config.txt";

        String projectroot = System.getProperty(curDirectoty);

        String fullConfPath = projectroot + confPath;

        File confFile   = new File(fullConfPath);

        if(confFile.exists())
        {
            confFile.delete();
            confFile = new File(fullConfPath);
        }
        writeConfigFile(confFile, classes);
    }
}
