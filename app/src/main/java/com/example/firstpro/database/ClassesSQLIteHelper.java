package com.example.firstpro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firstpro.data.Class;

public class ClassesSQLIteHelper extends SQLiteOpenHelper {

    private static ClassesSQLIteHelper mInstance;
    public static synchronized ClassesSQLIteHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new ClassesSQLIteHelper(context,"classMessageDB",null,1);
        }
        return mInstance;
    }

    public ClassesSQLIteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "create table classes(_id integer primary key autoincrement, classid text, teacheraccount text, classname text, classnum integer, credit real, location text, maxstunum integer, time text, true_stunum integer)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertClass(Class clas){


        SQLiteDatabase db = this.getWritableDatabase();

        if(db.isOpen()) {

            String taccount = clas.getTeacheraccount();
            String classname = clas.getClass_name();
            String classnum =( (Integer)clas.getClass_num()).toString();
            String credit = String.valueOf(clas.getCourse_credit());
            String location = clas.getLocation_Of_Class();
            String maxstunum = ((Integer)clas.getMax_stu_num()).toString();
            String time = clas.getTime();
            String class_id = clas.getClass_id();
            String true_stu_num = ((Integer)clas.getTrue_stu_num()).toString();

            String sql = "insert into classes(classid,teacheraccount,classname,classnum,credit,location,maxstunum,time,true_stunum) values(" +
                    "'" + class_id + "'," +"'" + taccount + "'," + "'" + classname + "'," +
                    classnum + "," + credit + "," + "'" + location + "'," +
                    maxstunum + "," + "'" + time + "',"+true_stu_num+')';

            db.execSQL(sql);
        }

        db.close();
    }

    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, args);
    }

    public void deleteOne(String class_id ){

        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {


            String sql = "delete from classes where classid=?";

            db.execSQL(sql,new Object[]{class_id});
        }

        db.close();

    }

}
