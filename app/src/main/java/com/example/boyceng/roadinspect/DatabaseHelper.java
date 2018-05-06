package com.example.boyceng.roadinspect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DatabaseHelper{
	
	public static final String DATABASE_NAME = "info.db"; //数据库名
	public static final int VERSION = 1;   //版本号
	private static File f = new File("/sdcard/info.db"); // 数据库文件
	private static SQLiteDatabase db;
	private static DBOpenHelper helper;
	
	private static class DBOpenHelper extends SQLiteOpenHelper {
		
		
		public DBOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, VERSION);
		}
		
		/**
		 * 在数据库第一次生成的时候会调用这个方法，同时我们在这个方法里边生成数据库表
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
		
			db.execSQL("DROP TABLE IF EXISTS admin");
			String adminSql = "CREATE TABLE admin (id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
					" name VARCHAR(50), password VARCHAR(50))";
			db.execSQL(adminSql);
		}
		
		/**
		 * 更新或者升级数据库的时候会自动调用这个方法，一般我们会在这个方法中
		 * 删除数据表，然后再创建新的数据表操作。
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
	
	public DatabaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		helper=new DBOpenHelper(context);
		
	}
	

	public void insertAdmin(String name,String password){
		db=helper.getWritableDatabase();
		ContentValues content=new ContentValues();
	    content.put("name",name);
	    content.put("password",password);
		db.insert("admin", null, content);
		db.close();
	}

	
	public boolean checkAdmin(String name){
		db=helper.getWritableDatabase();
	    int i=0;
	    Cursor cursor=db.rawQuery("select * from admin where name=?",new String[]{name});
	    while(cursor.moveToNext()){
	    	i=i+1;
	    }
	    cursor.close();
	    db.close();
	    if(i>0)
	    	return false;
	    else
	    	return true;
	}
	
	public void updatePassword(String name){
		db=helper.getWritableDatabase();
		db.execSQL("update admin set password=? where name=?", new Object[]{name});
		db.close();
	}
	

	public boolean check(String name,String password){

		db=helper.getWritableDatabase();
	    Cursor cursor=db.rawQuery("select * from admin where name=?",new String[]{name});
	    while(cursor.moveToNext()){
	    	String pwd = cursor.getString(cursor.getColumnIndex("password"));
	    	if(pwd.equals(password)){

				cursor.close();
				db.close();
	    		return true;
	    	}
	    }
	    cursor.close();
	    db.close();
	    return false;
	}
}