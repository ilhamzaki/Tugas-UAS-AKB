package com.ilhamzaki.note.view.note;

// 10119006 - Ilham zaki - IF1

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilhamzaki.note.R;
import com.ilhamzaki.note.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.DiaryViewHolder>{
    private Activity activity;
    private List<NoteModel> diaryList = new ArrayList<NoteModel>();

    public NoteAdapter(Activity activity, List<NoteModel> diaryList) {
        this.activity = activity;
        this.diaryList = diaryList;
    }


    @NonNull
    @Override
    public NoteAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.card_note, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.DiaryViewHolder holder, int position) {
        holder.title.setText(String.valueOf(diaryList.get(position).getTitle()));
        holder.category.setText(String.valueOf(diaryList.get(position).getCategory()));
        holder.note.setText(String.valueOf(diaryList.get(position).getNote()));
        holder.date.setText(String.valueOf(diaryList.get(position).getDate()).substring(0,10));

        holder.diaryItem.setOnClickListener(view -> {
            Intent intent = new Intent(activity, UpdateNoteActivity.class);
            intent.putExtra("id", String.valueOf(diaryList.get(position).getId()));
            intent.putExtra("title", String.valueOf(diaryList.get(position).getTitle()));
            intent.putExtra("category", String.valueOf(diaryList.get(position).getCategory()));
            intent.putExtra("note", String.valueOf(diaryList.get(position).getNote()));
            intent.putExtra("date", String.valueOf(diaryList.get(position).getDate()));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        if(diaryList != null) return diaryList.size();
        return 0;
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder{
        TextView title, category, date, note;
        LinearLayout diaryItem;

        DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            category = itemView.findViewById(R.id.text_category);
            date = itemView.findViewById(R.id.text_date);
            note = itemView.findViewById(R.id.text_note);
            diaryItem = itemView.findViewById(R.id.diaryItem);
        }
    }
}