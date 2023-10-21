// Nama   : Rizal Dwi Anggoro
// NIM    : L0122142
// GitHub : https://github.com/rizalanggoro/PPBO-W06-ArrayAndCollection

/*
  pada program ini digunakan 2 struktur data, yaitu arraylist dan hashmap.
  - arraylist digunakan untuk menampung riwayat receipt/transaksi
    yang telah dilakukan.
  - hashmap digunakan untuk menampung daftar smartphone dan daftar orderan
    smartphone yang dibeli oleh user.

  alasan pemilihan struktur data:
  - arraylist: karena hanya digunakan sebagai riwayat, dimana hanya terdapat
    operasi penambahan, maka arraylist merupakan pilihan yang cocok karena
    setiap riwayat transaksi baru akan ditambahkan pada bagian akhir dari
    arraylist secara dinamis.
  - hashmap: karena digunakan untuk menampung daftar smartphone, dimana terdapat
    beberapa operasi: create, read, update (restock), delete. maka akan lebih mudah
    jika beberapa operasi tersebut didasarkan pada key dibandingkan menggunakan
    index(arraylist) yang harus dilakukan pencarian terlebih dahulu. jika
    menggunakan key, akan sangat mudah untuk mengakses setiap entry dari hashmap.
    namun, jika menggunakan index (arraylist), maka harus dilakukan pencarian
    setiap elemen untuk menemukan index yang tepat.
 */

