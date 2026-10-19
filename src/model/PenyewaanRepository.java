package model;

import java.util.List;

public interface PenyewaanRepository{
        String buatIdBaru();
        void tambah(Penyewaan penyewaan);
        List<Penyewaan> semua();
        Penyewaan cariAktifByPlat(String platNomor);
}
