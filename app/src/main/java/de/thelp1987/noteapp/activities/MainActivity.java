package de.thelp1987.noteapp.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import de.thelp1987.noteapp.R;
import de.thelp1987.noteapp.helper.DBHelper;
import de.thelp1987.noteapp.helper.NotesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList notes;
    private DBHelper dbHelper;

    private FloatingActionButton fabMain, fabMaps, fabShare, fabAdd;
    private LinearLayout fabLayoutMaps, fabLayoutShareNote, fabLayoutAddNote;
    private View fabBGLayout;
    private boolean isFABOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List load into Recycler View
        RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvNotes);

        dbHelper = new DBHelper(MainActivity.this);
        notes = dbHelper.getAllNotesTitle();

        final NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this, notes);
        notesAdapter.notifyDataSetChanged();

        rvNotes.setAdapter(notesAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        /*
        List load END
         */

        /*
        FAB Menu founded by: https://github.com/ajaydewari/FloatingActionButtonMenu
         */
        // FAB Menu
        fabLayoutMaps= (LinearLayout) findViewById(R.id.fabLayoutMaps);
        fabLayoutShareNote= (LinearLayout) findViewById(R.id.fabLayoutShareNote);
        fabLayoutAddNote= (LinearLayout) findViewById(R.id.fabLayoutAddNote);

        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabMaps = (FloatingActionButton) findViewById(R.id.fabMaps);
        fabShare= (FloatingActionButton) findViewById(R.id.fabShare);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        fabBGLayout=findViewById(R.id.fabBGLayout);


        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });
        /*
        FAB Menu END
         */

        /*
        Sub Menu binding -> Intention
         */
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareNoteActivity.class);
                startActivity(intent);
            }
        });

        fabMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMapsActivity.class);
                startActivity(intent);
            }
        });
        /*
        Sub Menu Binding END
         */

        dbHelper.close();
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayoutMaps.setVisibility(View.VISIBLE);
        fabLayoutShareNote.setVisibility(View.VISIBLE);
        fabLayoutAddNote.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fabMain.animate().rotationBy(180);
        fabLayoutMaps.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayoutShareNote.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayoutAddNote.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fabMain.animate().rotationBy(-180);
        fabLayoutMaps.animate().translationY(0);
        fabLayoutShareNote.animate().translationY(0);
        fabLayoutAddNote.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayoutMaps.setVisibility(View.GONE);
                    fabLayoutShareNote.setVisibility(View.GONE);
                    fabLayoutAddNote.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
    }
}
