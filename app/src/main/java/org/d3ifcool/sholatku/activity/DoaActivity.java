package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.adapter.AdapterListDoa;
import org.d3ifcool.sholatku.data.Doa;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

public class DoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
        RecyclerView list = findViewById(R.id.list_doa);
        LinearLayoutManager in= new LinearLayoutManager(DoaActivity.this);

        final ArrayList<Doa> doas = new ArrayList<>();
        doas.add(new Doa("Doa Sebelum Tidur", "Dibaca sebelum tidur"));
        doas.add(new Doa("Doa Bangun Tidur", "Dibaca setelah bangun tidur"));
        doas.add(new Doa("Doa Menjelang Subuh", "Dibaca ketika menjelang subuh"));
        doas.add(new Doa("Doa Menyambut Pagi", "Dibaca untuk menyambut pagi"));
        doas.add(new Doa("Doa Menyambut Sore", "Dibaca untuk menyambut sore"));
        doas.add(new Doa("Doa Mimpi Baik", "Dibaca ketika mendapat mimpi baik"));
        doas.add(new Doa("Doa Mimpi Buruk", "Dibaca ketika mendapat mimpi buruk"));
        doas.add(new Doa("Doa Masuk Rumah", "Dibaca ketika akan masuk rumah"));
        doas.add(new Doa("Doa Keluar Rumah", "Dibaca ketika akan keluar rumah"));
        doas.add(new Doa("Doa Sebelum Makan", "Dibaca sebelum makan"));
        doas.add(new Doa("Doa Sesudah Makan", "Dibaca sesudah makan"));
        doas.add(new Doa("Doa Bercermin", "Dibaca pada saat bercermin"));
        doas.add(new Doa("Doa Istinja", "Dibaca pada saat istinja"));

        AdapterListDoa adapter = new AdapterListDoa(doas);

        adapter.setListener(new OnClickListener() {
            @Override
            public void onclick(int position) {
                doas.get(position);
                switch (position){
                    case 0:
                        Intent intent = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent.putExtra("datadoa", "sebelumtidur");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent2.putExtra("datadoa", "banguntidur");
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent3.putExtra("datadoa", "menjelangsubuh");
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent4.putExtra("datadoa", "menyambutpagi");
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent5.putExtra("datadoa", "menyambutsore");
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent6.putExtra("datadoa", "mimpibaik");
                        startActivity(intent6);
                        break;
                    case 6:
                        Intent intent7 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent7.putExtra("datadoa", "mimpiburuk");
                        startActivity(intent7);
                        break;
                    case 7:
                        Intent intent8 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent8.putExtra("datadoa", "masukrumah");
                        startActivity(intent8);
                        break;
                    case 8:
                        Intent intent9 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent9.putExtra("datadoa", "keluarrumah");
                        startActivity(intent9);
                        break;
                    case 9:
                        Intent intent10 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent10.putExtra("datadoa", "sebelummakan");
                        startActivity(intent10);
                        break;
                    case 10:
                        Intent intent11 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent11.putExtra("datadoa", "sesudahmakan");
                        startActivity(intent11);
                        break;
                    case 11:
                        Intent intent12 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent12.putExtra("datadoa", "bercermin");
                        startActivity(intent12);
                        break;
                    case 12:
                        Intent intent13 = new Intent(DoaActivity.this, DetailDoaActivity.class);
                        intent13.putExtra("datadoa", "istinja");
                        startActivity(intent13);
                        break;
                }
            }
        });
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setLayoutManager(in);
        list.setAdapter(adapter);
    }
}
