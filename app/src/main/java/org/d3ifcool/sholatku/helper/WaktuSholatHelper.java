package org.d3ifcool.sholatku.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.TextView;

import org.d3ifcool.sholatku.constans.VarConstans;
import org.d3ifcool.sholatku.libraries.PrayerTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static org.d3ifcool.sholatku.activity.JadwalkuActivity.MY_PREFS_NAME;

public class WaktuSholatHelper {
    private PrayerTime pray = new PrayerTime();
    private Date date = new Date();
    private Calendar calendar = Calendar.getInstance();
    private Context context;

    private int waktuSaatIni, jmlWaktuShubuh, jmlWaktuTerbit, jmlWaktuTerbenam, jmlWaktuDzuhur, jmlWaktuAshar, jmlWaktuMaghrib, jmlWaktuIsya, jmlBeMidnight, jmlAftMidnight;
    private String jamWaktuShubuh, jamWaktuDzuhur, jamWaktuAshar, jamWaktuMaghrib, jamWaktuIsya;

    private double timezone = (Calendar.getInstance().getTimeZone().getOffset(Calendar.getInstance().getTimeInMillis())) / (1000 * 60 * 60);

    private int offsets [] = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
    private ArrayList prayerTimes, prayerNames;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public WaktuSholatHelper(Context context) {
        this.context = context;
        MethodHelper methodHelper = new MethodHelper();
        this.jmlBeMidnight = (23 * VarConstans.Constans.JAM_KE_DETIK) + (59 * VarConstans.Constans.MENIT_KE_DETIK); // 86.340
        this.jmlAftMidnight = 0; // 0
        setJmlWaktu();
        methodHelper.getSystemRealTime();
        this.waktuSaatIni = methodHelper.getSumWaktuDetik();
    }


    public boolean saatWaktunyaShubuh() {
        return (waktuSaatIni == jmlWaktuShubuh) || (waktuSaatIni < jmlWaktuTerbit);
    }

    public boolean saatWaktunyaDzuhur(){
        return (waktuSaatIni == jmlWaktuDzuhur) || (waktuSaatIni < jmlWaktuAshar);
    }

    public boolean saatWaktunyaAshar(){
        return (waktuSaatIni == jmlWaktuAshar) || (waktuSaatIni < jmlWaktuMaghrib);
    }

    public boolean saatWaktunyaMaghrib(){
        return (waktuSaatIni == jmlWaktuMaghrib) || (waktuSaatIni < jmlWaktuIsya);
    }

    public boolean saatWaktunyaIsyaPagi(){
        return (waktuSaatIni == jmlAftMidnight) || (waktuSaatIni < jmlWaktuShubuh);
    }

    public boolean saatWaktunyaIsyaMalam(){
        return (waktuSaatIni == jmlWaktuIsya) || (waktuSaatIni <= jmlBeMidnight);
    }

    public boolean saatWaktunyaBukan(){
        return (waktuSaatIni == jmlWaktuTerbit) || (waktuSaatIni < jmlWaktuDzuhur);
    }

    public void setJadwalSholat(TextView txt){
        if (saatWaktunyaIsyaPagi()) {
            txt.setText(VarConstans.Constans.ISYA); // isya
        } else if (saatWaktunyaShubuh()) {
            txt.setText(VarConstans.Constans.SHUBUH); // shubuh
        } else if (saatWaktunyaBukan()) {
            txt.setText(VarConstans.Constans.BUKAN_WAKTU_SHOLAT); // bukan waktu shalat
        } else if (saatWaktunyaDzuhur()) {
            txt.setText(VarConstans.Constans.DZUHUR); // dzuhur
        } else if (saatWaktunyaAshar()) {
            txt.setText(VarConstans.Constans.ASHAR); // ashar
        } else if (saatWaktunyaMaghrib()) {
            txt.setText(VarConstans.Constans.MAGHRIB); // maghrib
        } else if (saatWaktunyaIsyaMalam()) {
            txt.setText(VarConstans.Constans.ISYA); // isya
        }  else {
            txt.setText(VarConstans.Constans.BUKAN_WAKTU_SHOLAT);
            txt.setTextSize(20);
        }
    }

    public String getJadwalShalat() {
        if (saatWaktunyaIsyaPagi()){
            return VarConstans.Constans.ISYA; // isya
        }else if (saatWaktunyaShubuh()) {
            return VarConstans.Constans.SHUBUH; // shubuh
        }else if (saatWaktunyaBukan()) {
            return VarConstans.Constans.BUKAN_WAKTU_SHOLAT; // bukan waktu shalat
        } else if (saatWaktunyaDzuhur()) {
            return VarConstans.Constans.DZUHUR; // dzuhur
        } else if (saatWaktunyaAshar()) {
            return VarConstans.Constans.ASHAR; // ashar
        } else if (saatWaktunyaMaghrib()) {
            return VarConstans.Constans.MAGHRIB; // maghrib
        } else if (saatWaktunyaIsyaMalam()) {
            return VarConstans.Constans.ISYA; // isya
        }  else {
            return VarConstans.Constans.BUKAN_WAKTU_SHOLAT; // bukan waktu shalat
        }
    }

