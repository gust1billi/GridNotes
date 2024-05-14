package com.example.gridnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    Bundle extras;

    EditText editDesc;
    TextInputLayout layoutTitle; TextInputEditText editTitle;
    ImageView backBtn, deleteBtn;
    TextView toolbarTitle;
    FloatingActionButton updateBtn;

    LinearLayout lowerBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        toolbarTitle = findViewById(R.id.title_toolbar);
        updateBtn = findViewById(R.id.saveBtn); lowerBackBtn = findViewById(R.id.editNoteBackBtn);
        backBtn = findViewById(R.id.back_icon); deleteBtn = findViewById(R.id.search_icon);
        editDesc = findViewById(R.id.editDesc); editTitle = findViewById(R.id.editTitle);
        layoutTitle = findViewById(R.id.layoutTitle);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( Objects.requireNonNull( editTitle.getText( ) ).length() == 0 ){
                    layoutTitle.setErrorEnabled(true);
                    layoutTitle.setError("Judul Note harus ada isinya");
                } else layoutTitle.setErrorEnabled(false);
            }
        });

        deleteBtn.setBackgroundResource(R.drawable.ic_baseline_delete_outline_24);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);

                intent.putExtra("delete", true);
                intent.putExtra("position", extras.getInt("position"));
                intent.putExtra("newNote", extras.getBoolean("newNote"));
                setResult(RESULT_OK, intent);

                finish();
//                Toast.makeText(EditNoteActivity.this, "Position: " + extras.getInt("posistion"), Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        extras = getIntent().getExtras();
        toolbarTitle.setText(extras.getString("noteTitle"));
        editTitle.setText(extras.getString("noteTitle"));

        if (extras.getBoolean("filled")) {
            editDesc.setText(extras.getString("noteDesc"));
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            final Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);

            @Override
            public void onClick(View view) {
                // Toast.makeText(EditNoteActivity.this, "update Btn", Toast.LENGTH_SHORT).show();

                if (Objects.requireNonNull(editTitle.getText()).length() == 0){
                    Toast.makeText(EditNoteActivity.this, "Empty Title", Toast.LENGTH_SHORT).show();
                } else {
                    if (editDesc.getText().length() == 0){
                        editDesc.setText("");
                    }

                    intent.putExtra("title", editTitle.getText().toString() );
                    intent.putExtra("desc", editDesc.getText().toString() );

                    if (extras.getBoolean("newNote")){
                        intent.putExtra("save", true);
                    } else {
                        intent.putExtra("update", true);
                        intent.putExtra("position", extras.getInt("position"));
                    }

                    setResult(RESULT_OK, intent); finish();
                }
//                if (editDesc.getText().length() == 0){
//                    Toast.makeText(EditNoteActivity.this, "empty Desc", Toast.LENGTH_SHORT).show();
//                    editDesc.setText(""); // intent.putExtra("noteDesc", noteDesc);
//                }


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lowerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Toast.makeText(EditNoteActivity.this, noteDesc, Toast.LENGTH_SHORT).show();
    }

}