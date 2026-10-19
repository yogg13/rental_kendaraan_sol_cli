package model;

import java.util.List;

//[SRP] Satu tanggung jawab: membuat objek Kendaraan sesuai jenis yang dipilih.
//[OCP] Pembuatan objek harus terjadi di suatu tempat. Kita kumpulkan di SATU kelas ini,
//      sehingga Controller, View, dan Repository tetap tertutup untuk modifikasi.
public class KendaraanFactoryDefault implements KendaraanFactory{
        public final List<String> JENIS = List.of("MOBIL", "MOTOR", "MINIBUS", "PREMIUM");

        @Override 
        public List<String> daftarJenis() {
                return JENIS;
        }

        public Kendaraan buat(String jenis, String platNomor, String merk, long tarifPerHari) {
                return switch (jenis) {
                case "MOBIL" -> new Mobil(platNomor, merk, tarifPerHari);
                case "MOTOR" -> new Motor(platNomor, merk, tarifPerHari);
                case "MINIBUS" -> new Minibus(platNomor, merk, tarifPerHari);
                case "PREMIUM" -> new MobilPremium(platNomor, merk, tarifPerHari);
                default -> null;   // jenis tidak dikenal
                };
        }
}
