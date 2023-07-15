package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDataObat extends AppCompatActivity {
    EditText enomor, enama, ejenis, estok;
    Button updatebutton;
    DatabaseReference dbr;
    ModelObat modelObat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_obat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        enomor= findViewById(R.id.nomorobat);
        enama= findViewById(R.id.namaobat);
        ejenis= findViewById(R.id.jenisobat);
        estok= findViewById(R.id.stokobat);

        dbr= FirebaseDatabase.getInstance().getReference();
        modelObat=new ModelObat();
        Bundle bundle=getIntent().getExtras();
        enomor.setText(bundle.getString("nomor"));
        enama.setText(bundle.getString("nama"));
        ejenis.setText(bundle.getString("jenis"));
        estok.setText(bundle.getString("stok"));

        updatebutton= findViewById(R.id.btnUpdate);

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelObat.setNomor(Integer.parseInt(enomor.getText().toString()));
                modelObat.setNama(enama.getText().toString());
                modelObat.setJenis(ejenis.getText().toString());
                modelObat.setStok(Integer.parseInt(estok.getText().toString()));

                String kuncine=(bundle.getString("kunci"));
                dbr.child("DataObat").child(kuncine)
                        .setValue(modelObat)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateDataObat.this, "Update Sukses !", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(UpdateDataObat.this,ManajemenObat.class);
                startActivity(intent);
            }
        });
    }

}