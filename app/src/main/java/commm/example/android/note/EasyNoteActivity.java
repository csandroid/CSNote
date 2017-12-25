package commm.example.android.note;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

import commm.example.android.note1.R;

public class EasyNoteActivity extends ListActivity {

	private String LOGTAG="EasyNoteActivity";
	static List<Notebean> mydata;
    Button btnAdd;

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	
	private NotesDbAdapter db = null;
	private Cursor cur = null;
	
//	private int counter = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);

        initView();

        db = new NotesDbAdapter(this);
        db.open();
        listAllNotes();
        this.registerForContextMenu(getListView());
    }

    void initView()
    {
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });
    }
    
    private void listAllNotes() {


    	cur = db.retrieveAllNotes();

		mydata=db.getAllNotes();
		Log.v(LOGTAG,"List:"+mydata);

		String[] from = new String[]{NotesDbAdapter.KEY_TITLE};
    	int[] to = new int[]{R.id.textrow};
    	
    	SimpleCursorAdapter notes =
    			new SimpleCursorAdapter(this, R.layout.notes_row, cur, from, to);
    	this.setListAdapter(notes);
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.create_note);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}
    
	private void createNote() {
		Intent i = new Intent(this, NoteEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
			db.deleteNote(info.id);
			listAllNotes();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.delete_note);
	}

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, NoteEditActivity.class);
		cur.moveToPosition(position);
		i.putExtra(NotesDbAdapter.KEY_TITLE, cur.getString(cur.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
		i.putExtra(NotesDbAdapter.KEY_BODY, cur.getString(cur.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Bundle extras = data.getExtras();
		switch (requestCode) {
		case ACTIVITY_EDIT:
			Long rowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
			if (rowId != null) {
				String title = extras.getString(NotesDbAdapter.KEY_TITLE);
				String body = extras.getString(NotesDbAdapter.KEY_BODY);
				db.updateNote(rowId, title, body);
			}
			break;
		case ACTIVITY_CREATE:
			String title = extras.getString(NotesDbAdapter.KEY_TITLE);
			String body = extras.getString(NotesDbAdapter.KEY_BODY);
			db.createNote(title, body);	
			break;
		}
	}
	
}