package org.d3ifcool.sholatku.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.database.DataContract;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListSholatku extends RecyclerView.Adapter<AdapterListSholatku.sholatkuHolder> {
    private Context mContext;
    private Cursor mCursor;
    private View mEmptyView;
    private OnClickListener mListener;

    public AdapterListSholatku(Context mContext, View mEmptyView, OnClickListener mListener) {
        this.mContext = mContext;
        this.mEmptyView = mEmptyView;
        this.mListener = mListener;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();

        mCursor = newCursor;
        if (newCursor == null || newCursor.getCount() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public sholatkuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_viewsholatku, viewGroup, false);
        return new sholatkuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sholatkuHolder sholatkuHolder, int i) {
        mCursor.moveToPosition(i);

        int indexTanggal = mCursor.getColumnIndex(DataContract.Entry.COLUMN_TANGGAL);
        int indexNamaSholat = mCursor.getColumnIndex(DataContract.Entry.COLUMN_SHOLAT);
        int indexTelat = mCursor.getColumnIndex(DataContract.Entry.COLUMN_WAKTU_TELAT);

        sholatkuHolder.namasholat.setText(mCursor.getString(indexNamaSholat));
        sholatkuHolder.tanggal.setText(mCursor.getString(indexTanggal));
        sholatkuHolder.waktutelat.setText(mCursor.getString(indexTelat));
    }

    @Override
    public int getItemCount() {
        if(mCursor ==null) return 0;
        return mCursor.getCount();
    }

    public class sholatkuHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final TextView namasholat, tanggal, waktutelat;
        public sholatkuHolder(@NonNull View itemView) {
            super(itemView);
            namasholat = itemView.findViewById(R.id.tvNamaSholat);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            waktutelat = itemView.findViewById(R.id.tvTelat);
        }

        @Override
        public void onItemClick(int id) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mCursor.moveToPosition(position);
                int indexId = mCursor.getColumnIndex(DataContract.Entry._ID);
                mListener.onItemClick(mCursor.getInt(indexId));
            }
        }
    }
    public interface OnClickListener {
        void onItemClick(int id);
    }
}
