package commm.example.android.note;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity {

	private EditText titleEdit;
	private EditText bodyEdit;
	private Button confirmBtn;
	private Long rowId;
	private NotesDbAdapter db = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newactivity_layout);
		db = new NotesDbAdapter(this);
		db.open();
		titleEdit = (EditText) findViewById(R.id.title);
		bodyEdit = (EditText) findViewById(R.id.body);
		confirmBtn = (Button) findViewById(R.id.confirm);

		rowId = null;

		
		confirmBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				db.createNote(titleEdit.getText().toString(), bodyEdit.getText().toString());
				Toast.makeText(NewActivity.this, "增加成功", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}
