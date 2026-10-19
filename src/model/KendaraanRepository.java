package model;

import java.util.List;

public interface KendaraanRepository {
        void tambah(Kendaraan kendaraan);
        List<Kendaraan> semua();
        Kendaraan cariByPlat(String platNomor);
        
}
