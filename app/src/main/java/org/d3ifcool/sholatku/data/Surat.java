package org.d3ifcool.sholatku.data;

public class Surat {
    String judul, subjudul;

    public Surat(String judul, String subjudul) {
        this.judul = judul;
        this.subjudul = subjudul;
    }

    public String getJudul() {
        return judul;
    }

    public String getSubjudul() {
        return subjudul;
    }
}
