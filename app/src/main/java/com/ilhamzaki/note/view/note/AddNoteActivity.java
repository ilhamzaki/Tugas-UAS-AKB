package com.ilhamzaki.note.view.note;

// 10119006 - Ilham zaki - IF1

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ilhamzaki.note.MainActivity;
import com.ilhamzaki.note.R;
import com.ilhamzaki.note.helper.DatabaseHelper;
import com.ilhamzaki.note.models.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    EditText title_input,category_input, note_input;
    String date_input;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        title_input = findViewById(R.id.title_input);
        category_input = findViewById(R.id.category_input);
        note_input = findViewById(R.id.note_input);
        date_input = getDateNow();

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            DatabaseHelper db = new DatabaseHelper(AddNoteActivity.this);
            NoteModel diary = new NoteModel("id",
                    title_input.getText().toString().trim(),
                    category_input.getText().toString().trim(),
                    note_input.getText().toString().trim(),
                    date_input.trim());

            db.addDiary(diary);

            Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    public String getDateNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
}