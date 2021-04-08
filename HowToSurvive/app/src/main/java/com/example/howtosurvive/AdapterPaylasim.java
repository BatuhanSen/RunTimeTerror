package com.example.howtosurvive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPaylasim extends RecyclerView.Adapter<AdapterPaylasim.CustomViewHolder> {

    private Context context;
    private List<PaylasimList> paylasim;

    public AdapterPaylasim(Context context,List<PaylasimList> paylasim){
        this.context=context;
        this.paylasim=paylasim;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view=inflater.inflate(R.layout.paylasim_layout,parent,false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.baslik.setText(paylasim.get(position).getBaslik());
        holder.icerik.setText(paylasim.get(position).getIcerik());
        holder.tarih.setText(paylasim.get(position).getTarih());
        holder.username.setText(paylasim.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return paylasim.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView baslik;
        TextView icerik;
        TextView tarih;
        TextView username;

            public CustomViewHolder(@NonNull View itemView) {

            super(itemView);
            baslik=itemView.findViewById(R.id.get_baslik);
            icerik=itemView.findViewById(R.id.get_icerik);
            tarih=itemView.findViewById(R.id.get_tarih);
            username=itemView.findViewById(R.id.get_kullaniciAd);

        }
    }

}
