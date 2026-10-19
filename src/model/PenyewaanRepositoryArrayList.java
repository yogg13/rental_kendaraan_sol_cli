package model;

import java.util.ArrayList;
import java.util.List;

//[SRP] satu-satunya tanggung jawab: menyimpan & mencari data Penyewaan di ArrayList. 
public class PenyewaanRepositoryArrayList implements PenyewaanRepository {
        private final List<Penyewaan> data = new ArrayList<>();

        @Override 
        public String buatIdBaru() {
                return String.format("SW-%03d", data.size() +1);
        }

        @Override 
        public void tambah(Penyewaan penyewaan) {
                data.add(penyewaan);
        }

        @Override 
        public List<Penyewaan> semua() {
                return new ArrayList<>(data);
        }

        @Override 
        public Penyewaan cariAktifByPlat(String platNomor) {
                for (Penyewaan p : data) {
                        if (p.isAktif() && p.getKendaraan().getPlatNomor().equalsIgnoreCase(platNomor)) {
                                return p;
                        }
                }
                return null;
        }
}