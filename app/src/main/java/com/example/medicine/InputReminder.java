package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputReminder extends AppCompatActivity {

    EditText ednomor, ednama;
    DatabaseReference dbr;
    Button tombolsave;
    ModelReminder modelReminder;
//    Button dateTimeButton;
    Calendar calendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_reminder);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ednomor=findViewById(R.id.nomor_obat);
        ednama=findViewById(R.id.nama_obat);
        modelReminder=new ModelReminder();
        dbr= FirebaseDatabase.getInstance().getReference().child("DataReminder");
        tombolsave=findViewById(R.id.btnSave);
//        dateTimeButton = findViewById(R.id.dateTimeButton);

        // Mendapatkan tanggal dan waktu saat ini
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

//        // Menampilkan DateTimePickerDialog saat tombol tanggal dan waktu diklik
//        dateTimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Membuat DatePickerDialog
//                DatePickerDialog datePickerDialog = new DatePickerDialog(InputReminder.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Menampilkan TimePickerDialog setelah tanggal dipilih
//                        TimePickerDialog timePickerDialog = new TimePickerDialog(InputReminder.this, new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                // Mengupdate teks pada tombol dengan tanggal dan waktu yang dipilih
//                                String selectedDateTime = dayOfMonth + "/" + (month + 1) + "/" + year + " " + hourOfDay + ":" + minute;
//                                dateTimeButton.setText(selectedDateTime);
//                            }
//                        }, hourOfDay, minute, true);
//
//                        // Menampilkan TimePickerDialog
//                        timePickerDialog.show();
//                    }
//                }, year, month, dayOfMonth);
//
//                // Menampilkan DatePickerDialog
//                datePickerDialog.show();
//            }
//        });

        tombolsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelReminder.setNomor(Integer.parseInt(ednomor.getText().toString()));
                modelReminder.setNama(ednama.getText().toString());
                dbr.push().setValue(modelReminder);
            }
        });
    }
}