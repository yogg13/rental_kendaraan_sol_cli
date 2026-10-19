package model;

//[OCP] Ekstensi dari Kendaraan: aturan biaya mobil tinggal di kelasnya sendiri.
public class Mobil extends Kendaraan implements WajibSIM, WajibJaminan {
        private static final long ASURANSI = 50000;
        private static final long UANG_JAMINAN = 500000;

        public Mobil(String platNomor, String merk, long tarifPerHari) {
                super(platNomor, merk, tarifPerHari);
        }

        @Override
        public String getJenis() {
                return "MOBIL";
        }

        @Override
        public long hitungBiaya(int hari) {
                return getTarifPerHari() * hari + ASURANSI;
        }

        @Override 
        public String getGolonganSIM() {
                return "A";
        }

        @Override 
        public long getUangJaminan() {
                return UANG_JAMINAN;
        }
}
