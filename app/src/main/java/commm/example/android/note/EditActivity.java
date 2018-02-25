package commm.example.android.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {

	private EditText titleEdit;
	private EditText bodyEdit;
	private Button confirmBtn;
	private Long rowId;
	private NotesDbAdapter db = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);
		db = new NotesDbAdapter(this);
		db.open();
		titleEdit = (EditText) findViewById(R.id.title);
		bodyEdit = (EditText) findViewById(R.id.body);
		confirmBtn = (Button) findViewById(R.id.confirm);

		rowId = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString(NotesDbAdapter.KEY_TITLE);
			String body = extras.getString(NotesDbAdapter.KEY_BODY);
			rowId = extras.getLong(NotesDbAdapter.KEY_ROWID);

			if (title != null)
				titleEdit.setText(title);
			if (body != null)
				bodyEdit.setText(body);
		}
		
		confirmBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {

				db.updateNote(rowId, titleEdit.getText().toString(),  bodyEdit.getText().toString());
				Toast.makeText(EditActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}
