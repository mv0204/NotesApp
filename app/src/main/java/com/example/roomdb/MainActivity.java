package com.example.roomdb;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.roomdb.adapter.RecAdapter;
import com.example.roomdb.databinding.ActivityMainBinding;
import com.example.roomdb.viewmodel.NotesViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;
    RecAdapter adapter;
    List<Notes> notesList;

    Notes deletedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RecAdapter(notesList);
        deletedNote = null;
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.recyclerView.setAdapter(adapter);

        notesViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, notes -> {
            if (notes != null) {
                notesList = notes;
                adapter.updateList(notesList);
            }
            if (notes == null) {
                binding.recyclerView.setVisibility(View.GONE);
            }
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),DataInsertActivity.class);
            intent.putExtra("type","addNote");
            startActivity(intent);

        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                        .addSwipeLeftLabel("delete")
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green))
                        .addSwipeRightActionIcon(R.drawable.ic_edit_24)
                        .setSwipeLeftLabelColor(R.color.white)
                        .addSwipeRightLabel("update")
                        .setSwipeRightLabelColor(ContextCompat.getColor(MainActivity.this, R.color.white))

                        .setSwipeLeftLabelColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case ItemTouchHelper.LEFT: {
                        int pos = viewHolder.getAdapterPosition();
                        deletedNote = notesList.get(pos);
                        notesViewModel.delete(deletedNote);
                        notesList.remove(pos);
                        adapter.updateList(notesList);
                        Snackbar.make(binding.recyclerView, deletedNote.getTitle() + "deleted", Snackbar.LENGTH_LONG)
                                .setAction("Undo", view -> {
                                    notesList.add(deletedNote);
                                    notesViewModel.insert(deletedNote);
                                    adapter.updateList(notesList);
                                })
                                .show();
                        break;
                    }
                    case ItemTouchHelper.RIGHT: {
                        Intent intent=new Intent(MainActivity.this,DataInsertActivity.class);
                        intent.putExtra("type","update");
                        intent.putExtra("noteTitle",notesList.get(viewHolder.getAdapterPosition()).getTitle());
                        intent.putExtra("notes",notesList.get(viewHolder.getAdapterPosition()).getNotes());
                        intent.putExtra("id",notesList.get(viewHolder.getAdapterPosition()).getUid());

                        startActivity(intent);
//
                        break;
                    }
                }


            }
        }).attachToRecyclerView(binding.recyclerView);


    }

    @Override
    protected void onRestart() {
        adapter.updateList(notesList);
        super.onRestart();
    }
}
