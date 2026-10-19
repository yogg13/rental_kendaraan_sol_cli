package controller;

import model.Kendaraan;
import model.Penyewaan;
import model.KendaraanRepository;
import model.PenyewaanRepository;
import model.WajibJaminan;
import model.WajibSIM;
import view.RentalView;
import view.StrukView;
import java.util.List;
import model.KendaraanFactory;

//[SRP] Satu tanggung jawab: mengatur alur aplikasi
//menerima pilihan dari View, memvalidasi, memanggil Model, mengirim hasil ke View.
//Tidak menyimpan data sendiri, tidak memformat struk, tidak menghitung tarif. 
//[DIP] Controller adalah modul tingkat tinggi (mengatur alur aplikasi)
//      semua import di atas hanya interface dan entitas model
//      tidak ada satupun kelas implementasi.
public class RentalController {
        private final KendaraanRepository kendaraanRepo;
        private final PenyewaanRepository penyewaanRepo;
        private final RentalView view;
        private final StrukView struktView;
        private final KendaraanFactory kendaraanFactory;

        public RentalController(
                KendaraanRepository kendaraanRepository,
                PenyewaanRepository penyewaanRepository,
                RentalView view,
                StrukView strukView,
                KendaraanFactory kendaraanFactory
        ) {
                this.kendaraanRepo = kendaraanRepository;
                this.penyewaanRepo = penyewaanRepository;
                this.view = view;
                this.struktView = strukView;
                this.kendaraanFactory = kendaraanFactory;
        }

        public void tambahDataAwal(Kendaraan kendaraan) {
                kendaraanRepo.tambah(kendaraan);
        }

        public void jalankan() {
                while (true) {
                        String pilihan = view.tampilMenu();
                        switch (pilihan) {
                                case "1" -> view.tampilDaftarKendaraan(kendaraanRepo.semua());
                                case "2" -> tambahKendaraan();
                                case "3" -> sewaKendaraan();
                                case "4" -> kembalikanKendaraan();
                                case "5" -> view.tampilRiwayat(penyewaanRepo.semua());
                                case "0" -> {
                                        view.tampilPesan("Thank You.");
                                        return;
                                }
                                default -> view.tampilPesan("Menu tidak ada.");
                        }
                }
        }

        public void tambahKendaraan() {
                String plat = view.bacaTeks("Plat Nomor");
                if (plat.isEmpty() || kendaraanRepo.cariByPlat(plat) != null) {
                        view.tampilPesan("Plat nomor kosong atau sudah terdaftar.");
                        return;
                }
                String merk = view.bacaTeks("Merk");
                // [OCP] Daftar jenis diambil dari Factory, bukan ditulis manual di Controller.
                String jenis = view.bacaTeks("Jenis (" + String.join("/", kendaraanFactory.daftarJenis()) + ")").toUpperCase();
                // if (!List.of(kendaraanFactory.daftarJenis()).contains(jenis)) {
                //         view.tampilPesan("Jenis tidak dikenal.");
                //         return;
                // }
                if (!kendaraanFactory.daftarJenis().contains(jenis)) {
                        view.tampilPesan("Jenis tidak dikenal.");
                        return;
                }
                long tarif = view.bacaAngka("Tarif per hari");
                if (tarif <= 0) {
                        view.tampilPesan("Tarif harus lebih dari 0.");
                        return;
                }
                kendaraanRepo.tambah(kendaraanFactory.buat(jenis, plat, merk, tarif));
                view.tampilPesan("Kendaraan berhasil ditambahkan.");
        }

        public void sewaKendaraan() {
                String plat = view.bacaTeks("Plat Nomor");
                Kendaraan kendaraan = kendaraanRepo.cariByPlat(plat);
                if (kendaraan == null || !kendaraan.isTersedia()) {
                        view.tampilPesan("Kendaraan tidak ditemukan atau sedang disewa.");
                        return;
                }
                String nama = view.bacaTeks("Nama Penyewa");

                if (kendaraan instanceof WajibSIM wajibSIM) {
                        String sim = view.bacaTeks("Golongan SIM Penyewa");
                        if (!sim.equalsIgnoreCase(wajibSIM.getGolonganSIM())) {
                                view.tampilPesan(
                                        kendaraan.getMerk() 
                                        + " Wajib SIM "
                                        + wajibSIM.getGolonganSIM() + "."
                                );
                                return;
                        }
                }

                int hari = (int) view.bacaAngka("Lama sewa (hari)");

                if (kendaraan instanceof WajibJaminan wajibJaminan) {
                        if (!view.konfirmasiUangJaminan(wajibJaminan.getUangJaminan())) {
                                view.tampilPesan("Penyewaan dibatalkan.");
                                return; 
                        }
                }
                //[LSP] Validasi memakai kontrak kelas induk, BUKAN instanceof MobilPremium.
                //      Berlaku sama untuk semua jenis, termasuk jenis yang dibuat nanti.
                if (hari < kendaraan.getMinimalHari()) {
                        view.tampilPesan(kendaraan.getMerk() + " minimal disewa "
                                + kendaraan.getMinimalHari() + " hari.");
                        return;
                }
                
                //[OCP] Controller tidak tahu dan tidak perlu tahu jenis kendaraannya.
                //      Polimorfisme memilih hitungBiaya() milik subclass yang tepat.
                long total = kendaraan.hitungBiaya(hari);
                Penyewaan sewa = new Penyewaan(penyewaanRepo.buatIdBaru(), nama, kendaraan, hari, total);
                penyewaanRepo.tambah(sewa);
                kendaraan.setTersedia(false);
                struktView.cetak(sewa);
        }

        public void kembalikanKendaraan() {
                String plat = view.bacaTeks("Plat Nomor");
                Penyewaan sewa = penyewaanRepo.cariAktifByPlat(plat);
                if (sewa == null) {
                        view.tampilPesan("Tidak ada penyewaan.");
                        return;
                }
                sewa.selesai();
                sewa.getKendaraan().setTersedia(true);
                view.tampilPesan("Kendaraan " + sewa.getKendaraan().getMerk() + " telah dikembalikan.");
        }
}
