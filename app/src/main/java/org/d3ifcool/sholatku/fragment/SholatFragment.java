package org.d3ifcool.sholatku.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.activity.JadwalkuActivity;
import org.d3ifcool.sholatku.activity.KiblatActivity;
import org.d3ifcool.sholatku.activity.SholatActivity;
import org.d3ifcool.sholatku.adapter.AdapterListSholat;
import org.d3ifcool.sholatku.data.Sholat;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SholatFragment extends Fragment {

    public interface OnOptionClickListener {
        void onOptionSelected(String option);
    }

    private OnOptionClickListener mCallback;

    public SholatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnOptionClickListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement SettingOptionsFragment.OnOptionClickListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sholat, container, false);
        RecyclerView list = rootView.findViewById(R.id.list_sholat);
        LinearLayoutManager in= new LinearLayoutManager(getContext());

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
                        mCallback.onOptionSelected("satu");
                        break;
                    case 1:
                        mCallback.onOptionSelected("dua");
                        break;
                }
            }
        });

        list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        list.setLayoutManager(in);
        list.setAdapter(adapter);
        return rootView;
    }

}
