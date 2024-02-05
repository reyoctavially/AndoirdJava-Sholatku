package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.fragment.BerandaSholatFragment;
import org.d3ifcool.sholatku.fragment.JadwalFragment;
import org.d3ifcool.sholatku.fragment.KiblatFragment;
import org.d3ifcool.sholatku.fragment.SholatFragment;

public class DashboardSholatActivity extends AppCompatActivity implements SholatFragment.OnOptionClickListener {

    private boolean isTwoPane;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sholat);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.detailContainer) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, new SholatFragment())
                    .commit();
        }

        if (isTwoPane) {
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, new BerandaSholatFragment())
                    .commit();
        }
    }

    @Override
    public void onOptionSelected(String option) {
        if (isTwoPane) {
            switch (option) {
                case "satu":
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, new JadwalFragment())
                            .commit();
                    break;
                case "dua":
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, new KiblatFragment())
                            .commit();
                    break;
            }
        } else {
            Intent intent = new Intent(this, DashboardSholatSettingActivity.class);
            intent.putExtra(DashboardSholatSettingActivity.EXTRA_SETTING_OPTION, option);
            startActivity(intent);
        }
    }
}
