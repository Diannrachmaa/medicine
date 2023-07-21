package com.example.medicine;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateDataReminder extends AppCompatActivity {
    EditText nomor, nama;
    Button updatebutton;
    Button dateTime;
    DatabaseReference dbr;
    ModelReminder modelReminder;
    Calendar calendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_reminder);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nomor= findViewById(R.id.nomorobat);
        nama= findViewById(R.id.namaobat);
        dateTime=findViewById(R.id.dateTimeButton);

        dbr= FirebaseDatabase.getInstance().getReference();
        modelReminder=new ModelReminder();
        Bundle bundle=getIntent().getExtras();
        nomor.setText(bundle.getString("nomor"));
        nama.setText(bundle.getString("nama"));
        dateTime.setText(bundle.getString("datetime"));

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Menampilkan DateTimePickerDialog saat tombol tanggal dan waktu diklik
        dateTime= findViewById(R.id.dateTimeButton);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateDataReminder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Menampilkan TimePickerDialog setelah tanggal dipilih
                        TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateDataReminder.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Mengupdate teks pada tombol dengan tanggal dan waktu yang dipilih
                                String selectedDateTime = dayOfMonth + "/" + (month + 1) + "/" + year + " " + hourOfDay + ":" + minute;
                                dateTime.setText(selectedDateTime);
                            }
                        }, hourOfDay, minute, true);

                        // Menampilkan TimePickerDialog
                        timePickerDialog.show();
                    }
                }, year, month, dayOfMonth);

                // Menampilkan DatePickerDialog
                datePickerDialog.show();
            }
        });

        updatebutton= findViewById(R.id.btnUpdate);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelReminder.setNomor(Integer.parseInt(nomor.getText().toString()));
                modelReminder.setNama(nama.getText().toString());
                modelReminder.setDatetime(""+year+"-"+month+"-"+dayOfMonth+" "+hourOfDay+":"+minute);


                String kunci=(bundle.getString("key"));
                dbr.child("DataReminder").child(kunci)
                        .setValue(modelReminder)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateDataReminder.this, "Update Sukses !", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(UpdateDataReminder.this,Reminder.class);
                startActivity(intent);
            }
        });
    }

}