package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.fragment.JadwalFragment;
import org.d3ifcool.sholatku.fragment.KiblatFragment;

public class DashboardSholatSettingActivity extends AppCompatActivity {

    public static final String EXTRA_SETTING_OPTION = "option";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sholat_setting);
        String option = getIntent().getStringExtra(EXTRA_SETTING_OPTION);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (option == null) {
            finish();
            return;
        }

        switch (option) {
            case "satu": {
                fragmentManager.beginTransaction()
                        .add(R.id.settingsContainer, new JadwalFragment())
                        .commit();
                break;
            }
            case "dua": {
                fragmentManager.beginTransaction()
                        .add(R.id.settingsContainer, new KiblatFragment())
                        .commit();
                break;
            }
        }
    }
}
