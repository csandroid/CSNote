package commm.example.android.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	ListView listView;
	NotesAdapter adapter;

	private String LOGTAG="MainActivity";
	static List<Notebean> notebeanList;
    Button btnAdd;

	private NoteDbHelper db = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);

		db = new NoteDbHelper(this);
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
		adapter= new NotesAdapter(this, R.layout.note_item, notebeanList);
		//绑定 Adapter到控件
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Notebean m= notebeanList.get(i);

				Intent intent = new Intent(MainActivity.this	, EditActivity.class);

				intent.putExtra(NoteDbHelper.KEY_TITLE, notebeanList.get(i).getTitle());
				intent.putExtra(NoteDbHelper.KEY_BODY, notebeanList.get(i).getBody());
				intent.putExtra(NoteDbHelper.KEY_ROWID, notebeanList.get(i).getId());
				startActivity(intent);

			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				//长按删除
				Toast.makeText(MainActivity.this, "长按:"+position, Toast.LENGTH_SHORT).show();

				db.deleteNote(notebeanList.get(position).getId());
				notebeanList.remove(position);

				return true;
			}
		});
	}

	private void createNote() {
		Intent i = new Intent(this, NewActivity.class);
		startActivity(i);
	}

}