package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.adapter.AdapterListSholat;
import org.d3ifcool.sholatku.data.Sholat;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

public class SholatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sholat);
        ActionBar menu = getSupportActionBar();
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
        RecyclerView list = findViewById(R.id.list_sholat);

        LinearLayoutManager in= new LinearLayoutManager(SholatActivity.this);

        final ArrayList<Sholat> sholats = new ArrayList<>();
        sholats.add(new Sholat("Jadwal Sholat", "Detail jadwal sholat hari ini"));
        sholats.add(new Sholat("Arah Kiblat", "Kompas yang menunjukan arah kiblat"));

        AdapterListSholat adapter = new AdapterListSholat(sholats);

        adapter.setListener(new OnClickListener() {
            @Override
            public void onclick(int position) {
                sholats.get(position);
                switch (position){
                    case 0:
                        Intent intent = new Intent(SholatActivity.this, JadwalkuActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(SholatActivity.this, KiblatActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setLayoutManager(in);
        list.setAdapter(adapter);
    }
}
