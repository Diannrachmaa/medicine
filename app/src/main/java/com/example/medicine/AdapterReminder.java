package com.example.medicine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.myViewHolder>{

    Activity activity;

    public AdapterReminder(Activity activity, ArrayList<ModelReminder> modelReminderArrayList) {
        this.activity = activity;
        this.modelReminderArrayList = modelReminderArrayList;
    }
    ArrayList<ModelReminder> modelReminderArrayList;
    DatabaseReference dbr;

    @NonNull
    @Override
    public AdapterReminder.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(activity);
        View view= inflater.inflate(R.layout.format_tampilan_reminder,parent,false);
        return new AdapterReminder.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nomor.setText("Nomor Kotak Obat: "+String.valueOf(modelReminderArrayList.get(position).getNomor()));
        holder.nama.setText("Nama Obat: "+modelReminderArrayList.get(position).getNama());
        holder.datetime.setText("Waktu Reminder: "+modelReminderArrayList.get(position).getDatetime());
        holder.cardreminder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent= new Intent(v.getContext(),UpdateDataReminder.class);
                intent.putExtra("key",modelReminderArrayList.get(position).getKey());
                intent.putExtra("nomor",String.valueOf(modelReminderArrayList.get(position).getNomor()));
                intent.putExtra("nama",modelReminderArrayList.get(position).getNama());
                intent.putExtra("datetime",modelReminderArrayList.get(position).getDatetime());
                v.getContext().startActivity(intent);
                return false;
            }
        });
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbr= FirebaseDatabase.getInstance().getReference();
                        dbr.child("DataReminder").child(modelReminderArrayList.get(position).getKey())
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(activity, "Hapus berhasil !", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage("Apakah Anda yakin data reminder obat"+" "+modelReminderArrayList.get(position).getNama()+" "+"akan dihapus?");
                builder.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelReminderArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nomor, nama, datetime;
        CardView cardreminder;
        ImageView hapus;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nomor= itemView.findViewById(R.id.nomorobat);
            nama= itemView.findViewById(R.id.namaobat);
            datetime= itemView.findViewById(R.id.datetime);
            hapus= itemView.findViewById(R.id.hapus);
            cardreminder=itemView.findViewById(R.id.cardview_reminder);
        }

    }
}
