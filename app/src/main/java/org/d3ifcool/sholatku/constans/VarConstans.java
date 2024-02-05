package org.d3ifcool.sholatku.constans;

public class VarConstans {

    public VarConstans() {
    }

    public static final class Constans {

        // format jam
        public static final int JAM_KE_DETIK = 3600;
        public static final int MENIT_KE_DETIK = 60;
        public static final int DETIK_KE_MILI = 1000;

        // format tampilan timer dan tanggal
        public static final String COUNTDOWN = "%02d : %02d : %02d";
        public static final String FORMAT_COUNTUP = "%02d Jam %02d Menit %02d Detik";
        public static final String DATE = "dd MMMM yyyy";

        //
        public static final String TERBIT = "Matahari Terbit";
        public static final String TERBENAM = "Matahari Terbenam";
        public static final String BUKAN_WAKTU_SHOLAT = "Belum Masuk Waktu Sholat";

        // format nama sholat
        public static final String SHUBUH = "Sholat Shubuh";
        public static final String DZUHUR = "Sholat Dzuhur";
        public static final String ASHAR = "Sholat Ashar";
        public static final String MAGHRIB = "Sholat Maghrib";
        public static final String ISYA = "Sholat Isya";
    }
}
