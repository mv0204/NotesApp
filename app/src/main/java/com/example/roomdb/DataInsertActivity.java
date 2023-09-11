package com.example.roomdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.roomdb.databinding.ActivityDataInsertBinding;
import com.example.roomdb.viewmodel.NotesViewModel;

public class DataInsertActivity extends AppCompatActivity {
    ActivityDataInsertBinding binding;
    NotesViewModel notesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable( new ColorDrawable( getResources().getColor( R.color.yellow)));

        notesViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NotesViewModel.class);

        String type = getIntent().getStringExtra("type");


        if (type.equals("update")) {
            getSupportActionBar().setTitle("Update Note");
            String title =getIntent().getStringExtra("noteTitle");
            String desc=getIntent().getStringExtra("notes");
            int id=getIntent().getIntExtra("id",0);

            binding.editTextTitle.setText(title);
            binding.editTextNotes.setText(desc);
            binding.addButton.setText("update note");
            binding.addButton.setOnClickListener(view -> {
                Notes notes=new Notes(title,desc);
                notes.setUid(id);
                notesViewModel.update(notes);
                Toast.makeText(this, "note updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            });
        }
        else {
            getSupportActionBar().setTitle("Add Note");
            binding.addButton.setOnClickListener(view -> {

                String title = binding.editTextTitle.getText().toString();
                String desc = binding.editTextNotes.getText().toString();
                notesViewModel.insert(new Notes(title, desc));
                Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            });
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
    }
}