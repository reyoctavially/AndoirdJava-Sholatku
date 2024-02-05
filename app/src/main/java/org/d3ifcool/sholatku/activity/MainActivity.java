package org.d3ifcool.sholatku.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.constans.VarConstans;
import org.d3ifcool.sholatku.database.DataProvider;
import org.d3ifcool.sholatku.helper.MethodHelper;
import org.d3ifcool.sholatku.helper.WaktuSholatHelper;

public class MainActivity extends AppCompatActivity {
    private ImageView sholat, sholatku, doa, tasbih, suratpendek, tuntunansholat;
    private TextView tanggal, namasholat;
    private Button catat;
    private String waktusholat;
    private MethodHelper methodHelper = new MethodHelper();
    private WaktuSholatHelper waktuSholatHelper;
    private DataProvider dataProvider;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waktuSholatHelper = new WaktuSholatHelper(this);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_polymer);
        menu.setDisplayUseLogoEnabled(true);

        sholat = findViewById(R.id.iv_sholat);
        sholatku = findViewById(R.id.iv_sholatku);
        doa = findViewById(R.id.iv_doa);
        tasbih = findViewById(R.id.iv_tasbih);
        suratpendek = findViewById(R.id.iv_suratpendek);
        tuntunansholat = findViewById(R.id.iv_tuntunansholat);


        tanggal = findViewById(R.id.tv_tanggal);
        namasholat = findViewById(R.id.tv_waktusholat);

        catat = findViewById(R.id.btn_catat);

        methodHelper.getSystemTime();
        methodHelper.getSystemRealTime();
        methodHelper.getSumRealTime();
        waktuSholatHelper.setJadwalSholat(namasholat);
        tanggal.setText(methodHelper.getDateToday());

        waktusholat = namasholat.getText().toString();

        if (waktusholat.equals(VarConstans.Constans.BUKAN_WAKTU_SHOLAT)) {
            catat.setVisibility(View.GONE);
        } else {
            catat.setVisibility(View.VISIBLE);
//            if (isDone(waktusholat)){
//                catat.setVisibility(View.GONE);
//            } else {
//                catat.setVisibility(View.VISIBLE);
//            }
        }

        sholat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SholatActivity.class);
                startActivity(intent);

            }
        });
        sholatku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SholatkuActivity.class);
                startActivity(intent);
            }
        });
        doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoaActivity.class);
                startActivity(intent);
            }
        });
        tasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TasbihActivity.class);
                startActivity(intent);
            }
        });
        catat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatatWaktuSholatActivity.class);
                startActivity(intent);
            }
        });

        suratpendek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuratPendekActivity.class);
                startActivity(intent);
            }
        });

        tuntunansholat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TuntunanSholatActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_about:
                showAbout();
                break;
        }
    }
    private void showAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private boolean isDone(String sholat) {
        Cursor res = null;
        try {
            res = dataProvider.readDatabase(this, methodHelper.getDateToday(), sholat);
            int cek = res.getCount();
            return cek == 0;
        } finally {
            res.close();
        }
    }
}
