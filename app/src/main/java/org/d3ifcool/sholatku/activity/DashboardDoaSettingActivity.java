package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.fragment.DetailDoaFragment;
import org.d3ifcool.sholatku.fragment.JadwalFragment;
import org.d3ifcool.sholatku.fragment.KiblatFragment;

public class DashboardDoaSettingActivity extends AppCompatActivity {

    public static final String EXTRA_SETTING_OPTION = "option";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doa_setting);
        String option = getIntent().getStringExtra(EXTRA_SETTING_OPTION);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (option == null) {
            finish();
            return;
        }

        switch (option) {
            case "detail": {
                fragmentManager.beginTransaction()
                        .add(R.id.settingsContainer2, new DetailDoaFragment())
                        .commit();
                break;
            }
        }
    }
}
