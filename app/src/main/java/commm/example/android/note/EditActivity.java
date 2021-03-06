package commm.example.android.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

	private EditText titleEdit;
	private EditText bodyEdit;
	private Button confirmBtn;
	private int rowId;
	private NoteDbHelper db = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editactivity_layout);
		db = new NoteDbHelper(this);
		db.open();
		titleEdit = (EditText) findViewById(R.id.title);
		bodyEdit = (EditText) findViewById(R.id.body);
		confirmBtn = (Button) findViewById(R.id.confirm);


		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString(NoteDbHelper.KEY_TITLE);
			String body = extras.getString(NoteDbHelper.KEY_BODY);
			rowId = extras.getInt(NoteDbHelper.KEY_ROWID);

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
