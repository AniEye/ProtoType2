package com.bbv.prototype1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLPros_Og_Teori {
	public static final String KEY_CHAPTERNAME = "chapter";
	public static final String KEY_CHAPERINDEX1NAME = "chapterPart1";
	public static final String KEY_CHAPERINDEX2NAME = "chapterPart2";
	public static final String KEY_FILENAME = "file_name";

	private static final String DATABASE_NAME = "ProtoType1DB";
	private static final String DATABASE_TABLE = "Pros_og_Teori";
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
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ("
					+ KEY_CHAPTERNAME + " TEXT NOT NULL, "
					+ KEY_CHAPERINDEX1NAME + " TEXT, " + KEY_CHAPERINDEX2NAME
					+ " TEXT, " + KEY_FILENAME + " TEXT NOT NULL);");
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

	public String getData() {
		// TODO Auto-generated method stub
		Log.println(Log.ERROR, "SQLDB", "creating string array");
		String[] columns = new String[] { KEY_CHAPTERNAME,
				KEY_CHAPERINDEX1NAME, KEY_CHAPERINDEX2NAME, KEY_FILENAME };
		int[] indexes = new int[columns.length];
		Log.println(Log.ERROR, "SQLDB", "creating cursor");
		// Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null,
		// null,
		// null, null);
		Cursor c = ourDatabase.query(DATABASE_TABLE, null, null, null, null,
				null, null);
		String result = "";
		Log.println(Log.ERROR, "SQLDB", "getting indexes of keys");

		for (int i = 0; i < columns.length; i++) {
			try {
				indexes[i] = c.getColumnIndex(columns[i]);
			} catch (Exception e) {

			}
		}
		int iChapter = c.getColumnIndex(KEY_CHAPTERNAME);
		int iChapterPart1 = c.getColumnIndex(KEY_CHAPERINDEX1NAME);
		int iChapterPart2 = c.getColumnIndex(KEY_CHAPERINDEX2NAME);
		// int iFileName = c.getColumnIndex(KEY_FILENAME);
		result = "index 0: " + indexes[0] + "index 1: " + indexes[1]
				+ "index 2: " + indexes[2] + "index 3: " + indexes[3]
				+ "\nRows: " + c.getCount();
		Log.println(Log.ERROR, "SQLDB", "doing the for loop");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			// + c.getString(iFileName)
			result = result + c.getString(iChapter) + " "
					+ c.getString(iChapterPart1) + " "
					+ c.getString(iChapterPart2) + " " + "\n";
		}
		Log.println(Log.ERROR, "SQLDB", "returning result");
		return result;
	}

	public void close() {
		ourHelper.close();
	}

	public void create() throws SQLException {
		ourHelper.onCreate(ourDatabase);
	}

	public void delete() throws SQLException {
		ourHelper.onUpgrade(ourDatabase, 0, 0);
	}

	public long createEntry(String chapter, String part1, String part2,
			String filename) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();

		cv.put(KEY_CHAPTERNAME, chapter);
		if (!part1.contentEquals("") || part1 == null)
			cv.put(KEY_CHAPERINDEX1NAME, part1);
		if (!part2.contentEquals("") || part2 == null)
			cv.put(KEY_CHAPERINDEX2NAME, part2);
		cv.put(KEY_FILENAME, filename);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
}
