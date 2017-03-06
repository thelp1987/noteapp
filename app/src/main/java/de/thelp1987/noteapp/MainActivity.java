package de.thelp1987.noteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import de.thelp1987.noteapp.helper.Notes;
import de.thelp1987.noteapp.helper.NotesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvNotes);

        notes = Notes.createTestList(20);

        NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this, notes);

        rvNotes.setAdapter(notesAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}
