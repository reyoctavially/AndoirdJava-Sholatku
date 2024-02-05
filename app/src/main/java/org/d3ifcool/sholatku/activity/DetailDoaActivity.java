package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.sholatku.R;

public class DetailDoaActivity extends AppCompatActivity {

    private TextView judul;
    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_doa);
        judul = findViewById(R.id.tv_judul_doa);
        gambar = findViewById(R.id.iv_detail_doa);
        String doa = getIntent().getStringExtra("datadoa");
        if (doa.equalsIgnoreCase("sebelumtidur")){
            judul.setText("Doa Sebelum Tidur");
            gambar.setImageResource(R.drawable.img_sebelumtidur);
        } else if (doa.equalsIgnoreCase("banguntidur")) {
            judul.setText("Doa Bangun Tidur");
            gambar.setImageResource(R.drawable.img_banguntidur);
        } else if (doa.equalsIgnoreCase("menjelangsubuh")) {
            judul.setText("Doa Menjelang Subuh");
            gambar.setImageResource(R.drawable.img_menjelangsubuh);
        } else if (doa.equalsIgnoreCase("menyambutpagi")) {
            judul.setText("Doa Menyambut Pagi");
            gambar.setImageResource(R.drawable.img_menyambutpagi);
        } else if (doa.equalsIgnoreCase("menyambutsore")) {
            judul.setText("Doa Menyambut Sore");
            gambar.setImageResource(R.drawable.img_menyambutsore);
        } else if (doa.equalsIgnoreCase("mimpibaik")) {
            judul.setText("Doa Ketika Mendapat Mimpi Baik");
            gambar.setImageResource(R.drawable.img_mimpibaik);
        } else if (doa.equalsIgnoreCase("mimpiburuk")) {
            judul.setText("Doa Ketika Mendapat Mimpi Buruk");
            gambar.setImageResource(R.drawable.img_mimpiburuk);
        } else if (doa.equalsIgnoreCase("masukrumah")) {
            judul.setText("Doa Ketika Akan Masuk Rumah");
            gambar.setImageResource(R.drawable.img_masukrumah);
        } else if (doa.equalsIgnoreCase("keluarrumah")) {
            judul.setText("Doa Ketika Keluar Rumah");
            gambar.setImageResource(R.drawable.img_keluarrumah);
        } else if (doa.equalsIgnoreCase("sebelummakan")) {
            judul.setText("Doa Sebelum Makan");
            gambar.setImageResource(R.drawable.img_sebelummakan);
        } else if (doa.equalsIgnoreCase("sesudahmakan")) {
            judul.setText("Doa Sesudah Makan");
            gambar.setImageResource(R.drawable.img_sesudahmakan);
        } else if (doa.equalsIgnoreCase("bercermin")) {
            judul.setText("Doa Ketika Bercermin");
            gambar.setImageResource(R.drawable.img_bercermin);
        } else if (doa.equalsIgnoreCase("istinja")) {
            judul.setText("Doa Ketika Melakukan Istinja");
            gambar.setImageResource(R.drawable.img_istinja);
        }
    }
}
