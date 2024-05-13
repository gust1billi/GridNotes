package com.example.gridnotes.rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gridnotes.EditNoteActivity;
import com.example.gridnotes.R;

import java.util.List;

public class SearchRecyclerViewAdapter
        extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {

    private final List<Note> notes;
    Context context;

    public SearchRecyclerViewAdapter (Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_note_list, parent, false);
        return new SearchRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {
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
//                ((SearchActivity)context).openNextActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class SearchRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;

        public SearchRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            desc = itemView.findViewById(R.id.noteDesc);
        }
    }
}
