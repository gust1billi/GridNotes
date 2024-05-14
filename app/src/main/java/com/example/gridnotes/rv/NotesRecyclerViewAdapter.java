package com.example.gridnotes.rv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gridnotes.EditNoteActivity;
import com.example.gridnotes.MainActivity;
import com.example.gridnotes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerViewAdapter
        extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesViewHolder> {

    List<Note> notes;

    Context context;

//    public NotesRecyclerViewAdapter(Context context){
//        this.context = context;
//    }

    public NotesRecyclerViewAdapter(Context context, List<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_note_list, parent, false);
        // Log.e("onCreate", "TEST");
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position); Integer post = position;

        holder.title.setText(note.getTitle() ); holder.desc.setText(note.getDesc() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent =
                        new Intent(holder.itemView.getContext(), EditNoteActivity.class);

                intent.putExtra("filled", true);
                intent.putExtra("noteTitle", note.getTitle() );
                intent.putExtra("position", post);

                if (note.getDesc() == null){
                    intent.putExtra("noteDesc", "" );
                } else intent.putExtra("noteDesc", note.getDesc() );
//                    Toast.makeText(holder.itemView.getContext(), note.getTitle(), Toast.LENGTH_SHORT).show();

                // holder.itemView.getContext().startActivity(intent);
                ((MainActivity)context).openNextActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            desc = itemView.findViewById(R.id.noteDesc);
        }

    }

    public void filterSearch(List<Note> filteredList){
        notes = filteredList; notifyDataSetChanged();
    }

    public void saveNote(){
        notifyItemInserted( notes.size()-1 );
    }
}
