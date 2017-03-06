package de.thelp1987.noteapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.thelp1987.noteapp.R;
import de.thelp1987.noteapp.helper.DBHelper;

public class AddNoteActivity extends AppCompatActivity {
    private TextView noteTitle, noteContent;
    private Button btnSave;
    private DBHelper dbHelper;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setTitle("Adding Note");

        dbHelper = new DBHelper(AddNoteActivity.this);

        noteTitle = (TextView) findViewById(R.id.noteTitleText);
        noteContent = (TextView) findViewById(R.id.noteContentText);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insertNote(noteTitle.getText().toString(), noteContent.getText().toString(), "");
                showMessageCloseActivity(v);
            }
        });
    }

    private void showMessageCloseActivity(View view){
        Snackbar.make(view,
                getResources().getText(R.string.note_saved_succes),
                Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }
}


