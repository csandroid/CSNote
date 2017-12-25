package commm.example.android.note;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import commm.example.android.note1.R;

public class NoteEditActivity extends Activity {

	private EditText titleEdit;
	private EditText bodyEdit;
	private Button confirmBtn;
	private Long rowId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);

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
				Bundle bundle = new Bundle();
				bundle.putString(NotesDbAdapter.KEY_TITLE, titleEdit.getText().toString());
				bundle.putString(NotesDbAdapter.KEY_BODY, bodyEdit.getText().toString());
				
				if (rowId != null) {
					bundle.putLong(NotesDbAdapter.KEY_ROWID, rowId);
				}
				
				Intent iRes = new Intent();
				iRes.putExtras(bundle);
				setResult(RESULT_OK, iRes);
				finish();
			}
		});
	}

}
