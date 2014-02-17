package com.bbv.prototype1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase {

	// public static final String KEY_ROWID = "_id";
	public static final String KEY_CHAPTER = "chapter";
	public static final String KEY_CHAPTERPART1 = "chapter_part1";
	public static final String KEY_CHAPTERPART2 = "chapter_part2";
	public static final String KEY_FILENAME = "filename";

	private static final String DATABASE_NAME = "ProsTeoriDB";
	private static final String DATABASE_TABLE = "TeoriTable";
	private static final int DATABASE_VERSION = 1;

	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_CHAPTER
					+ " TEXT NOT NULL, " + KEY_CHAPTERPART1 + " TEXT, "
					+ KEY_CHAPTERPART2 + " TEXT, " + KEY_FILENAME
					+ " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public SQLDatabase(Context c) {
		ourContext = c;
	}

	public SQLDatabase open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String chapter, String chapterPart1,
			String chapterPart2, String filename) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CHAPTER, chapter);
		cv.put(KEY_CHAPTERPART1, chapterPart1);
		cv.put(KEY_CHAPTERPART2, chapterPart2);
		cv.put(KEY_FILENAME, filename);
		// check out the null if that's the reason that it doesn't
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public DatabaseContent[] getData() {
		String[] columns = new String[] { KEY_CHAPTER, KEY_CHAPTERPART1,
				KEY_CHAPTERPART2, KEY_FILENAME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";
		DatabaseContent[] content = new DatabaseContent[c.getCount()];
		int iChapter = c.getColumnIndex(KEY_CHAPTER);
		int iChapterPart1 = c.getColumnIndex(KEY_CHAPTERPART1);
		int iChapterPart2 = c.getColumnIndex(KEY_CHAPTERPART2);
		int iFilename = c.getColumnIndex(KEY_FILENAME);

		int currentRow=0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			content[currentRow] = new DatabaseContent();
			content[currentRow].setChapter(c.getString(iChapter));
			content[currentRow].setChapterPart1(c.getString(iChapterPart1));
			content[currentRow].setChapterPart2(c.getString(iChapterPart2));
			content[currentRow].setFileName(c.getString(iFilename));
			currentRow++;
			result = result + c.getString(iChapter) + " "
					+ c.getString(iChapterPart1) + " "
					+ c.getString(iChapterPart2) + " " + c.getString(iFilename)
					+ "\n";
		}

		return content;
	}
	
	public DatabaseContent[] getChapters(){
		
		String[] columns = new String[] { KEY_CHAPTER, KEY_CHAPTERPART1,
				KEY_CHAPTERPART2, KEY_FILENAME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		
		String result = "";
		DatabaseContent[] content = new DatabaseContent[c.getCount()];
		int iChapter = c.getColumnIndex(KEY_CHAPTER);
		int iChapterPart1 = c.getColumnIndex(KEY_CHAPTERPART1);
		int iChapterPart2 = c.getColumnIndex(KEY_CHAPTERPART2);
		int iFilename = c.getColumnIndex(KEY_FILENAME);

		int currentRow=0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			content[currentRow] = new DatabaseContent();
			content[currentRow].setChapter(c.getString(iChapter));
			content[currentRow].setChapterPart1(c.getString(iChapterPart1));
			content[currentRow].setChapterPart2(c.getString(iChapterPart2));
			content[currentRow].setFileName(c.getString(iFilename));
			currentRow++;
			result = result + c.getString(iChapter) + " "
					+ c.getString(iChapterPart1) + " "
					+ c.getString(iChapterPart2) + " " + c.getString(iFilename)
					+ "\n";
		}

		return content;
		
	}

}