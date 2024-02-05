package org.d3ifcool.sholatku.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.adapter.AdapterListSholatku;
import org.d3ifcool.sholatku.database.DataContract;

public class SholatkuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER = 0;

    AdapterListSholatku mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sholatku);
        RecyclerView recyclerView = findViewById(R.id.list_sholatku);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this,
                manager.getOrientation());
        recyclerView.addItemDecoration(divider);
        View emptyView = findViewById(R.id.empty_view);
        mCursorAdapter = new AdapterListSholatku(this, emptyView, new AdapterListSholatku.OnClickListener() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent(SholatkuActivity.this, CatatWaktuSholatActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(DataContract.Entry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mCursorAdapter);
        LoaderManager.getInstance(this).initLoader(LOADER, null, this);
    }

    private void deleteAllDatas() {
        int rowsDeleted = getContentResolver().delete(DataContract.Entry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + "Data berhasil di hapus");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_datasholatku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                deleteAllDatas();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                DataContract.Entry._ID,
                DataContract.Entry.COLUMN_SHOLAT,
                DataContract.Entry.COLUMN_TANGGAL,
                DataContract.Entry.COLUMN_WAKTU_TELAT };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                DataContract.Entry.CONTENT_URI,
                projection,
                null,
                null,
                DataContract.Entry._ID + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
