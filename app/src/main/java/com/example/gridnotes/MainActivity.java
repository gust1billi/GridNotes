package com.example.gridnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gridnotes.rv.Note;
import com.example.gridnotes.rv.NotesRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager gridLayoutManager; NotesRecyclerViewAdapter myAdapter;

    List<Note> notes; List<Note> temp = new ArrayList<>();

    ImageView backBtn; // ImageView searchBtn;
    TextView tbTitle; TextInputEditText searchBar;
    FloatingActionButton plusBtn;

    ActivityResultLauncher<Intent>  nextActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    Toast.makeText(MainActivity.this, "yay", Toast.LENGTH_SHORT).show();

                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();

                        if ( data.getBooleanExtra("delete", false ) ){
                            int position = data.getIntExtra("position", 0);
                            deleteNote(position-1); myAdapter.notifyItemChanged( notes.size() );
                        }

                        // Jika save note baru
                        if ( data.getBooleanExtra("save", false) ){
//                            Toast.makeText(MainActivity.this,
//                                    data.getStringExtra("title") + data.getStringExtra("desc"),
//                                    Toast.LENGTH_SHORT).show();
                            addNote(data.getStringExtra("title"), data.getStringExtra("desc"));
                            myAdapter.notifyItemChanged(notes.size() );
                        } else if (data.getBooleanExtra("update", false)){
                            int position = data.getIntExtra("position", 0);
                            editNote(data.getStringExtra("title"), data.getStringExtra("desc"), position);
                            myAdapter.notifyDataSetChanged( );
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
                myAdapter.notifyItemChanged( notes.size() );
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

        searchBar = findViewById(R.id.search_bar);
        
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchBar.getText().length() == 0){
                    initRV(notes); myAdapter.notifyItemChanged( notes.size() );
                } else {
                    temp.clear();
                    for (Note note : notes
                         ) {
                        if (note.getTitle().contains(searchBar.getText() )){
                            temp.add(note);
                        }
                        initRV(temp);
                        myAdapter.notifyItemChanged(temp.size() );
                    }
                } // END of elif String Matched of Searchbox


//                Toast.makeText(MainActivity.this, "SearchBar: " +
//                        searchBar.getText(), Toast.LENGTH_SHORT).show();
            }
        });
//        searchBtn.setBackgroundResource(R.drawable.ic_baseline_search_24);
//
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//                intent.putParcelableArrayListExtra("listNotes", (ArrayList<? extends Parcelable>) notes);
//                openNextActivity(intent);
//            }
//        });

        recyclerView = findViewById(R.id.mainRV);

        initRV(notes);

    }

    private void initRV(List<Note> newNotes) {

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        myAdapter = new NotesRecyclerViewAdapter(MainActivity.this, newNotes);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    private void deleteNote(int i) {
        notes.remove(i); // notes.remove(notes.size()-1);
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
        Toast.makeText(MainActivity.this,
                notes.get(position).getTitle() + notes.get(position).getDesc(),
                Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onResume() {
//        super.onResume(); // Toast.makeText(MainActivity.this, "OnResume Start", Toast.LENGTH_SHORT).show();
//
//        // IF returned from the next activity. Identifier with Extra
////        if (true) { updateNotes(); }
//
//        // Bundle extras = getIntent().getExtras();
////        if (extras.getBoolean("delete")){
////            Toast.makeText(MainActivity.this, "Value: " + extras.getInt("position"), Toast.LENGTH_SHORT).show();
////            //deleteNote(extras.getInt("position"));
////        }
//    }

    private void updateNotes() {
        temp = notes;
        myAdapter.notifyItemRangeChanged(0, myAdapter.getItemCount() );
        // notes.clear(); // notes nya diisi ulang yang lama dan add yg terbaru
    }
}