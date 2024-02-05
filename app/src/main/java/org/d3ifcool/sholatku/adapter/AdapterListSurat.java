package org.d3ifcool.sholatku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.data.Surat;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

import java.util.ArrayList;

public class AdapterListSurat extends RecyclerView.Adapter<AdapterListSurat.SuratHolder> {

    private ArrayList<Surat> daftar;
    private OnClickListener listener;

    public AdapterListSurat(ArrayList<Surat> daftar) {
        this.daftar = daftar;
    }

    public void setListenerSurat(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterListSurat.SuratHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_viewsurat, viewGroup, false);
        AdapterListSurat.SuratHolder sh = new AdapterListSurat.SuratHolder(view);
        return sh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListSurat.SuratHolder holder, int i) {
        Surat surat = daftar.get(i);
        holder.judul.setText(surat.getJudul());
        holder.subjudul.setText(surat.getSubjudul());
        switch (i){
            case 0:
                holder.images.setBackgroundResource(R.drawable.ic_quran);
                break;
            case 1:
                holder.images.setBackgroundResource(R.drawable.ic_quran);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    public class SuratHolder extends RecyclerView.ViewHolder {
        final TextView judul;
        final TextView subjudul;
        final ImageView images;
        public SuratHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judul);
            subjudul = itemView.findViewById(R.id.tv_subjudul);
            images = itemView.findViewById(R.id.iv_detailsurat);
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
