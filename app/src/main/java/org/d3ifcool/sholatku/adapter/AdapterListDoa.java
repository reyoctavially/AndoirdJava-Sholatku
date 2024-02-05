package org.d3ifcool.sholatku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.d3ifcool.sholatku.data.Doa;
import org.d3ifcool.sholatku.interfaces.OnClickListener;
import org.d3ifcool.sholatku.R;

import java.util.ArrayList;

public class AdapterListDoa extends RecyclerView.Adapter<AdapterListDoa.DoaHolder> {

    private ArrayList<Doa> daftar;
    private OnClickListener listener;

    public AdapterListDoa(ArrayList<Doa> daftar) {
        this.daftar = daftar;
    }
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_viewdoa, viewGroup, false);
        AdapterListDoa.DoaHolder sh = new AdapterListDoa.DoaHolder(view);
        return sh;
    }

    @Override
    public void onBindViewHolder(@NonNull DoaHolder doaHolder, int i) {
        Doa doa = daftar.get(i);
        doaHolder.judul.setText(doa.getJudul());
        doaHolder.subjudul.setText(doa.getSubjudul());
        switch (i){
            case 0:
                doaHolder.images.setBackgroundResource(R.drawable.ic_doa);
                break;
            case 1:
                doaHolder.images.setBackgroundResource(R.drawable.ic_doa);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    public class DoaHolder extends RecyclerView.ViewHolder {
        final TextView judul;
        final TextView subjudul;
        final ImageView images;
        public DoaHolder(@NonNull View itemView) {
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