    public void CoundownTime(int waktu, final TextView mTextView){
        new CountDownTimer(waktu, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                mTextView.setText("- "+ String.format(VarConstans.Constans.COUNTDOWN,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                mTextView.setText("Saatnya Sholat");
            }

        }.start();
    }

    public void CounupTime(int waktu, final TextView mTextView){
        new CountDownTimer(waktu, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                mTextView.setText(String.format(VarConstans.Constans.FORMAT_COUNTUP,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                mTextView.setText("Waktunya sudah habis");
            }

        }.start();
    }


    private void MethodWaktuShalatHelper(){
        SharedPreferences editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String latitudeString = editor.getString("latitude", "0");
        String longitudeString = editor.getString("longtitude", "0");

        double latitude = Double.valueOf(latitudeString);
        double longitude = Double.valueOf(longitudeString);

        pray.setTimeFormat(pray.Time24);
        pray.setCalcMethod(pray.MWL);
        pray.setAsrJuristic(pray.Shafii);
        pray.setAdjustHighLats(pray.MidNight);
        pray.tune(offsets);

        calendar.setTime(date);
        prayerTimes = pray.getPrayerTimes(calendar, latitude, longitude, timezone);
        prayerNames = pray.getTimeNames();

    }

    private void setJmlWaktu(){
        MethodWaktuShalatHelper();

        for (int i = 0; i < prayerTimes.size(); i++) {
            String tempWaktu = prayerTimes.get(i).toString();
            String hours = tempWaktu.substring(0, 2);
            String minutes = tempWaktu.substring(5, tempWaktu.length());
            int jam = Integer.parseInt(hours);
            int menit = Integer.parseInt(minutes);
            int total = (jam * VarConstans.Constans.JAM_KE_DETIK) + (menit * VarConstans.Constans.MENIT_KE_DETIK);
            if (prayerNames.get(i).equals(VarConstans.Constans.SHUBUH.substring(7))) {
                this.jmlWaktuShubuh = total;
            } else if (prayerNames.get(i).equals(VarConstans.Constans.DZUHUR.substring(7))){
                this.jmlWaktuDzuhur = total;
            } else if (prayerNames.get(i).equals(VarConstans.Constans.ASHAR.substring(7))) {
                this.jmlWaktuAshar = total;
            } else if (prayerNames.get(i).equals(VarConstans.Constans.MAGHRIB.substring(7))) {
                this.jmlWaktuMaghrib = total;
            } else if (prayerNames.get(i).equals(VarConstans.Constans.ISYA.substring(7))) {
                this.jmlWaktuIsya = total;
            }  else if (prayerNames.get(i).equals(VarConstans.Constans.TERBIT)){
                this.jmlWaktuTerbit = total;
            } else if (prayerNames.get(i).equals(VarConstans.Constans.TERBENAM)) {
                this.jmlWaktuTerbenam = total;
            }
        }
    }

    public void setTimeOnline(TextView mTextViewWaktuShubuh, TextView mTextViewWaktuDzuhur,
                              TextView mTextViewWaktuAshar, TextView mTextViewWaktuMaghrib,
                              TextView mTextViewWaktuIsya) {
        MethodWaktuShalatHelper();
        // Menset Waktu Sholat
        for (int i = 0; i < prayerTimes.size(); i++) {
            if (prayerNames.get(i).equals(VarConstans.Constans.SHUBUH.substring(7))) {
                mTextViewWaktuShubuh.setText(prayerTimes.get(i).toString());
                this.jamWaktuShubuh = prayerTimes.get(i).toString();

            } else if (prayerNames.get(i).equals(VarConstans.Constans.DZUHUR.substring(7))){
                mTextViewWaktuDzuhur.setText(prayerTimes.get(i).toString());
                this.jamWaktuDzuhur = prayerTimes.get(i).toString();

            } else if (prayerNames.get(i).equals(VarConstans.Constans.ASHAR.substring(7))) {
                mTextViewWaktuAshar.setText(prayerTimes.get(i).toString());
                this.jamWaktuAshar = prayerTimes.get(i).toString();

            } else if (prayerNames.get(i).equals(VarConstans.Constans.MAGHRIB.substring(7))) {
                mTextViewWaktuMaghrib.setText(prayerTimes.get(i).toString());
                this.jamWaktuMaghrib = prayerTimes.get(i).toString();

            } else if (prayerNames.get(i).equals(VarConstans.Constans.ISYA.substring(7))) {
                mTextViewWaktuIsya.setText(prayerTimes.get(i).toString());
                this.jamWaktuIsya = prayerTimes.get(i).toString();
            }
        }

    }


    public int getJmlWaktuShubuh() {
        return jmlWaktuShubuh;
    }

    public int getJmlWaktuDzuhur() {
        return jmlWaktuDzuhur;
    }

    public int getJmlWaktuAshar() {
        return jmlWaktuAshar;
    }

    public int getJmlWaktuMaghrib() {
        return jmlWaktuMaghrib;
    }

    public int getJmlWaktuIsya() {
        return jmlWaktuIsya;
    }

    public int getJmlBeMidnight() {
        return jmlBeMidnight;
    }

    public int getJmlAftMidnight() {
        return jmlAftMidnight;
    }
}
