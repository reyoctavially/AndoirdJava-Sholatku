package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.sholatku.R;

import java.util.ArrayList;

public class TasbihActivity extends AppCompatActivity {
    private TextView judul_tasbih, count, tasbih_selanjutnya, judul_selanjutnya;
    private ImageView iv_sekarang, iv_selanjutnya;
    private Button next;
    private CardView klik;
    private Vibrator getar;
    int angka = 0;
    int index = 0;
    private ArrayList<String> tasbih = new ArrayList<>();
    private ArrayList<Integer> gambar_tasbih = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih);

        getar = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        judul_tasbih = findViewById(R.id.tv_judul_tasbih);
        judul_selanjutnya = findViewById(R.id.tv_judul_selanjutnya);
        count = findViewById(R.id.tv_count);
        tasbih_selanjutnya = findViewById(R.id.tv_tasbih_selanjutnya);
        klik = findViewById(R.id.cv_klik_layar);
        next = findViewById(R.id.btn_tasbih_selanjutnya);
        iv_sekarang = findViewById(R.id.iv_judul);
        iv_selanjutnya = findViewById(R.id.iv_selanjutnya);
        next.setVisibility(View.GONE);

        tasbih.add("Subhanallah");
        tasbih.add("Alhamdulillah");
        tasbih.add("Allahuakbar");

        gambar_tasbih.add(R.drawable.img_subhanallah);
        gambar_tasbih.add(R.drawable.img_alhamdulillah);
        gambar_tasbih.add(R.drawable.img_allahuakbar);

        judul_tasbih.setText("( " + tasbih.get(index) + " )");
        tasbih_selanjutnya.setText("( " + tasbih.get(index+1) + " )");
        iv_sekarang.setImageResource(gambar_tasbih.get(index));
        iv_selanjutnya.setImageResource(gambar_tasbih.get(index+1));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 1){
                    index++;
                    angka = 0;
                    judul_tasbih.setText("( " + tasbih.get(index) + " )");
                    iv_sekarang.setImageResource(gambar_tasbih.get(index));
                    count.setText("" + angka);
                    next.setVisibility(View.GONE);
                    klik.setClickable(true);
                    tasbih_selanjutnya.setText("-");
//                    judul_selanjutnya.setVisibility(View.GONE);
                    iv_selanjutnya.setVisibility(View.GONE);
                    next.setText("Selesai");
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TasbihActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    index++;
                    angka = 0;
                    judul_tasbih.setText("( " + tasbih.get(index) + " )");
                    iv_sekarang.setImageResource(gambar_tasbih.get(index));
                    tasbih_selanjutnya.setText("( " + tasbih.get(index + 1) + " )");
                    iv_selanjutnya.setImageResource(gambar_tasbih.get(index+1));
                    count.setText("" + angka);
                    next.setVisibility(View.GONE);
                    klik.setClickable(true);
                }
            }
        });

        klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angka = angka+1;
                count.setText("" + angka);
                if (angka == 33){
                    getar.vibrate(400);
                    next.setVisibility(View.VISIBLE);
                    klik.setClickable(false);
                }
            }
        });
    }
}
