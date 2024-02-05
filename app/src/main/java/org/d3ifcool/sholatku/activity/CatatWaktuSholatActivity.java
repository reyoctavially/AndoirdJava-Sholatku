package org.d3ifcool.sholatku.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.sholatku.notif.NotificationPublisher;
import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.constans.VarConstans;
import org.d3ifcool.sholatku.database.DataContract;
import org.d3ifcool.sholatku.helper.MethodHelper;
import org.d3ifcool.sholatku.helper.WaktuSholatHelper;

public class CatatWaktuSholatActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_PET_LOADER = 0;
    private Uri current;

    private MethodHelper methodHelper = new MethodHelper();
    private WaktuSholatHelper waktuSholatHelper;

    private int countTime;
    private String jadwal;
    private int detikSekarang, detikShubuh, detikDzuhur, detikAshar, detikMaghrib, detikIsya;
    private TextView tanggal, namasholat, waktutelat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catat_waktu_sholat);

        waktuSholatHelper = new WaktuSholatHelper(this);

        Intent intent = getIntent();
        current = intent.getData();
        invalidateOptionsMenu();
        detikSekarang = methodHelper.getSumWaktuDetik();
        jadwal = waktuSholatHelper.getJadwalShalat();
        detikShubuh = waktuSholatHelper.getJmlWaktuShubuh();
        detikDzuhur = waktuSholatHelper.getJmlWaktuDzuhur();
        detikAshar = waktuSholatHelper.getJmlWaktuAshar();
        detikMaghrib = waktuSholatHelper.getJmlWaktuMaghrib();
        detikIsya = waktuSholatHelper.getJmlWaktuIsya();

        tanggal = findViewById(R.id.tv_tanggal);
        namasholat = findViewById(R.id.tv_waktusholat);
        waktutelat = findViewById(R.id.tv_telat);

        methodHelper.getSystemTime();
        methodHelper.getSystemRealTime();
        methodHelper.getSumRealTime();
        waktuSholatHelper.setJadwalSholat(namasholat);
        tanggal.setText(methodHelper.getDateToday());

        switch (jadwal) {
            case VarConstans.Constans.SHUBUH:
                countTime = (detikSekarang - detikShubuh) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.DZUHUR:
                countTime = (detikSekarang - detikDzuhur) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.ASHAR:
                countTime = (detikSekarang - detikAshar) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.MAGHRIB:
                countTime = (detikSekarang - detikMaghrib) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.ISYA:
                countTime = (detikSekarang - detikIsya) * VarConstans.Constans.DETIK_KE_MILI;
                break;
        }
        waktuSholatHelper.CounupTime(countTime, waktutelat);
    }

    private void save(){
        String nama, tgl, telat;
        int waktu, dikerjakan;
        nama = namasholat.getText().toString().trim();
        tgl = tanggal.getText().toString().trim();
        waktu = 0;
        dikerjakan = detikSekarang;

        telat = waktutelat.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(DataContract.Entry.COLUMN_SHOLAT, nama);
        values.put(DataContract.Entry.COLUMN_TANGGAL, tgl);
        values.put(DataContract.Entry.COLUMN_WAKTU_SHOLAT, waktu);
        values.put(DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN, dikerjakan);
        values.put(DataContract.Entry.COLUMN_WAKTU_TELAT, telat);

        Uri resultUri = getContentResolver().insert(DataContract.Entry.CONTENT_URI,values);
        if (resultUri == null) {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Terimakasih sudah sholat", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catatsholatku, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                finish();
                return true;
            case R.id.action_discard:
                scheduleNotification(getNotification(), 100000);
                Toast.makeText(this, "Nanti aku ingetin lagi yah ... ", Toast.LENGTH_SHORT).show();
                finish();
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
                DataContract.Entry.COLUMN_WAKTU_SHOLAT,
                DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN,
                DataContract.Entry.COLUMN_WAKTU_TELAT};
        return new CursorLoader(this,
                current,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1){
            return;
        }
        if (data.moveToFirst()){
            int sholat = data.getColumnIndex(DataContract.Entry.COLUMN_SHOLAT);
            int tanggal = data.getColumnIndex(DataContract.Entry.COLUMN_TANGGAL);
            int waktu = data.getColumnIndex(DataContract.Entry.COLUMN_WAKTU_SHOLAT);
            int dikerjakan = data.getColumnIndex(DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN);
            int telat = data.getColumnIndex(DataContract.Entry.COLUMN_WAKTU_TELAT);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }
}
