package org.d3ifcool.sholatku.helper;

import org.d3ifcool.sholatku.constans.VarConstans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MethodHelper {

    private Calendar currentTime;
    private String dateToday, nilai_jam, nilai_menit, nilai_detik,
            nol_jam = "", nol_menit = "",nol_detik = "";

    private int systemJam, systemMenit, systemDetik, sumWaktuDetik, systemYear;

    public MethodHelper() {
        this.currentTime = Calendar.getInstance();
        // set tanggal dengan format date dalam constans
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VarConstans.Constans.DATE);
        this.dateToday = simpleDateFormat.format(currentTime.getTime());
        getSystemRealTime();
        getSumRealTime();
        getSumWaktuDetik();

    }

    public String getDateToday() {
        return dateToday;
    }

    private String getOutputStringTime() {
        return nilai_jam + ":" + nilai_menit;
    }

    private void getPureSystemTime() {
        Calendar cal = Calendar.getInstance();
        systemYear = cal.get(Calendar.YEAR);
        systemJam = cal.get(Calendar.HOUR_OF_DAY);
        systemMenit = cal.get(Calendar.MINUTE);
        systemDetik = cal.get(Calendar.SECOND);
    }

    private void getCekFormat(){
        getPureSystemTime();
        if(systemJam <= 9) {
            nol_jam = "0";
        }

        if(systemMenit <= 9) {
            nol_menit = "0";
        }

        if(systemDetik <= 9) {
            nol_detik = "0";
        }
    }

    public void getSystemTime() {
        getCekFormat();
        nilai_jam = nol_jam + Integer.toString(systemJam);
        nilai_menit = nol_menit + Integer.toString(systemMenit);
        nilai_detik = nol_detik + Integer.toString(systemDetik);
    }

    public void getSystemRealTime(){
        Thread p = new Thread(){
            public void run(){
                for(;;){
                    getSystemTime(); // Panggil waktu dari System
                    getOutputStringTime(); // Panggil String waktu;
                    try {
                        sleep(1000);
                    }catch (InterruptedException ex){

                    }
                }
            }
        };
        p.start();
    }

    public void getSumRealTime() {
        Thread p = new Thread(){
            public void run(){
                for(;;){
                    getPureSystemTime();
                    try {
                        sleep(1000);
                    }catch (InterruptedException ex){

                    }
                }
            }
        };
        p.start();
    }

    public int getSumWaktuDetik() {
        getSumRealTime();
        sumWaktuDetik = (systemJam * VarConstans.Constans.JAM_KE_DETIK) + (systemMenit * VarConstans.Constans.MENIT_KE_DETIK) + systemDetik;
        return sumWaktuDetik;
    }
}
