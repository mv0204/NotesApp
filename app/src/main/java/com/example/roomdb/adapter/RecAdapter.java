package com.example.roomdb.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.Notes;
import com.example.roomdb.R;
import com.example.roomdb.databinding.EachRowBinding;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewholder> {
    List<Notes> notesList;
    Notes currentNote;
    public RecAdapter(List<Notes> notesList) {
        this.notesList = notesList;
    }


    @NonNull
    @Override
    public RecViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row,parent,false);
        return new RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewholder holder, int position) {
        Notes note= notesList.get(position);
        holder.binding.textView.setText(note.getTitle());
        holder.binding.textView2.setText(note.getNotes());
    }

    public Notes getNote(int position){
        return notesList.get(position);
    }
    @Override
    public int getItemCount() {
        if(notesList!=null)
            return notesList.size();
        else
            return 0;
    }

    public void updateList(List<Notes> newNotesList) {
    this.notesList =newNotesList;
    notifyDataSetChanged();
    }

    public class RecViewholder extends RecyclerView.ViewHolder {
        EachRowBinding binding;

        public RecViewholder(@NonNull View itemView) {
            super(itemView);
            binding=EachRowBinding.bind(itemView);

        }
    }
}