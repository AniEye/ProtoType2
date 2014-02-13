package com.bbv.prototype1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLPros_Og_Teori {
	public static final String KEY_CHAPTERNAME = "_id";
	public static final String KEY_CHAPERINDEX1NAME = "persons_name";
	public static final String KEY_CHAPERINDEX2NAME = "persons_hotness";
	public static final String KEY_FILENAME = "persons_hotness";
	
	

	private static final String DATABASE_NAME = "HotOrNotdb";
	private static final String DATABASE_TABLE = "peopleTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_CHAPTERNAME
					+ " TEXT NOT NULL, " + KEY_CHAPERINDEX1NAME
					+ " TEXT , " + KEY_CHAPERINDEX2NAME + " TEXT"+ KEY_FILENAME
					+ " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);

		}

	}

	public SQLPros_Og_Teori(Context c) {
		ourContext = c;
	}

	public SQLPros_Og_Teori open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
}
