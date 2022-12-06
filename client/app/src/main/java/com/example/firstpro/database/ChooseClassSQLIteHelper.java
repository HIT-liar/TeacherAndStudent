package com.example.firstpro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firstpro.data.Class;
import com.example.firstpro.data.Me;

import java.util.ArrayList;
import java.util.List;

public class ChooseClassSQLIteHelper extends SQLiteOpenHelper {

    private static ChooseClassSQLIteHelper mInstance;
    public static synchronized ChooseClassSQLIteHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new ChooseClassSQLIteHelper(context,"choiceMessageDB",null,1);
        }


        return mInstance;
    }

    public ChooseClassSQLIteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "create table choices(_id integer primary key autoincrement, class_id text, stu_account text)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String insertChoice(String class_id , String stuid){

        boolean flag = false;

        SQLiteDatabase db = this.getWritableDatabase();

        if(db.isOpen()) {

                flag = true;
                String sql = "insert into choices(class_id,stu_account) values(" +
                        "'" + class_id + "'," + "'" + stuid + "')";

                db.execSQL(sql);
        }

        db.close();
        if(flag){
            return "success";
        }else {
            return "fail";
        }
    }

    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, args);
    }

    public List<Class> getAll(String stu_account,Context context){

        List<Class> classList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("choices",new String[]{"class_id"},"stu_account=?",new String[]{stu_account},null,null,null);

        int size = cursor.getCount();
       int class_id_index =  cursor.getColumnIndex("class_id");

       //在课程的本地数据库中寻找
       ClassesSQLIteHelper helper = ClassesSQLIteHelper.getInstance(context);
       SQLiteDatabase classdb = helper.getReadableDatabase();

       String[] class_column = new String[]{"classid","teacheraccount","classname","classnum","credit","location","maxstunum","time","true_stunum"};

       //遍历找到的所有class
        for(int i=0 ; i<size ;i++){

            cursor.moveToNext();
            String class_id = cursor.getString(class_id_index);

            Cursor cl_cursor = classdb.query("classes",class_column,"classid=?",new String[]{class_id}
                    ,null,null,null);

            int size2 = cl_cursor.getCount();
            int classid_index = cl_cursor.getColumnIndex("classid");
            int teacheraccount_index = cl_cursor.getColumnIndex("teacheraccount");
            int classname_index = cl_cursor.getColumnIndex("classname");
            int classnum_index = cl_cursor.getColumnIndex("classnum");
            int credit_index = cl_cursor.getColumnIndex("credit");
            int location_index = cl_cursor.getColumnIndex("location");
            int maxstunum_index = cl_cursor.getColumnIndex("maxstunum");
            int time_index = cl_cursor.getColumnIndex("time");
            int true_stunum_index = cl_cursor.getColumnIndex("true_stunum");
         //加入class
            for(int j=0 ; j<size2 ;j++){
                cl_cursor.moveToNext();
                Class thisClass = new Class(cl_cursor.getString(classid_index),
                                            cl_cursor.getString(classname_index),
                                            cl_cursor.getInt(classnum_index),
                                            cl_cursor.getInt(maxstunum_index),
                                            cl_cursor.getDouble(credit_index),
                                            cl_cursor.getString(location_index),
                                            cl_cursor.getString(time_index),
                                            cl_cursor.getString(teacheraccount_index),
                                            cl_cursor.getInt(true_stunum_index));
                classList.add(thisClass);

            }

            cl_cursor.close();
        }
        cursor.close();
        db.close();
        classdb.close();

        return classList;
    }

    public String deleteOne(String class_id , String stu_id){
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            flag = true;

            String sql = "delete from choices where class_id=? and stu_account=?";

            db.execSQL(sql,new Object[]{class_id,stu_id});
        }

        db.close();
        if(flag) {
            return "success";
        }else {
            return "fail";
        }
    }

    public void update(List<Class> classes, String stu_id, Context context){
        List<String> classidList = new ArrayList<>();
        for(Class cla : classes){
            classidList.add(cla.getClass_id());
        }


        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            String existSQL = "select * from choices where stu_account=?";
            Cursor cursor = db.rawQuery(existSQL, new String[]{stu_id});
            int size = cursor.getCount();
            int class_id_index = cursor.getColumnIndex("class_id");
            for (int i = 0; i < size; i++) {
                cursor.moveToNext();
                String claid = cursor.getString(class_id_index);
                if (classidList.contains(claid)) {
                    classidList.remove(claid);
                } else {
                    String sql = "delete from choices where class_id=? and stu_account=?";
                    db.execSQL(sql,new Object[]{claid,stu_id});
                }
            }
            db.close();

            if(classidList.size()>0) {
                for (String id : classidList) {
                    insertChoice(id, stu_id);
                    for(Class cla : classes){
                        if(cla.getClass_id().equals(id)){
                            ClassesSQLIteHelper.getInstance(context).insertClass(cla);
                            break;
                        }
                    }
                }
            }

        }
    }
}
