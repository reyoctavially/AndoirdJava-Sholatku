package org.d3ifcool.sholatku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.fragment.BerandaDoaFragment;
import org.d3ifcool.sholatku.fragment.DetailDoaFragment;
import org.d3ifcool.sholatku.fragment.DoaFragment;
import org.d3ifcool.sholatku.fragment.JadwalFragment;
import org.d3ifcool.sholatku.fragment.KiblatFragment;
import org.d3ifcool.sholatku.fragment.SholatFragment;

public class DashboardDoaActivity extends AppCompatActivity implements DoaFragment.OnOptionClickListener {

    private boolean isTwoPane;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doa);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.detailContainer2) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container2, new DoaFragment())
                    .commit();
        }

        if (isTwoPane) {
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer2, new BerandaDoaFragment())
                    .commit();
        }
    }

    @Override
    public void onOptionSelected(String option) {
        if (isTwoPane) {
            switch (option) {
                case "detail":
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer2, new DetailDoaFragment())
                            .commit();
                    break;
            }
        } else {
            Intent intent = new Intent(this, DashboardDoaSettingActivity.class);
            intent.putExtra(DashboardDoaSettingActivity.EXTRA_SETTING_OPTION, option);
            startActivity(intent);
        }
    }
}
