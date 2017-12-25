package commm.example.android.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotesDbAdapter {

	private String LOGTAG="NotesDbAdapter";
	private static final String DB_NAME = "data1";
	private static final int DB_VERSION = 2;
	
	private static final String TBL_NAME = "notes";
	static final String KEY_TITLE = "title";
	static final String KEY_BODY = "body";
	static final String KEY_ROWID = "_id";

	private Context ctx = null;
	private SQLiteOpenHelper dbHelper = null;
	private SQLiteDatabase db = null;

	public NotesDbAdapter(Context ctx) {
		this.ctx = ctx;		
	}
	
	public NotesDbAdapter open() {
		dbHelper = new SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL("create table notes (_id integer primary key autoincrement, " +
						"title text not null, body text not null)");
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
				//TODO on DB upgrade
			}
			
		};
		
		db = dbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createNote(String title, String body) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_BODY, body);
		
		long rowId = db.insert(TBL_NAME, null, values);
		return rowId;
	}
	
	public Cursor retrieveNote(long rowId) {
		Cursor cur = db.query(true, TBL_NAME, new String[]{KEY_ROWID, KEY_TITLE, KEY_BODY},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (cur != null) {
			cur.moveToFirst();
		}
		
		return cur;
	}


	
	public Cursor retrieveAllNotes() {
		Cursor cur = db.query(TBL_NAME, new String[]{KEY_ROWID, KEY_TITLE, KEY_BODY},
				null, null, null, null, null);


		return cur;
	}

	public List<Notebean> getAllNotes()
	{
		Cursor c=retrieveAllNotes();
		ArrayList<Notebean> itemList = new ArrayList<Notebean>();
		while (c.moveToNext()) {

			Notebean item = new Notebean();

			item.setId(c.getString(c.getColumnIndex(KEY_ROWID)));
			item.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
			item.setBody(c.getString(c.getColumnIndex(KEY_BODY)));


				Log.v(LOGTAG, "Notebean-" + item.toString());


			itemList.add(item);

		}
		return itemList;

	}
	
	public boolean updateNote(long rowId, String title, String body) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_BODY, body);
		
		int updatedRows = db.update(TBL_NAME, values, KEY_ROWID + "=" + rowId, null);
		return updatedRows > 0;
	}

	public boolean deleteNote(long rowId) {
		int deletedRows = db.delete(TBL_NAME, KEY_ROWID + "=" + rowId, null);
		return deletedRows > 0;
	}
	
}
