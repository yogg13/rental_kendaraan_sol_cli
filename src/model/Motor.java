package model;

//[OCP] Ekstensi dari Kendaraan: aturan diskon motor tinggal di kelasnya sendiri.
public class Motor extends Kendaraan implements WajibSIM {
        private static final int HARI_DISKON = 7;

        public Motor(String platNomor, String merk, long tarifPerHari) {
                super(platNomor, merk, tarifPerHari);
        }

        @Override
        public String getJenis() {
                return "MOTOR";
        }

        @Override
        public long hitungBiaya(int hari) {
                long total = getTarifPerHari() * hari;
                if (hari >= HARI_DISKON) {
                total -= total / 10;   // diskon 10%
                }
                return total;
        }

        @Override 
        public String getGolonganSIM() {
                return "C";
        }
}
