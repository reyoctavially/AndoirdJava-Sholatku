package org.d3ifcool.sholatku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.d3ifcool.sholatku.interfaces.OnClickListener;
import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.data.Sholat;

public class AdapterListSholat extends RecyclerView.Adapter<AdapterListSholat.SholatHolder>{
    private ArrayList<Sholat> daftar;
    private OnClickListener listener;

    public AdapterListSholat(ArrayList<Sholat> daftar) {
        this.daftar = daftar;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public SholatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_viewsholat, viewGroup, false);
        SholatHolder sh = new SholatHolder(view);
        return sh;
    }

    @Override
    public void onBindViewHolder(@NonNull SholatHolder sholatHolder, int i) {
        Sholat sholat = daftar.get(i);
        sholatHolder.judul.setText(sholat.getJudul());
        sholatHolder.subjudul.setText(sholat.getSubjudul());
        switch (i){
            case 0:
                sholatHolder.images.setBackgroundResource(R.drawable.ic_jadwal);
                break;
            case 1:
                sholatHolder.images.setBackgroundResource(R.drawable.ic_kompas);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    public class SholatHolder extends RecyclerView.ViewHolder {
        final TextView judul;
        final TextView subjudul;
        final ImageView images;

        public SholatHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judul);
            subjudul = itemView.findViewById(R.id.tv_subjudul);
            images = itemView.findViewById(R.id.iv_detailsholat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onclick(position);
                        }
                    }
                }
            });
        }
    }
}
