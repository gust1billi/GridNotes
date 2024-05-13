package com.example.gridnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gridnotes.rv.Note;
import com.example.gridnotes.rv.SearchRecyclerViewAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView; SearchRecyclerViewAdapter myAdapter; GridLayoutManager layoutManager;

    ImageView backBtn;
    TextInputEditText searchBar;

    List<Note> notes, temp;

//    public void openNextActivity(Intent intent){
//        nextActivityLauncher.launch(intent);
////        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        notes = extras.getParcelableArrayList("listNotes");
        temp = new ArrayList<Note>();

        recyclerView = findViewById(R.id.mainRV);
        backBtn = findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
//                searchBar.getText(); Toast.makeText(SearchActivity.this, searchBar.getText(), Toast.LENGTH_SHORT).show();
                temp.clear();
                for (Note note : notes) {
                    if (note.getTitle().contains(searchBar.getText() ) ){
                        temp.add(new Note(note.getTitle(), note.getDesc()));
                    }
                } // myAdapter.notifyItemChanged(temp.size());
                initRV(temp); myAdapter.notifyItemChanged(temp.size());
                // Toast.makeText(SearchActivity.this, "Sample Size: " + temp.size(), Toast.LENGTH_SHORT).show();
            }
        });

        initRV(notes);

    }

    private void initRV(List<Note> newNote){
        layoutManager = new GridLayoutManager(SearchActivity.this, 2);
        myAdapter = new SearchRecyclerViewAdapter(SearchActivity.this, newNote);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
    }
}