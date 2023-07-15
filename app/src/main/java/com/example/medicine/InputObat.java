package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputObat extends AppCompatActivity {
    EditText ednomor, ednama, edjenis, edstok;
    DatabaseReference dbr;
    Button tombolsave;
    ModelObat modelObat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_obat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ednomor=findViewById(R.id.nomor_obat);
        ednama=findViewById(R.id.nama_obat);
        edjenis=findViewById(R.id.jenis_obat);
        edstok=findViewById(R.id.stok_obat);
        modelObat=new ModelObat();
        dbr= FirebaseDatabase.getInstance().getReference().child("DataObat");
        tombolsave=findViewById(R.id.btnSave);
        tombolsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelObat.setNomor(Integer.parseInt(ednomor.getText().toString()));
                modelObat.setNama(ednama.getText().toString());
                modelObat.setJenis(edjenis.getText().toString());
                modelObat.setStok(Integer.parseInt(edstok.getText().toString()));
                dbr.push().setValue(modelObat);
                Intent intent = new Intent(InputObat.this,ManajemenObat.class);
                startActivity(intent);
            }
        });
    }
}