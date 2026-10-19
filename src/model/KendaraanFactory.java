package model;

import java.util.List;

public interface KendaraanFactory{
        List<String> daftarJenis();
        Kendaraan buat(String jenis, String platNomor, String merk, long tarifPerHari);
}

