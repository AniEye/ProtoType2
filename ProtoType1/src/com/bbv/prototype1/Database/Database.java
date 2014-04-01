package com.bbv.prototype1.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

	// public static final String KEY_ROWID = "_id";
	public static final String KEY_CHAPTER = "chapter";
	public static final String KEY_CHAPTERPART1 = "chapter_part1";
	public static final String KEY_CHAPTERPART2 = "chapter_part2";

	public static final String KEY_OVING = "oving";

	private static final String DATABASE_NAME = "ProsTeoriDB";
	private static final String DATABASE_TABLE_TEORI = "TeoriTable";
	private static final String DATABASE_TABLE_OVINGER = "OvingTable";

	private static final int DATABASE_VERSION = 1;

	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	/**
	 * This is the helper that initializes and updates the database when 
	 * there isn't a database with the name and updates when the database version 
	 * has been updated
	 * @author Andre
	 *
	 */
	private static class DBHelper extends SQLiteOpenHelper {
		Context c;

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			c = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_TEORI + " ("
					+ KEY_CHAPTER + " TEXT NOT NULL, " + KEY_CHAPTERPART1
					+ " TEXT, " + KEY_CHAPTERPART2 + " TEXT);");

			// might need to create a new thread to take care of this
			// in case the existing database is to big
			String str = "";
			try {
				AssetManager as = c.getAssets();
				InputStream is = as.open("Database/TeoriTable.txt");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				if (is != null) {
					while ((str = reader.readLine()) != null) {
						if (!str.contains("#"))
							if (!str.isEmpty()) {
								db.execSQL("INSERT INTO TeoriTable (chapter,chapter_part1,chapter_part2) VALUES "
										+ str);
							}
					}
				}
				is.close();
			} catch (IOException e) {
				Log.e("Database",
						"TeoriTable.txt does not exist or not able to open it");
			}

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_OVINGER + " ("
					+ KEY_OVING + " TEXT NOT NULL);");

			try {
				AssetManager as = c.getAssets();
				InputStream is = as.open("Database/OvingTable.txt");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				if (is != null) {
					while ((str = reader.readLine()) != null) {
						if (!str.contains("#"))
							if (!str.isEmpty()) {
								db.execSQL("INSERT INTO OvingTable (oving) VALUES "
										+ str);
							}
					}
				}
				is.close();
			} catch (IOException e) {
				Log.e("Database",
						"OvingTable.txt does not exist or not able to open it");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TEORI);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_OVINGER);
			onCreate(db);
		}

	}

	/**
	 * Constructor needs a reference to the context from which the database is
	 * being referenced
	 * @param c
	 */
	public Database(Context c) {
		ourContext = c;
	}

	/**
	 * This will create a reference to the handler for the current context and
	 * return a reference to this database class.
	 * 
	 * @return Database reference
	 * @throws SQLException
	 */
	public Database open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	/**
	 * this is used when reseting the tables in the database. Only used for
	 * developing purposes to avoid changing the DATABASE_VERSION every time
	 * there has been made changes to the tables/database.
	 */
	public void createFromExisting() {
		ourDatabase.delete(DATABASE_TABLE_TEORI, null, null);
		ourDatabase.delete(DATABASE_TABLE_OVINGER, null, null);

		String str = "";
		try {
			AssetManager as = ourContext.getAssets();
			InputStream is = as.open("Database/TeoriTable.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					if (!str.contains("#"))
						if (!str.isEmpty()) {
							ourDatabase
									.execSQL("INSERT INTO TeoriTable (chapter,chapter_part1,chapter_part2) VALUES "
											+ str);
						}
				}
			}
			is.close();
		} catch (IOException e) {
			Log.e("Database", "Could not open or find TeoriTable.txt");
		}

		try {
			AssetManager as = ourContext.getAssets();
			InputStream is = as.open("Database/OvingTable.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					if (!str.contains("#"))
						if (!str.isEmpty()) {
							ourDatabase
									.execSQL("INSERT INTO OvingTable (oving) VALUES "
											+ str);
						}
				}
			}
			is.close();
		} catch (IOException e) {

		}

	}

	/**
	 * Closes the reference/handler to the SQL Database
	 */
	public void close() {
		ourHelper.close();
	}

	/**
	 * will return all the content from the theory table only used in SQLView
	 * for developing purposes for checking all the rows in the theory table.
	 * 
	 * @return
	 */
	public DatabaseContent[] getTheoryData() {
		String[] columns = new String[] { KEY_CHAPTER, KEY_CHAPTERPART1,
				KEY_CHAPTERPART2 };
		Cursor c = ourDatabase.query(DATABASE_TABLE_TEORI, columns, null, null,
				null, null, null);

		DatabaseContent[] content = new DatabaseContent[c.getCount()];
		int iChapter = c.getColumnIndex(KEY_CHAPTER);
		int iChapterPart1 = c.getColumnIndex(KEY_CHAPTERPART1);
		int iChapterPart2 = c.getColumnIndex(KEY_CHAPTERPART2);

		int currentRow = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			content[currentRow] = new DatabaseContent();
			content[currentRow].setChapter(c.getString(iChapter));
			content[currentRow].setChapterPart1(c.getString(iChapterPart1));
			content[currentRow].setChapterPart2(c.getString(iChapterPart2));
			currentRow++;

		}
		return content;
	}

	/**
	 * This method will return the all the content in the assignment/oving table
	 * and since there is only one column in the this table it will return a
	 * string-array with all the elements/rows
	 * 
	 * @return
	 */
	public String[] getOvingTableRows() {
		String[] returnTable;
		try {
			String[] column = { KEY_OVING };
			Cursor c = ourDatabase.query(DATABASE_TABLE_OVINGER, column, null,
					null, null, null, null);
			returnTable = new String[c.getCount()];
			int iOving = c.getColumnIndex(KEY_OVING);
			int currentRow = 0;
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				returnTable[currentRow] = c.getString(iOving);
				currentRow++;
			}
		} catch (Exception e) {
			Log.e("Database", "Can't find table and column your looking for");
			returnTable = new String[] { "Nothing in table" };
		}
		return returnTable;
	}

	/**
	 * Used to retrieve one column from the theoryTable. 'Groupedby' is to make
	 * sure that we only get one instance of something that occur multiple times
	 * in the same column. the tag 'WHERE' is looking for a result where the tag
	 * 'WHERE' occurs.
	 * 
	 * @param column
	 * @param groupedBy
	 * @param where
	 * @return the wanted column
	 */
	public String[] getColumnGrouped(String[] column, String groupedBy,
			String where) {
		if (where == null)
			where = groupedBy + " != '' ";

		// create a cursor that is connected to a table based on where clause
		Cursor c = ourDatabase.query(DATABASE_TABLE_TEORI, column, where, null,
				groupedBy, null, null);
		// create an array of the size equal to the amount of rows in column
		String[] content = new String[c.getCount()];

		int currentRow = 0;
		if (!groupedBy.contentEquals(KEY_CHAPTER)) {// if the content is not
													// grouped by chapters
			content = new String[c.getCount() + 1]; // the return array is to be
													// 1 size bigger
			currentRow = 1;// and the cursors content is to be loaded into the
							// array from index 1
			if (groupedBy.contentEquals(KEY_CHAPTERPART1))// if what we're
															// looking for was
															// chapterpart1
				content[0] = "Choose partition";// the first index
			else if (groupedBy.contentEquals(KEY_CHAPTERPART2))
				content[0] = "Choose partition";

		}
		int iChapter = c.getColumnIndex(groupedBy);// get the index of the
													// column of the column you
													// want to have

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			// here starts the for loop that will fill up the rest of the
			// returned array
			content[currentRow] = c.getString(iChapter);
			currentRow++;

		}
		return content;
	}

}
