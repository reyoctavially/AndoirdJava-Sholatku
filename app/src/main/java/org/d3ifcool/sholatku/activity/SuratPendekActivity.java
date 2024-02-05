package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.adapter.AdapterListSurat;
import org.d3ifcool.sholatku.data.Surat;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

import java.util.ArrayList;

public class SuratPendekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat_pendek);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
        RecyclerView list = findViewById(R.id.list_surat_pendek);
        LinearLayoutManager in= new LinearLayoutManager(SuratPendekActivity.this);

        final ArrayList<Surat> surats = new ArrayList<>();
        surats.add(new Surat("Surat An Naas",       "surah penutup (ke-114) dalam Al-Qur'an"));
        surats.add(new Surat("Surat Al Falaq",      "surah ke-113 dalam Al-Qur'an"));
        surats.add(new Surat("Surat Al Ikhlash",    "surah ke-112 dalam al-Qur'an"));
        surats.add(new Surat("Surat Al Lahab",      "surat ke-111 dalam Al-Qur'an"));
        surats.add(new Surat("Surat An Nasr",       "surah ke-110 dalam al-Qur'an"));
        surats.add(new Surat("Surat Al Kaafiruun",  "surah ke-109 dalam al-Qur'an"));
        surats.add(new Surat("Surat Al Kautsar",    "surah ke-108 dalam al-Qur'an"));
        surats.add(new Surat("Surat Al Maa'uun",    "surah ke-107 dalam Al-Qur'an"));
        surats.add(new Surat("Surat Al Quraisy",    "surah ke-106 dalam al-Qur'an"));
        surats.add(new Surat("Surat Al Fiil",       "surah ke-105 dalam al-Qur'an"));

        AdapterListSurat adapter = new AdapterListSurat(surats);
        
        adapter.setListenerSurat(new OnClickListener() {
            @Override
            public void onclick(int position) {
                surats.get(position);
                switch (position){
                    case 0:
                        Intent intent = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent.putExtra("datasurat", "Naas");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent2.putExtra("datasurat", "Falaq");
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent3.putExtra("datasurat", "Ikhlash");
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent4.putExtra("datasurat", "Lahab");
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent5.putExtra("datasurat", "Nasr");
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent6.putExtra("datasurat", "Kaafiruun");
                        startActivity(intent6);
                        break;
                    case 6:
                        Intent intent7 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent7.putExtra("datasurat", "Kautsar");
                        startActivity(intent7);
                        break;
                    case 7:
                        Intent intent8 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent8.putExtra("datasurat", "Maauun");
                        startActivity(intent8);
                        break;
                    case 8:
                        Intent intent9 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent9.putExtra("datasurat", "Quraisy");
                        startActivity(intent9);
                        break;
                    case 9:
                        Intent intent10 = new Intent(SuratPendekActivity.this, DetailSuratActivity.class);
                        intent10.putExtra("datasurat", "Fiil");
                        startActivity(intent10);
                        break;
                }
            }
        });
        
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setLayoutManager(in);
        list.setAdapter(adapter);
    }
}
