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

public class ManajemenObat extends AppCompatActivity {
    RecyclerView recyclerViewObat;
    DatabaseReference dbr;
    ArrayList<ModelObat> modelObatArrayList=new ArrayList<>();
    ModelObat modelObat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_obat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        recyclerViewObat=findViewById(R.id.rv_obat);
        dbr= FirebaseDatabase.getInstance().getReference();
        tampil_obat();
        recyclerViewObat.setLayoutManager(new LinearLayoutManager(this));
    }

    private void tampil_obat() {
        dbr.child("DataObat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ModelObat modelObat=dataSnapshot.getValue(ModelObat.class);
                    modelObat.setKey(dataSnapshot.getKey());
                    modelObatArrayList.add(modelObat);
                }
                AdapterObat adapterObat=new AdapterObat(ManajemenObat.this,modelObatArrayList);
                recyclerViewObat.setAdapter(adapterObat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tambah (View view) {
        Intent intent = new Intent(ManajemenObat.this,InputObat.class);
        startActivity(intent);
    }
}