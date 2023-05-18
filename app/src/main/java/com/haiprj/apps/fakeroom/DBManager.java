package com.haiprj.apps.fakeroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.apps.fakeroom.model.ThuChi;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private final static String DB_NAME = "QLTC";
    private final static int VERSION = 7;


    public DBManager(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table ThuChi (id integer primary key autoincrement, TenKhoan varchar(255), NgayThang varchar(50), SoTien float, IsThu integer)";
        db.execSQL(query);
    }

    public List<ThuChi> getAll() {

        ArrayList<ThuChi> list = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from ThuChi", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String tk = cursor.getString(1);
            String date = cursor.getString(2);
            float st = cursor.getFloat(3);
            boolean isThu = cursor.getInt(4) != 0;
            ThuChi thuChi = new ThuChi(
                    tk,
                    date,
                    st,
                    isThu
            );

            thuChi.setId(id);
            list.add(thuChi);
            cursor.moveToNext();
        }
        cursor.close();

        List<ThuChi> sortList = new ArrayList<>();

        list.forEach(thuChi -> {
            if (thuChi.isThu()) {
                sortList.add(thuChi);
            }
        });
        list.forEach(thuChi -> {
            if (!thuChi.isThu()) {
                sortList.add(thuChi);
            }
        });

        return sortList;
    }

    public ThuChi getById(int id) {
        String[] args = new String[] {
                String.valueOf(id)
        };
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from ThuChi where id = ?", args);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ThuChi thuChi = new ThuChi(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getInt(4) != 0
            );
            thuChi.setId(cursor.getInt(0));
            cursor.close();
            return thuChi;
        }
        return  null;
    }

    public void insert(ThuChi thuChi) {
        String[] args = new String[] {
                thuChi.getTenKhoan(),
                thuChi.getNgayThang(),
                String.valueOf(thuChi.getSoTien()),
                String.valueOf(thuChi.isThu() ? 1 : 0)
        };
        getWritableDatabase().execSQL("insert into ThuChi (TenKhoan, NgayThang, SoTien, IsThu) values (?,?,?,?)", args);
    }

    public void update(ThuChi thuChi) {
        String[] args = new String[] {
                thuChi.getTenKhoan(),
                thuChi.getNgayThang(),
                String.valueOf(thuChi.getSoTien()),
                String.valueOf(thuChi.isThu() ? 1 : 0),
                String.valueOf(thuChi.getId())
        };
        getWritableDatabase().execSQL("update ThuChi set TenKhoan = ?, NgayThang = ?,SoTien = ?, IsThu = ? where id = ?;", args);
    }

    public void delete(int id) {
        String[] args = new String[] {
                String.valueOf(id)
        };
        getWritableDatabase().execSQL("delete from ThuChi where id = ?;", args);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
