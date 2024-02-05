package org.d3ifcool.sholatku.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.constans.VarConstans;
import org.d3ifcool.sholatku.helper.MethodHelper;
import org.d3ifcool.sholatku.helper.WaktuSholatHelper;
import org.d3ifcool.sholatku.notif.NotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class JadwalkuActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private MethodHelper methodHelper = new MethodHelper();
    private WaktuSholatHelper waktuSholatHelper;

    private int countTime;
    public int hasilJamShubuh, hasilJamDzuhur, hasilJamAshar, hasilJamMaghrib, hasilJamIsya;
    public int hasilMenitShubuh, hasilMenitDzuhur, hasilMenitAshar, hasilMenitMaghrib, hasilMenitIsya;
    private TextView jam, sholatSekarang, tanggal, timer, waktuShubuh, waktuDzuhur, waktuAshar, waktuMaghrib, waktuIsya, sholatMendatang;
    private Location location;
    private TextView locationTv;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    //untuk memindahkan data ke class lain
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    // memanggil switch
    private Switch switchShubuh, switchDzuhur, switchAshar, switchMaghrib, switchIsya;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalku);

        waktuSholatHelper = new WaktuSholatHelper(this);

        Thread myThread = null;

        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();

        int detiksekarang = methodHelper.getSumWaktuDetik(); // Mengambil detik sekarang dari methodHelper
        String jadwal = waktuSholatHelper.getJadwalShalat(); // Mengambil jadwal sholat dari waktuSholatHelper

        jam = findViewById(R.id.tv_waktu);
        sholatSekarang = findViewById(R.id.tv_sholat_sekarang);
        tanggal = findViewById(R.id.tv_tanggal);
        timer = findViewById(R.id.tv_timer);
        waktuShubuh = findViewById(R.id.tv_waktu_shubuh);
        waktuDzuhur = findViewById(R.id.tv_waktu_dzuhur);
        waktuAshar = findViewById(R.id.tv_waktu_ashar);
        waktuMaghrib = findViewById(R.id.tv_waktu_maghrib);
        waktuIsya = findViewById(R.id.tv_waktu_isya);
        sholatMendatang = findViewById(R.id.tv_sholat_berikutnya);

        // deklarasi switch
        switchShubuh = findViewById(R.id.switch_shubuh);
        switchDzuhur = findViewById(R.id.switch_dzuhur);
        switchAshar = findViewById(R.id.switch_ashar);
        switchMaghrib = findViewById(R.id.switch_maghrib);
        switchIsya = findViewById(R.id.switch_isya);

        // set notifikasi menggunakan switch
        SharedPreferences getnotif = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean hasilSubuh = getnotif.getBoolean("notifSubuh", false);
        Boolean hasilDzuhur = getnotif.getBoolean("notifDzuhur", false);
        Boolean hasilAshar = getnotif.getBoolean("notifAshar", false);
        Boolean hasilMaghrib = getnotif.getBoolean("notifMaghrib", false);
        Boolean hasilIsya = getnotif.getBoolean("notifIsya", false);

        switchShubuh.setChecked(hasilSubuh);
        switchDzuhur.setChecked(hasilDzuhur);
        switchAshar.setChecked(hasilAshar);
        switchMaghrib.setChecked(hasilMaghrib);
        switchIsya.setChecked(hasilIsya);

        switchShubuh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shubuhNotification(getNotifShubuh());
                SharedPreferences.Editor notif = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                notif.putBoolean("notifSubuh", isChecked);
                notif.apply();
            }
        });

        switchDzuhur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shubuhNotification(getNotifShubuh());
                SharedPreferences.Editor notif = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                notif.putBoolean("notifDzuhur", isChecked);
                notif.apply();
            }
        });

        switchAshar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shubuhNotification(getNotifShubuh());
                SharedPreferences.Editor notif = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                notif.putBoolean("notifAshar", isChecked);
                notif.apply();
            }
        });

        switchMaghrib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shubuhNotification(getNotifShubuh());
                SharedPreferences.Editor notif = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                notif.putBoolean("notifMaghrib", isChecked);
                notif.apply();
            }
        });

        switchIsya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shubuhNotification(getNotifShubuh());
                SharedPreferences.Editor notif = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                notif.putBoolean("notifIsya", isChecked);
                notif.apply();
            }
        });

        methodHelper.getSystemTime();
        methodHelper.getSystemRealTime();
        methodHelper.getSumRealTime();
        tanggal.setText(methodHelper.getDateToday());

        // Memanggil tiap detik waktu sholat
        int detikShubuh = waktuSholatHelper.getJmlWaktuShubuh();
        int detikDzuhur = waktuSholatHelper.getJmlWaktuDzuhur();
        int detikAshar = waktuSholatHelper.getJmlWaktuAshar();
        int detikMaghrib = waktuSholatHelper.getJmlWaktuMaghrib();
        int detikIsya = waktuSholatHelper.getJmlWaktuIsya();
        int detikSesudahMalam = waktuSholatHelper.getJmlAftMidnight();
        int detikSebelumMalam = waktuSholatHelper.getJmlBeMidnight();

        methodHelper.getSystemTime();
        methodHelper.getSystemRealTime();
        methodHelper.getSumRealTime();
        waktuSholatHelper.setJadwalSholat(sholatSekarang); // Set jadwal sholat pada waktu sholat sekarang

        switch (jadwal) {
            case VarConstans.Constans.SHUBUH: // Jika yang tampil adalah sholat subuh
                // set sholat mendatangnya adalah sholat dzuhur
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.DZUHUR.substring(7));
                // set waktu hitung mundur dari waktu dzuhur di kurang waktu sekarang di kali detik ke mili
                countTime = (detikDzuhur - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.DZUHUR:
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.ASHAR.substring(7));
                countTime = (detikAshar - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.ASHAR:
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.MAGHRIB.substring(7));
                countTime = (detikMaghrib - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.MAGHRIB:
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.ISYA.substring(7));
                countTime = (detikIsya - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                break;
            case VarConstans.Constans.ISYA:
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.SHUBUH.substring(7));
                // jika sudah tengah malam dan sebelum subuh
                // maka detik subuh di kurangi detik sekarang
                if ((detiksekarang == detikSesudahMalam) || (detiksekarang < detikShubuh)) {
                    countTime = (detikShubuh - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                    // jika masih isya atau sebelum tengah malam
                    // detik subuh + detik sebelum malam - detik sekarang
                } else if ((detiksekarang == detikIsya) || (detiksekarang <= detikSebelumMalam)) {
                    countTime =  (detikShubuh + detikSebelumMalam - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                }
                break;
            default:
                // pengaturan defaultnya adalah sholat dzuhur
                sholatMendatang.setText("Menuju Sholat " + VarConstans.Constans.DZUHUR.substring(7));
                countTime = (detikDzuhur - detiksekarang) * VarConstans.Constans.DETIK_KE_MILI;
                break;
        }
        // set setiap waktu sholatnya dengan waktu sholat helper
        waktuSholatHelper.setTimeOnline(waktuShubuh, waktuDzuhur, waktuAshar, waktuMaghrib, waktuIsya);
        // set timer hitung mundur
        waktuSholatHelper.CoundownTime(countTime, timer);

        // set notifikasi untuk waktu sholat
        String Shubuh = waktuShubuh.getText().toString();
        String Dzuhur = waktuDzuhur.getText().toString();
        String Ashar = waktuAshar.getText().toString();
        String Maghrib = waktuMaghrib.getText().toString();
        String Isya = waktuIsya.getText().toString();

        //Memotong string untuk jam
        String jamShubuh = Shubuh.substring(0,2);
        String jamDzuhur = Dzuhur.substring(0,2);
        String jamAshar = Ashar.substring(0,2);
        String jamMaghrib = Maghrib.substring(0,2);
        String jamIsya = Isya.substring(0,2);

        //Memotong string untuk menit
        String menitShubuh = Shubuh.substring(5,7);
        String menitDzuhur = Dzuhur.substring(5,7);
        String menitAshar = Ashar.substring(5,7);
        String menitMaghrib = Maghrib.substring(5,7);
        String menitIsya = Isya.substring(5,7);

        //Konversi String ke Interger
        hasilJamShubuh = Integer.parseInt(jamShubuh);
        hasilJamDzuhur = Integer.parseInt(jamDzuhur);
        hasilJamAshar = Integer.parseInt(jamAshar);
        hasilJamMaghrib = Integer.parseInt(jamMaghrib);
        hasilJamIsya = Integer.parseInt(jamIsya);

        hasilMenitShubuh = Integer.parseInt(menitShubuh);
        hasilMenitDzuhur = Integer.parseInt(menitDzuhur);
        hasilMenitAshar = Integer.parseInt(menitAshar);
        hasilMenitMaghrib = Integer.parseInt(menitMaghrib);
        hasilMenitIsya = Integer.parseInt(menitIsya);

        //script untuk mendapatkan lokasi
        locationTv = findViewById(R.id.tv_lokasi);
        // we add permissions we need to request location of the users
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }

    public void shubuhNotification(Notification notification) {
        ComponentName receiver = new ComponentName(this, NotificationPublisher.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamShubuh);
        calendar.set(Calendar.MINUTE, hasilMenitShubuh);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void dzuhurNotification(Notification notification) {
        ComponentName receiver = new ComponentName(this, NotificationPublisher.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamDzuhur);
        calendar.set(Calendar.MINUTE, hasilMenitDzuhur);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void asharNotification(Notification notification) {
        ComponentName receiver = new ComponentName(this, NotificationPublisher.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamAshar);
        calendar.set(Calendar.MINUTE, hasilMenitAshar);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void maghribNotification(Notification notification) {
        ComponentName receiver = new ComponentName(this, NotificationPublisher.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamMaghrib);
        calendar.set(Calendar.MINUTE, hasilMenitMaghrib);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 3, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void isyaNotification(Notification notification) {
        ComponentName receiver = new ComponentName(this, NotificationPublisher.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamIsya);
        calendar.set(Calendar.MINUTE, hasilMenitIsya);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 4, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifShubuh() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat shubuh telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifDzuhur() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat dzuhur telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifAshar() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat ashar telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 2, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifMaghrib() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat maghrib telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifIsya() {
        Uri alertSound = Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktu sholat isya telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(this, CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 4, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentTime= (TextView)findViewById(R.id.tv_waktu);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    String curTime = hours + ":" + minutes;
                    txtCurrentTime.setText(curTime);
                }catch (Exception e) {}
            }
        });
    }

    //Script untuk mendapatkan lokasi

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            locationTv.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            locationTv.setText("Latitude : " + location.getLatitude() + " & Longitude : " + location.getLongitude());
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("latitude", location.getLatitude()+"");
            editor.putString("longtitude", location.getLongitude()+"");
            editor.apply();
        }else{
            Toast.makeText(this, "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show();
        }

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(this, "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            locationTv.setText("Latitude : " + location.getLatitude() + " & Longitude : " + location.getLongitude());
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("latitude", location.getLatitude()+"");
            editor.putString("longtitude", location.getLongitude()+"");
            editor.apply();
        } else {
            Toast.makeText(this, "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(JadwalkuActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }
}
