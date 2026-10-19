package model;

/*
[LSP]   MobilPremium bisa menggantikan Kendaraan di mana pun tanpa crash.
        Syarat minimal 3 hari tidak lagi "disembunyikan" lewat exception,
        tetapi diumumkan melalui getMinimalHari() yang memang bagian dari kontrak induk

Pemilik rental ingin menambah Mobil Premium (misalnya Alphard) dengan dua aturan: 
- minimal disewa 3 hari, 
- dan ada asuransi premium tambahan Rp100.000
*/
public class MobilPremium extends Mobil {
        private static final long ASURANSI_PREMIUM = 100000;
        private static final int MINIMAL_HARI = 3;
        private static final long UANG_JAMINAN_PREMIUM = 2000000;

        public MobilPremium(String platNomor, String merk, long tarifPerHari) {
                super(platNomor, merk, tarifPerHari);
        }

        @Override 
        public long getUangJaminan() {
                return UANG_JAMINAN_PREMIUM;
        }

        @Override
        public String getJenis() {
                return "PREMIUM";
        }

        @Override
        public int getMinimalHari() {
                return MINIMAL_HARI;
        }

        //[LSP] hasil selalu biaya > 0 sesuai kontrak induk.
        @Override
        public long hitungBiaya(int hari) {
                return super.hitungBiaya(hari) + ASURANSI_PREMIUM;
        }
}
