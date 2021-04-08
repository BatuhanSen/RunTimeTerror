package com.example.howtosurvive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLokasyon extends RecyclerView.Adapter<AdapterLokasyon.CustomViewHolderLok> {

    private Context context;
    private List<LokasyonList> lokasyon;

    public AdapterLokasyon(Context context,List<LokasyonList> lokasyon){
        this.context=context;
        this.lokasyon=lokasyon;
    }

    @NonNull
    @Override
    public CustomViewHolderLok onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view=inflater.inflate(R.layout.lokasyon_layout,parent,false);

        return new CustomViewHolderLok(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderLok holder, int position) {

        holder.konumAd.setText(lokasyon.get(position).getKonum_ad());
        holder.il.setText(lokasyon.get(position).getIl());
        holder.ilce.setText(lokasyon.get(position).getIlce());

    }

    @Override
    public int getItemCount() {
        return lokasyon.size();
    }

    public static class CustomViewHolderLok extends RecyclerView.ViewHolder{

        TextView konumAd;
        TextView il;
        TextView ilce;


        public CustomViewHolderLok(@NonNull View itemView) {
            super(itemView);
            konumAd=itemView.findViewById(R.id.get_konumAd);
            il=itemView.findViewById(R.id.get_il);
            ilce=itemView.findViewById(R.id.get_ilce);
        }
    }

}
