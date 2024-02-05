package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.sholatku.R;

public class DetailSuratActivity extends AppCompatActivity {

    private TextView judul;
    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);
        judul = findViewById(R.id.tv_judul_surat_pendek);
        gambar = findViewById(R.id.iv_detail_surat_pendek);
        String surat = getIntent().getStringExtra("datasurat");
        if (surat.equalsIgnoreCase("Naas")){
            judul.setText("Surat An Naas");
            gambar.setImageResource(R.drawable.img_surat_naas);
        } else if (surat.equalsIgnoreCase("Falaq")) {
            judul.setText("Surat Al Falaq");
            gambar.setImageResource(R.drawable.img_surat_falaq);
        } else if (surat.equalsIgnoreCase("Ikhlash")) {
            judul.setText("Surat Al Ikhlash");
            gambar.setImageResource(R.drawable.img_surat_ikhlash);
        } else if (surat.equalsIgnoreCase("Lahab")) {
            judul.setText("Surat Al Lahab");
            gambar.setImageResource(R.drawable.img_surat_lahab);
        } else if (surat.equalsIgnoreCase("Nasr")) {
            judul.setText("Surat An Nasr");
            gambar.setImageResource(R.drawable.img_surat_nashr);
        } else if (surat.equalsIgnoreCase("Kaafiruun")) {
            judul.setText("Surat Al Kaafiruun");
            gambar.setImageResource(R.drawable.img_surat_kaafiruun);
        } else if (surat.equalsIgnoreCase("Kautsar")) {
            judul.setText("Surat Al Kautsar");
            gambar.setImageResource(R.drawable.img_surat_kautsar);
        } else if (surat.equalsIgnoreCase("Maauun")) {
            judul.setText("Surat Al Maa'uun");
            gambar.setImageResource(R.drawable.img_surat_maauun);
        } else if (surat.equalsIgnoreCase("Quraisy")) {
            judul.setText("Surat Al Quraisy");
            gambar.setImageResource(R.drawable.img_surat_quraisy);
        } else if (surat.equalsIgnoreCase("Fiil")) {
            judul.setText("Surat Al Fiil");
            gambar.setImageResource(R.drawable.img_surat_fiil);
        } 
    }
}
