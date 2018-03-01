package commm.example.android.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDbHelper {

	private String LOGTAG="NoteDbHelper";
	private static final String DB_NAME = "data1";
	private static final int DB_VERSION = 1;
	
	private static final String TABLE_NAME = "notes";
	static final String KEY_TITLE = "title";
	static final String KEY_BODY = "body";
	static final String KEY_ROWID = "_id";

	private Context ctx = null;
	private NoteSQLiteHelper dbHelper = null;
	private SQLiteDatabase db = null;

	public NoteDbHelper(Context ctx) {
		this.ctx = ctx;		
	}
	
	public NoteDbHelper open() {
		dbHelper = new NoteSQLiteHelper(ctx, DB_NAME, null, DB_VERSION) ;

		db = dbHelper.getWritableDatabase();

		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public int createNote(String title, String body) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_BODY, body);

		int rowId = (int)db.insert(TABLE_NAME, null, values);
		return rowId;
	}

	//根据ID来查询数据
	public Cursor retrieveNote(long rowId) {
		Cursor cur = db.query(true, TABLE_NAME, new String[]{KEY_ROWID, KEY_TITLE, KEY_BODY},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (cur != null) {
			cur.moveToFirst();
		}
		
		return cur;
	}


	
	public Cursor retrieveAllNotes() {
		Cursor cur = db.query(TABLE_NAME, new String[]{KEY_ROWID, KEY_TITLE, KEY_BODY},
				null, null, null, null, null);


		return cur;
	}

	public List<Notebean> getAllNotes()
	{
		Cursor c=retrieveAllNotes();
		ArrayList<Notebean> itemList = new ArrayList<Notebean>();
		c.moveToFirst();
		while (c.moveToNext()) {

			Notebean item = new Notebean();

			item.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
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
		
		int updatedRows = db.update(TABLE_NAME, values, KEY_ROWID + "=" + rowId, null);
		return updatedRows > 0;
	}

	public boolean deleteNote(long rowId) {
		int deletedRows = db.delete(TABLE_NAME, KEY_ROWID + "=" + rowId, null);
		return deletedRows > 0;
	}
	
}
