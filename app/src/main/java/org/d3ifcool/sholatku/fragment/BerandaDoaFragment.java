package org.d3ifcool.sholatku.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d3ifcool.sholatku.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaDoaFragment extends Fragment {


    public BerandaDoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda_doa, container, false);
    }

}