/*
  file utama
  digunakan sebagai ui / user interface atau sebagai menu
  yang akan beriteraksi dengan user
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PPBO_06_L0122142 {
  public static void main(String[] args) {
    // menginisialisasi object scanner dan logic
    Scanner scanner = new Scanner(System.in);
    Logic logic = new Logic();

    while (true) {
      try {
        // menampilkan main menu sekaligus meminta user untuk memasukkan
        // opsi pilihan
        int option = printMainMenu(scanner, logic);

        // validasi opsi pilihan dari user
        if (option < 1 || option > 6)
          throw new Exception("Opsi yang Anda masukkan tidak valid!");

        // pengkondisian terhadap opsi yang diberikan oleh user
        if (option == 1) createNewOrder(scanner, logic);
        else if (option == 2) addSmartphone(scanner, logic);
        else if (option == 3) restockSmartphone(scanner, logic);
        else if (option == 4) deleteSmartphone(scanner, logic);
        else if (option == 5) statisticStore(scanner, logic);
        else {
          System.out.println("\nKeluar program...");
          break;
        }
      } catch (Exception e) {
        // menangani error ketika opsi yang dimasukkan tidak valid
        System.out.printf("Error: %s\n", e.getMessage());
      }
    }

    // menutup scanner
    scanner.close();
  }

  // fungsi untuk menangani ui statistik toko
  private static void statisticStore(Scanner scanner, Logic logic) {
    System.out.println("\nStatistik Store\n");

    // menghitung total pemasukan dan total ppn
    int totalIncome = 0;
    int totalTaxIncome = 0;
    for (Receipt receipt : logic.getListReceiptHistory()) {
      totalIncome += receipt.getTotalPrice();
      totalTaxIncome += receipt.getTotalPPN();
    }

    // menampilkan total pemasukan dan pajak
    System.out.printf("Pemasukan       : Rp %s\n", Utils.Format.currency(totalIncome));
    System.out.printf("Pemasukan pajak : Rp %s\n", Utils.Format.currency(totalTaxIncome));
    System.out.printf(
        "Total pemasukan : Rp %s\n",
        Utils.Format.currency(totalIncome + totalTaxIncome)
    );

    // menampilkan total transaksi yang sudah terjadi
    System.out.printf("Total transaksi : %d\n", logic.getListReceiptHistory().size());

    Utils.Input.enterToContinue(scanner);
  }

  // fungsi untuk menangai ui hapus smartphone
  private static void deleteSmartphone(Scanner scanner, Logic logic) {
    System.out.println("\nHapus Smartphone\n");

    System.out.println("Petunjuk:");
    System.out.println("Gunakan format berikut untuk menambahkan smartphone:");
    System.out.println("<nama_smartphone>");
    System.out.println("Contoh: ");
    System.out.println("  Xiaomi 12T");
    System.out.println("  xiaomi 12t");

    while (true) {
      try {
        // meminta user untuk memasukkan nama smartphone yang
        // akan dihapus
        String input = Utils.Input.getString(scanner);

        // menghapus smartphone
        logic.deleteSmartphone(input);
        break;
      } catch (Exception e) {
        // menangani error ketika smartphone tidak ditemukan
        System.out.printf("  Error: %s\n", e.getMessage());
      }
    }

    System.out.println("\n$ Berhasil menghapus smartphone...");
    Utils.Input.enterToContinue(scanner);
  }

  // fungsi untuk menangani ui tambah smartphone
  private static void addSmartphone(Scanner scanner, Logic logic) {
    System.out.println("\nTambah Smartphone\n");

    System.out.println("Petunjuk:");
    System.out.println("Gunakan format berikut untuk menambahkan smartphone:");
    System.out.println("<nama_smartphone>, <harga_smartphone>, <jumlah_smartphone>");
    System.out.println("Contoh: ");
    System.out.println("  Xiaomi 12T, 5999000, 3");

    while (true) {
      try {
        // meminta user untuk memasukkan format yang telah ditentukan
        String input = Utils.Input.getString(scanner);

        // menambahkan smartphone sesuai dengan format
        logic.addSmartphone(input);
        break;
      } catch (Exception e) {
        // menangani error yang mungkin terjadi
        System.out.printf("  Error: %s\n", e.getMessage());
      }
    }

    System.out.println("\n$ Berhasil menambahkan smartphone...");
    Utils.Input.enterToContinue(scanner);
  }

  // fungsi untuk menangani ui restock smartphone
  private static void restockSmartphone(Scanner scanner, Logic logic) {
    System.out.println("\nRestok Smartphone\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format berikut untuk melakukan restok:");
    System.out.println("  <nama_smartphone>, <jumlah_smartphone>");
    System.out.println("  Contoh: ");
    System.out.println("    Xiaomi 12T, 3");
    System.out.println("- Gunakan kata kunci \"done\" untuk menyelesaikan");
    System.out.println("  restok.\n");

    while (true) {
      try {
        // meminta user untuk memasukkan input sesuai dengan format
        String input = Utils.Input.getString(scanner);
        if (input.equalsIgnoreCase("done"))
          break;

        // melakukan restock smartphone
        logic.restockSmartphone(input);
      } catch (Exception e) {
        // menangani error yang mungkin terjadi
        System.out.printf("  Error: %s\n", e.getMessage());
      }
    }

    System.out.println("\n$ Berhasil melakukan restok...");
    Utils.Input.enterToContinue(scanner);
  }

  // fungsi untuk menangani ui order smartphone
  private static void createNewOrder(Scanner scanner, Logic logic) {
    System.out.println("\nPesan Smartphone\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format berikut untuk melakukan pemesanan:");
    System.out.println("  <nama_smartphone>, <jumlah_smartphone>");
    System.out.println("  Contoh: ");
    System.out.println("    Xiaomi 12T, 3");
    System.out.println("    xiaomi 12t, 3");
    System.out.println("- Gunakan kata kunci \"done\" untuk menyelesaikan");
    System.out.println("  pemesanan.\n");

    // map untuk menyimpan daftar orderan
    Map<String, Integer> mapOrder = new HashMap<>();

    while (true) {
      try {
        // meminta user untuk memasukkan input sesuai dengan format
        String input = Utils.Input.getString(scanner);
        if (input.equalsIgnoreCase("done"))
          break;

        // menambahkan order ke map order
        logic.createOrder(mapOrder, input);
      } catch (Exception e) {
        // menangani kemungkinan error yang terjadi
        System.out.printf("  Error: %s\n", e.getMessage());
      }
    }

    // menampilkan daftar tagihan yang harus dibayarkan oleh user
    System.out.println("\n$ Membuat tagihan untuk pesanan Anda...\n");
    Invoice invoice = logic.createInvoice(mapOrder);

    Utils.Output.printLine("=", 56);
    System.out.printf(
        " Harga            : Rp %s \n",
        Utils.Format.currency(invoice.getTotalPrice())
    );
    System.out.printf(
        " PPN              : Rp %s \n",
        Utils.Format.currency(invoice.getTotalPPN())
    );
    Utils.Output.printLine("-", 56);
    System.out.printf(
        " Total pembayaran : Rp %s \n",
        Utils.Format.currency(invoice.getTotalPrice() + invoice.getTotalPPN())
    );
    Utils.Output.printLine("=", 56);

    // meminta user untuk memasukkan nominal pembayaran
    Receipt receipt;
    while (true) {
      try {
        System.out.println("\nMasukkan nominal pembayaran");
        int payment = Utils.Input.getInt(scanner);
        receipt = logic.createReceipt(invoice, payment);
        if (receipt != null)
          break;
      } catch (Exception e) {
        // menangani error yang terjadi ketika user melakukan
        // pembayaran
        System.out.printf("  Error: %s\n", e.getMessage());
      }
    }

    System.out.println("\n$ Membuat struk untuk belanjaan Anda...\n");

    // menampilkan struk belanja yang dilakukan oleh user
    Utils.Output.printLine("=", 75);
    System.out.printf(
        " %-24s | %-8s | %-16s | %-16s \n",
        "Nama", "Jumlah", "Satuan", "Harga"
    );
    Utils.Output.printLine("-", 75);

    // menampilkan daftar smartphone yang dibeli oleh user
    for (Map.Entry<String, Integer> entry : receipt.getMapOrder().entrySet()) {
      String smartphoneKey = entry.getKey();
      int smartphoneAmount = entry.getValue();

      final Smartphone smartphone = logic.getSmartphoneByKey(smartphoneKey);
      System.out.printf(
          " %-24s | %8d | Rp%14s | Rp%14s \n",
          smartphone.getName(), smartphoneAmount,
          Utils.Format.currency(smartphone.getPrice()),
          Utils.Format.currency(smartphone.getPrice() * smartphoneAmount)
      );
    }
    Utils.Output.printLine("-", 75);

    // menampilkan total harga, ppn, pembayaran
    System.out.printf(
        " %-56s Rp%14s \n",
        "Total harga",
        Utils.Format.currency(receipt.getTotalPrice())
    );
    System.out.printf(
        " %-56s Rp%14s \n",
        "Total PPN (".concat(String.valueOf(Logic.PPN)).concat("%)"),
        Utils.Format.currency(receipt.getTotalPPN())
    );
    System.out.printf(
        " %-56s Rp%14s \n",
        "Total pembayaran",
        Utils.Format.currency(
            receipt.getTotalPrice() + receipt.getTotalPPN()
        )
    );
    Utils.Output.printLine("-", 75);

    // menampilkan tunai yang dibayar oleh user dan kembalian
    // yang didapatkan
    System.out.printf(
        " %-56s Rp%14s \n",
        "Tunai",
        Utils.Format.currency(receipt.getCash())
    );
    System.out.printf(
        " %-56s Rp%14s \n",
        "Kembali",
        Utils.Format.currency(receipt.getCashback())
    );
    Utils.Output.printLine("=", 75);

    Utils.Input.enterToContinue(scanner);
  }

  // fungsi untuk menampilkan main menu
  private static int printMainMenu(Scanner scanner, Logic logic) {
    System.out.println("\nXiaomi Store\n");
    printSmartphones(logic);

    System.out.println("\nOpsi:");
    System.out.println("1. Pesan smartphone");
    System.out.println("2. Tambah smartphone");
    System.out.println("3. Restok smartphone");
    System.out.println("4. Hapus smartphone");
    System.out.println("5. Statistik store");
    System.out.println("6. Keluar program");

    // meminta user untuk memasukkan opsi pilihan menu
    // lalu dikembalikan dari fungsi ini
    return Utils.Input.getInt(scanner);
  }

  // fungsi untuk menampilkan daftar semua smartphone yang tersedia
  private static void printSmartphones(Logic logic) {
    Utils.Output.printLine("=", 56);
    System.out.printf(
        " %-24s | %-16s | %-8s \n",
        "Nama", "Harga", "Stok"
    );
    Utils.Output.printLine("-", 56);

    for (Smartphone smartphone : logic.getListSmartphones())
      System.out.printf(
          " %-24s | Rp%14s | %8d \n",
          smartphone.getName(),
          Utils.Format.currency(smartphone.getPrice()),
          smartphone.getStock()
      );

    Utils.Output.printLine("=", 56);
  }
}
