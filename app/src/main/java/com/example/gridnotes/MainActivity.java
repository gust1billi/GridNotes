package com.example.gridnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gridnotes.rv.Note;
import com.example.gridnotes.rv.NotesRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager gridLayoutManager; NotesRecyclerViewAdapter myAdapter;

    List<Note> notes;

    ImageView backBtn; ImageView deleteBtn;
    TextView tbTitle; SearchView searchBar;
    FloatingActionButton plusBtn;

    ActivityResultLauncher<Intent>  nextActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    Toast.makeText(MainActivity.this, "yay", Toast.LENGTH_SHORT).show();

                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();

                        assert data != null;
                        if ( data.getBooleanExtra("delete", false) ){
                            if ( !data.getBooleanExtra("newNote", false)){
                                int position = data.getIntExtra("position", 0);
                                myAdapter.notifyItemRemoved( position ); deleteNote(position);
                                myAdapter.notifyItemRangeChanged(position, notes.size());
                            }
                        }

                        // Jika save note baru
                        if ( data.getBooleanExtra("save", false) ){
                            addNote(data.getStringExtra("title"), data.getStringExtra("desc"));
                            myAdapter.notifyItemInserted( notes.size()-1 );
                        } else if (data.getBooleanExtra("update", false)){
                            int position = data.getIntExtra("position", 0);
                            editNote(data.getStringExtra("title"), data.getStringExtra("desc"), position);
                            myAdapter.notifyItemChanged( position );
                        }
//                        Toast.makeText(MainActivity.this, "yay" + position, Toast.LENGTH_SHORT).show(); // data.getIntExtra("position")
                    }
                }
            }
    );

    public void openNextActivity(Intent intent){
        nextActivityLauncher.launch(intent); // startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notes = new ArrayList<>(); populateNotes();

        backBtn = findViewById(R.id.back_icon); // searchBtn = findViewById(R.id.search_icon);
        backBtn.setVisibility(View.INVISIBLE);

        tbTitle = findViewById(R.id.title_toolbar); tbTitle.setText(R.string.main_title);
        tbTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // deleteNote();
                addNote("a", "b");
                // Toast.makeText(MainActivity.this, "Note size: "+ notes.size(), Toast.LENGTH_SHORT).show();
                myAdapter.saveNote();
//                myAdapter.notifyItemChanged( notes.size() );
            }
        });

        plusBtn = findViewById(R.id.floatingActionBtn);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent =
                        new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra("noteTitle", "New Note");
                intent.putExtra("newNote", true);
                openNextActivity(intent);
//                Toast.makeText(MainActivity.this, "Btn Press", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn = findViewById(R.id.delete_icon);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Deleted:" + notes.get(notes.size() -1 ).getTitle(), Toast.LENGTH_SHORT).show();
                deleteNote(notes.size()-1);
            }
        });

        searchBar = findViewById(R.id.mainSearchView);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filter) {
//                Toast.makeText(MainActivity.this, filter, Toast.LENGTH_SHORT).show();
//                if (filter.length() == 0 ){
//                    originalNotes( );
//                } else // LOGIC STATEMENT; The Magic is that we don't need this because we used SearchView
                filterRV(filter);
                return true;
            }
        });

        recyclerView = findViewById(R.id.mainRV);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        myAdapter = new NotesRecyclerViewAdapter(MainActivity.this, notes);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);

        // initRV(notes);

    }

//    private void originalNotes(){
//        myAdapter.filterSearch(notes);
//    } // LOGIC STATEMENT THAT IS NOT NEEDED BECAUSE SEARCHVIEW EXIST

    private void filterRV(String filter) {
        List<Note> filteredList = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTitle().toLowerCase().contains(filter.toLowerCase( ) ) ){
                filteredList.add(note);
            }
        }
        myAdapter.filterSearch(filteredList);
    }

    private void deleteNote(int i) {
        notes.remove(i); // notes.remove(notes.size()-1);
    }

    private void updateNote(List<Note> update){
        myAdapter.updateData(update);
    }

    private void populateNotes() {
        notes.add(new Note("doraemon", "kucing biru"));
        notes.add(new Note("arknight", "tower defense mobile"));
        notes.add(new Note("zelda", "sepuh genshin"));
        notes.add(new Note("air", "untuk diminium"));
        notes.add(new Note("idm", "singkatan dari indomaret"));
        notes.add(new Note("Text Panjang", "Lorem ipsum dolor sit amet bla bla bla bla bla"));
    }

    private void addNote(String title, String desc){
        notes.add(new Note(title, desc));
    }

    private void editNote(String title, String desc, int position){
        notes.set(position, new Note(title, desc));
    }

}