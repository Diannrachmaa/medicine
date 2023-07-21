package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reminder extends AppCompatActivity {

    RecyclerView recyclerViewReminder;
    DatabaseReference dbr;
    ArrayList<ModelReminder> modelReminderArrayList=new ArrayList<>();
    ModelReminder modelReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        recyclerViewReminder=findViewById(R.id.rv_pengingat);
        dbr= FirebaseDatabase.getInstance().getReference();
        tampil_reminder();
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
    }

    private void tampil_reminder() {
        dbr.child("DataReminder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ModelReminder modelReminder=dataSnapshot.getValue(ModelReminder.class);
                    modelReminder.setKey(dataSnapshot.getKey());
                    modelReminderArrayList.add(modelReminder);
                }
                AdapterReminder adapterReminder=new AdapterReminder(Reminder.this,modelReminderArrayList);
                recyclerViewReminder.setAdapter(adapterReminder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void tambah (View view) {
        Intent intent = new Intent(Reminder.this,InputReminder.class);
        startActivity(intent);
    }
}