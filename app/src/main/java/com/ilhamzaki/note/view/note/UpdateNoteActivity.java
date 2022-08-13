package com.ilhamzaki.note.view.note;

// 10119006 - Ilham zaki - IF1

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ilhamzaki.note.MainActivity;
import com.ilhamzaki.note.R;
import com.ilhamzaki.note.helper.DatabaseHelper;
import com.ilhamzaki.note.models.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {
    EditText title_input,category_input, note_input;
    Button update_button, delete_button;
    String date_input;
    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        title_input = findViewById(R.id.title_input_update);
        category_input = findViewById(R.id.category_input_update);
        note_input = findViewById(R.id.note_input_update);
        date_input = getDateNow();
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(noteModel.getTitle());
        }

        update_button.setOnClickListener(view -> {
            DatabaseHelper db =new DatabaseHelper(UpdateNoteActivity.this);
            noteModel = new NoteModel(
                    noteModel.getId(),
                    title_input.getText().toString().trim(),
                    category_input.getText().toString().trim(),
                    note_input.getText().toString().trim(),
                    date_input.trim()
            );

            db.updateDiary(noteModel);

            Intent intent = new Intent(UpdateNoteActivity.this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    public String getDateNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("category")
                && getIntent().hasExtra("note")
                && getIntent().hasExtra("date")) {

            noteModel = new NoteModel(
                    getIntent().getStringExtra("id"),
                    getIntent().getStringExtra("title"),
                    getIntent().getStringExtra("category"),
                    getIntent().getStringExtra("note"),
                    getIntent().getStringExtra("date")
            );

            title_input.setText(noteModel.getTitle());
            category_input.setText(noteModel.getCategory());
            note_input.setText(noteModel.getNote());

            Log.d("stev", noteModel.getTitle() + " " + noteModel.getCategory() + " " + noteModel.getNote());
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + noteModel.getTitle() + " ?");
        builder.setMessage("Are you sure you want to delete " + noteModel.getTitle() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(UpdateNoteActivity.this);
                db.deleteOne(String.valueOf(noteModel.getId()));
                finish();

                Intent intent = new Intent(UpdateNoteActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}