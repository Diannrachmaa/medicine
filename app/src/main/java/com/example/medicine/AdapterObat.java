package com.example.medicine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

public class AdapterObat extends RecyclerView.Adapter<AdapterObat.myViewHolder> {
    Activity activity;

    public AdapterObat(Activity activity, ArrayList<ModelObat> modelObatArrayList) {
        this.activity = activity;
        this.modelObatArrayList = modelObatArrayList;
    }
    ArrayList<ModelObat> modelObatArrayList;
    DatabaseReference dbr;
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(activity);
        View view= inflater.inflate(R.layout.format_tampilan_dataobat,parent,false);
        return new AdapterObat.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nomor.setText(String.valueOf(modelObatArrayList.get(position).getNomor()));
        holder.nama.setText(modelObatArrayList.get(position).getNama());
        holder.jenis.setText(modelObatArrayList.get(position).getJenis());
        holder.stok.setText(String.valueOf(modelObatArrayList.get(position).getStok()));
        holder.cardobat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent= new Intent(v.getContext(),UpdateDataObat.class);
                intent.putExtra("kunci",modelObatArrayList.get(position).getKey());
                intent.putExtra("nomor",String.valueOf(modelObatArrayList.get(position).getNomor()));
                intent.putExtra("nama",modelObatArrayList.get(position).getNama());
                intent.putExtra("jenis",modelObatArrayList.get(position).getJenis());
                intent.putExtra("stok",String.valueOf(modelObatArrayList.get(position).getStok()));
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
                        dbr.child("DataObat").child(modelObatArrayList.get(position).getKey())
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
                }).setMessage("Apakah Anda yakin data obat"+" "+modelObatArrayList.get(position).getNama()+" "+"akan dihapus?");
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelObatArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nomor, nama, jenis, stok;
        ImageView hapus;
        CardView cardobat;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nomor= itemView.findViewById(R.id.nomorobat);
            nama= itemView.findViewById(R.id.namaobat);
            jenis= itemView.findViewById(R.id.jenisobat);
            stok= itemView.findViewById(R.id.stokobat);
            hapus= itemView.findViewById(R.id.hapus);
            cardobat=itemView.findViewById(R.id.cardview_obat);
        }

    }
}
