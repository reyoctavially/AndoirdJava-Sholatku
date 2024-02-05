package org.d3ifcool.sholatku.fragment;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d3ifcool.sholatku.R;
import org.d3ifcool.sholatku.activity.CatatWaktuSholatActivity;
import org.d3ifcool.sholatku.constans.VarConstans;
import org.d3ifcool.sholatku.helper.MethodHelper;
import org.d3ifcool.sholatku.helper.WaktuSholatHelper;
import org.d3ifcool.sholatku.notif.NotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalFragment extends Fragment {

    private MethodHelper methodHelper = new MethodHelper();
    private WaktuSholatHelper waktuSholatHelper;

    private int countTime;
    public int hasilJamShubuh, hasilJamDzuhur, hasilJamAshar, hasilJamMaghrib, hasilJamIsya;
    public int hasilMenitShubuh, hasilMenitDzuhur, hasilMenitAshar, hasilMenitMaghrib, hasilMenitIsya;
    private TextView jam, sholatSekarang, tanggal, timer, waktuShubuh, waktuDzuhur, waktuAshar, waktuMaghrib, waktuIsya, sholatMendatang;

    public JadwalFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View rootView = inflater.inflate(R.layout.fragment_jadwal, container, false);

        waktuSholatHelper = new WaktuSholatHelper(getContext());

        int detiksekarang = methodHelper.getSumWaktuDetik(); // Mengambil detik sekarang dari methodHelper
        String jadwal = waktuSholatHelper.getJadwalShalat(); // Mengambil jadwal sholat dari waktuSholatHelper

        jam = rootView.findViewById(R.id.tv_waktu);
        sholatSekarang = rootView.findViewById(R.id.tv_sholat_sekarang);
        tanggal = rootView.findViewById(R.id.tv_tanggal);
        timer = rootView.findViewById(R.id.tv_timer);
        waktuShubuh = rootView.findViewById(R.id.tv_waktu_shubuh);
        waktuDzuhur = rootView.findViewById(R.id.tv_waktu_dzuhur);
        waktuAshar = rootView.findViewById(R.id.tv_waktu_ashar);
        waktuMaghrib = rootView.findViewById(R.id.tv_waktu_maghrib);
        waktuIsya = rootView.findViewById(R.id.tv_waktu_isya);
        sholatMendatang = rootView.findViewById(R.id.tv_sholat_berikutnya);

        methodHelper.getSystemTime();
        methodHelper.getSystemRealTime();
        methodHelper.getSumRealTime();
        tanggal.setText(methodHelper.getDateToday());

        // Mengambil waktu saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
        String currentDateandTime = sdf.format(new Date());
        jam.setText(currentDateandTime);

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

        Log.d("waktushubuh", hasilJamShubuh + " " + hasilMenitShubuh + "");
        Log.d("waktudzuhur", hasilJamDzuhur + " " + hasilMenitDzuhur + "");
        Log.d("waktuashar", hasilJamAshar + " " + hasilMenitAshar + "");
        Log.d("waktumaghrib", hasilJamMaghrib + " " + hasilMenitMaghrib + "");
        Log.d("waktuisya", hasilJamIsya + " " + hasilMenitIsya + "");

        //Set notifikasi sholat untuk setiap waktu sholat
        shubuhNotification(getNotifShubuh());
        dzuhurNotification(getNotifDzuhur());
        asharNotification(getNotifAshar());
        maghribNotification(getNotifMaghrib());
        isyaNotification(getNotifIsya());
        return rootView;
    }
    
    public void shubuhNotification(Notification notification) {
        ComponentName receiver = new ComponentName(getContext(), NotificationPublisher.class);
        PackageManager pm = getContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamShubuh);
        calendar.set(Calendar.MINUTE, hasilMenitShubuh);

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void dzuhurNotification(Notification notification) {
        ComponentName receiver = new ComponentName(getContext(), NotificationPublisher.class);
        PackageManager pm = getContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamDzuhur);
        calendar.set(Calendar.MINUTE, hasilMenitDzuhur);

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void asharNotification(Notification notification) {
        ComponentName receiver = new ComponentName(getContext(), NotificationPublisher.class);
        PackageManager pm = getContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamAshar);
        calendar.set(Calendar.MINUTE, hasilMenitAshar);

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 2, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void maghribNotification(Notification notification) {
        ComponentName receiver = new ComponentName(getContext(), NotificationPublisher.class);
        PackageManager pm = getContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamMaghrib);
        calendar.set(Calendar.MINUTE, hasilMenitMaghrib);

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 3, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void isyaNotification(Notification notification) {
        ComponentName receiver = new ComponentName(getContext(), NotificationPublisher.class);
        PackageManager pm = getContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hasilJamIsya);
        calendar.set(Calendar.MINUTE, hasilMenitIsya);

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 4, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifShubuh() {
        Uri alertSound = Uri.parse("android.resource://"
                + getContext().getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Waktu sholat shubuh telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(getContext(), CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifDzuhur() {
        Uri alertSound = Uri.parse("android.resource://"
                + getContext().getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Waktu sholat dzuhur telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(getContext(), CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifAshar() {
        Uri alertSound = Uri.parse("android.resource://"
                + getContext().getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Waktu sholat ashar telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(getContext(), CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 2, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifMaghrib() {
        Uri alertSound = Uri.parse("android.resource://"
                + getContext().getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Waktu sholat maghrib telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(getContext(), CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 3, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotifIsya() {
        Uri alertSound = Uri.parse("android.resource://"
                + getContext().getPackageName() + "/" + R.raw.suaraadzan);

        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Waktu sholat isya telah tiba");
        builder.setContentText("Tinggalkan kesibukanmu dan sholatlah");
        builder.setSmallIcon(R.drawable.ic_polymer);
        builder.setSound(alertSound);

        Intent notificationIntent = new Intent(getContext(), CatatWaktuSholatActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 4, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

}
