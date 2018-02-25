package commm.example.android.note;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	ListView listView;
	NotesAdapter adapter;

	private String LOGTAG="MainActivity";
	static List<Notebean> notebeanList;
    Button btnAdd;

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private NotesDbAdapter db = null;
	private Cursor cur = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);

		db = new NotesDbAdapter(this);
		db.open();

		notebeanList =db.getAllNotes();
        initView();

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

		listView = (ListView) findViewById(R.id.listview);

		//建立Adapter并且绑定数据源
		adapter= new NotesAdapter(this, R.layout.item, notebeanList);
		//绑定 Adapter到控件
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Notebean m= notebeanList.get(i);

				Intent intent = new Intent(MainActivity.this	, EditActivity.class);

				intent.putExtra(NotesDbAdapter.KEY_TITLE, notebeanList.get(i).getTitle());
				intent.putExtra(NotesDbAdapter.KEY_BODY, notebeanList.get(i).getBody());
				intent.putExtra(NotesDbAdapter.KEY_ROWID, notebeanList.get(i).getId());
				startActivity(intent);

			}
		});
	}

	private void createNote() {
		Intent i = new Intent(this, NewActivity.class);
		startActivity(i);
	}

}