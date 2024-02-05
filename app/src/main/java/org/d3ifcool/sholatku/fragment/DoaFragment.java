package org.d3ifcool.sholatku.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.activity.DetailDoaActivity;
import org.d3ifcool.sholatku.activity.DoaActivity;
import org.d3ifcool.sholatku.adapter.AdapterListDoa;
import org.d3ifcool.sholatku.data.Doa;
import org.d3ifcool.sholatku.interfaces.OnClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoaFragment extends Fragment {

    public interface OnOptionClickListener {
        void onOptionSelected(String option);
    }

    private OnOptionClickListener mCallback;

    public DoaFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_doa, container, false);
        RecyclerView list = rootView.findViewById(R.id.list_doa);
        LinearLayoutManager in= new LinearLayoutManager(getContext());

        final ArrayList<Doa> doas = new ArrayList<>();
        doas.add(new Doa("Doa Sebelum Tidur", "Dibaca sebelum tidur"));
        doas.add(new Doa("Doa Bangun Tidur", "Dibaca setelah bangun tidur"));
        doas.add(new Doa("Doa Menjelang Subuh", "Dibaca ketika menjelang subuh"));
        doas.add(new Doa("Doa Menyambut Pagi", "Dibaca untuk menyambut pagi"));
        doas.add(new Doa("Doa Menyambut Sore", "Dibaca untuk menyambut sore"));
        doas.add(new Doa("Doa Mimpi Baik", "Dibaca ketika mendapat mimpi baik"));
        doas.add(new Doa("Doa Mimpi Buruk", "Dibaca ketika mendapat mimpi buruk"));
        doas.add(new Doa("Doa Masuk Rumah", "Dibaca ketika akan masuk rumah"));
        doas.add(new Doa("Doa Keluar Rumah", "Dibaca ketika akan keluar rumah"));
        doas.add(new Doa("Doa Sebelum Makan", "Dibaca sebelum makan"));
        doas.add(new Doa("Doa Sesudah Makan", "Dibaca sesudah makan"));
        doas.add(new Doa("Doa Bercermin", "Dibaca pada saat bercermin"));
        doas.add(new Doa("Doa Istinja", "Dibaca pada saat istinja"));

        AdapterListDoa adapter = new AdapterListDoa(doas);

        adapter.setListener(new OnClickListener() {
            @Override
            public void onclick(int position) {
                doas.get(position);
                switch (position){
                    case 0:
                        mCallback.onOptionSelected("detail");
                        Intent intent = new Intent(getContext(), DetailDoaActivity.class);
                        intent.putExtra("datadoa", "sebelumtidur");
                        startActivity(intent);
                        break;
                    case 1:
                        mCallback.onOptionSelected("detail");
                        Intent intent2 = new Intent(getContext(), DetailDoaActivity.class);
                        intent2.putExtra("datadoa", "banguntidur");
                        startActivity(intent2);
                        break;
                    case 2:
                        mCallback.onOptionSelected("detail");
                        Intent intent3 = new Intent(getContext(), DetailDoaActivity.class);
                        intent3.putExtra("datadoa", "menjelangsubuh");
                        startActivity(intent3);
                        break;
                    case 3:
                        mCallback.onOptionSelected("detail");
                        Intent intent4 = new Intent(getContext(), DetailDoaActivity.class);
                        intent4.putExtra("datadoa", "menyambutpagi");
                        startActivity(intent4);
                        break;
                    case 4:
                        mCallback.onOptionSelected("detail");
                        Intent intent5 = new Intent(getContext(), DetailDoaActivity.class);
                        intent5.putExtra("datadoa", "menyambutsore");
                        startActivity(intent5);
                        break;
                    case 5:
                        mCallback.onOptionSelected("detail");
                        Intent intent6 = new Intent(getContext(), DetailDoaActivity.class);
                        intent6.putExtra("datadoa", "mimpibaik");
                        startActivity(intent6);
                        break;
                    case 6:
                        mCallback.onOptionSelected("detail");
                        Intent intent7 = new Intent(getContext(), DetailDoaActivity.class);
                        intent7.putExtra("datadoa", "mimpiburuk");
                        startActivity(intent7);
                        break;
                    case 7:
                        mCallback.onOptionSelected("detail");
                        Intent intent8 = new Intent(getContext(), DetailDoaActivity.class);
                        intent8.putExtra("datadoa", "masukrumah");
                        startActivity(intent8);
                        break;
                    case 8:
                        mCallback.onOptionSelected("detail");
                        Intent intent9 = new Intent(getContext(), DetailDoaActivity.class);
                        intent9.putExtra("datadoa", "keluarrumah");
                        startActivity(intent9);
                        break;
                    case 9:
                        mCallback.onOptionSelected("detail");
                        Intent intent10 = new Intent(getContext(), DetailDoaActivity.class);
                        intent10.putExtra("datadoa", "sebelummakan");
                        startActivity(intent10);
                        break;
                    case 10:
                        mCallback.onOptionSelected("detail");
                        Intent intent11 = new Intent(getContext(), DetailDoaActivity.class);
                        intent11.putExtra("datadoa", "sesudahmakan");
                        startActivity(intent11);
                        break;
                    case 11:
                        mCallback.onOptionSelected("detail");
                        Intent intent12 = new Intent(getContext(), DetailDoaActivity.class);
                        intent12.putExtra("datadoa", "bercermin");
                        startActivity(intent12);
                        break;
                    case 12:
                        mCallback.onOptionSelected("detail");
                        Intent intent13 = new Intent(getContext(), DetailDoaActivity.class);
                        intent13.putExtra("datadoa", "istinja");
                        startActivity(intent13);
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
