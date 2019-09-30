package com.bs.android.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bs.android.greendao.DaoMaster;
import com.bs.android.greendao.SearchHistoryModelDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author WWL
 */

public class DatabaseHelper extends DaoMaster.DevOpenHelper {


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, SearchHistoryModelDao.class);

    }
}
