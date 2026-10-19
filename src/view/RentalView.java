package view;

import java.util.List;
import model.Kendaraan;
import model.Penyewaan;

public interface RentalView {
        String tampilMenu();
        void tampilDaftarKendaraan(List<Kendaraan> daftar);
        void tampilRiwayat(List<Penyewaan> daftar);
        String bacaTeks(String label);
        long bacaAngka(String label);
        void tampilPesan(String pesan);
        boolean konfirmasiUangJaminan(long nominal);
} 
