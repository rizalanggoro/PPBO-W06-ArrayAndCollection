/*
  file logic
  berguna sebagai logika bisnis dari program

 */

import java.util.*;

public class Logic {
  // konstanta PPN
  public static final int PPN = 10;

  // map untuk menampung daftar smartphone
  private final Map<String, Smartphone> mapSmartphones = new HashMap<>();

  // array list untuk menampung daftar riwayat transaksi
  private final ArrayList<Receipt> arrayListReceiptHistory = new ArrayList<>();

  // constructor untuk menginisialisasi map smartphone
  public Logic() {
    initializeSmartphones();
  }

  // fungsi logika untuk menambahkan smartphone
  public void addSmartphone(String input) throws Exception {
    String invalidInputMessage = "Masukan yang Anda berikan tidak valid!";

    // validasi input dari user
    if (input.trim().isEmpty() || !input.contains(","))
      throw new Exception(invalidInputMessage);

    String[] inputs = input.trim().split(",");
    if (inputs.length != 3)
      throw new Exception(invalidInputMessage);

    // validasi input: nama smartphone
    String smartphoneName = inputs[0];
    if (smartphoneName.trim().isEmpty())
      throw new Exception("Nama smartphone yang Anda berikan tidak valid!");

    // validasi input: harga dan stok smartphone
    int smartphonePrice;
    int smartphoneStock;
    try {
      smartphonePrice = Integer.parseInt(inputs[1].trim());
      smartphoneStock = Integer.parseInt(inputs[2].trim());
    } catch (Exception e) {
      throw new Exception("Harga atau jumlah smartphone yang Anda berikan tidak valid!");
    }

    // validasi key smartphone
    // mengecek apakah sudah ada smartphone dengan nama/key
    // yang akan ditambahkan
    String smartphoneKey = Utils.Format.key(smartphoneName);
    // jika sudah ada, maka lempar sebuah exception
    if (this.mapSmartphones.containsKey(smartphoneKey))
      throw new Exception("Sudah ada smartphone dengan nama ini!");

    // jika belum ada, maka tambahkan object baru ke dalam map
    // smartphone
    this.mapSmartphones.put(smartphoneKey, new Smartphone(
        smartphoneName, smartphonePrice, smartphoneStock
    ));
  }

  // fungsi logika untuk melakukan restock smartphone
  public void restockSmartphone(String input) throws Exception {
    String invalidInputMessage = "Masukan yang Anda berikan tidak valid!";

    // validasi input dari user
    if (input.trim().isEmpty() || !input.contains(","))
      throw new Exception(invalidInputMessage);

    String[] inputs = input.trim().split(",");
    if (inputs.length != 2)
      throw new Exception(invalidInputMessage);

    // validasi input: nama smartphone
    String smartphoneName = inputs[0];
    if (smartphoneName.trim().isEmpty())
      throw new Exception("Nama smartphone yang Anda berikan tidak valid!");

    // validasi input: jumlah smartphone
    int smartphoneAmount;
    try {
      smartphoneAmount = Integer.parseInt(inputs[1].trim());
    } catch (Exception e) {
      throw new Exception("Jumlah smartphone yang Anda berikan tidak valid!");
    }

    // validasi nama smartphone
    String smartphoneKey = Utils.Format.key(smartphoneName);
    // jika nama smartphone tidak ditemukan, lempar sebuah exception
    if (!this.mapSmartphones.containsKey(smartphoneKey))
      throw new Exception("Nama smartphone tidak ditemukan!");

    // jika ditemukan, tambahkan stock smartphone saat ini
    // dengan stock yang dimasukkan oleh user
    int currentSmartphoneStock = this.mapSmartphones.get(smartphoneKey).getStock();
    this.mapSmartphones.get(smartphoneKey).setStock(currentSmartphoneStock + smartphoneAmount);
  }

  // fungsi logika untuk menghapus smartphone
  public void deleteSmartphone(String input) throws Exception {
    String invalidInputMessage = "Masukan yang Anda berikan tidak valid!";

    // validasi input dari user
    if (input.trim().isEmpty())
      throw new Exception(invalidInputMessage);

    // validasi key smartphone
    String smartphoneKey = Utils.Format.key(input);

    // jika key smartphone tidak ditemukan, lempar sebuah exception
    if (!this.mapSmartphones.containsKey(smartphoneKey))
      throw new Exception("Nama smartphone tidak ditemukan!");

    // jika ditemukan, maka hapus entri dengan key smartphone
    // tersebut
    this.mapSmartphones.remove(smartphoneKey);
  }

  // fungsi logika untuk melakukan pemesanan smartphone
  public void createOrder(
      Map<String, Integer> mapOrder,
      String input
  ) throws Exception {
    String invalidInputMessage = "Masukan yang Anda berikan tidak valid!";

    // validasi input dari user
    if (input.trim().isEmpty() || !input.contains(","))
      throw new Exception(invalidInputMessage);

    String[] inputs = input.trim().split(",");
    if (inputs.length != 2)
      throw new Exception(invalidInputMessage);

    // validasi input: nama smartphone
    String smartphoneName = inputs[0];
    if (smartphoneName.trim().isEmpty())
      throw new Exception("Nama smartphone yang Anda berikan tidak valid!");

    // validasi input: jumlah smartphone
    int smartphoneAmount;
    try {
      smartphoneAmount = Integer.parseInt(inputs[1].trim());
    } catch (Exception e) {
      throw new Exception("Jumlah smartphone yang Anda berikan tidak valid!");
    }

    // validasi nama/key smartphone
    String smartphoneKey = Utils.Format.key(smartphoneName);
    if (!this.mapSmartphones.containsKey(smartphoneKey))
      throw new Exception("Nama smartphone tidak ditemukan!");

    // validasi stock smartphone
    if (mapOrder.containsKey(smartphoneKey)) {
      // jika smartphone sudah di order

      // validasi jumlah stock smartphone
      int totalSmartphoneAmount = mapOrder.get(smartphoneKey) + smartphoneAmount;
      if (this.mapSmartphones.get(smartphoneKey).getStock() < totalSmartphoneAmount)
        throw new Exception("Stok smartphone tidak mencukupi!");

      mapOrder.put(smartphoneKey, totalSmartphoneAmount);
    } else {
      // jika smartphone belum di order

      // validasi jumlah stock smartphone
      if (this.mapSmartphones.get(smartphoneKey).getStock() < smartphoneAmount)
        throw new Exception("Stok smartphone tidak mencukupi!");

      mapOrder.put(smartphoneKey, smartphoneAmount);
    }
  }

  // fungsi logika untuk men-generate invoice
  public Invoice createInvoice(
      Map<String, Integer> mapOrder
  ) {
    // menghitung total harga smartphone yang dibeli
    int totalPrice = 0;
    for (Map.Entry<String, Integer> entry : mapOrder.entrySet()) {
      Smartphone smartphone = this.mapSmartphones.get(entry.getKey());
      int amount = entry.getValue();

      totalPrice += smartphone.getPrice() * amount;
    }

    // menghitung ppn
    double totalPPN = (Logic.PPN / 100.0) * totalPrice;

    // mengembalikan sebuah object invoice
    return new Invoice(
        mapOrder,
        totalPrice,
        (int) totalPPN
    );
  }

  // fungsi logika untuk men-generate receipt
  public Receipt createReceipt(
      Invoice invoice,
      int cash
  ) throws Exception {
    // melakukan validasi pembayaran
    int totalPayment = invoice.getTotalPrice() + invoice.getTotalPPN();
    if (cash < totalPayment)
      throw new Exception("Nominal yang Anda berikan tidak mencukupi!");

    // menghitung kembalian yang diterima user
    int cashback = cash - totalPayment;

    // mengurangi jumlah stock smartphone di map smartphone
    for (Map.Entry<String, Integer> entry : invoice.getMapOrder().entrySet()) {
      String smartphoneKey = entry.getKey();
      int smartphoneAmount = entry.getValue();

      int currentStock = this.mapSmartphones.get(smartphoneKey).getStock();
      this.mapSmartphones.get(smartphoneKey).setStock(currentStock - smartphoneAmount);
    }

    // mengembalikan sebuah object receipt sekaligus menambahkannya
    // ke dalam array list history
    Receipt receipt = new Receipt(
        invoice.getMapOrder(),
        invoice.getTotalPrice(),
        invoice.getTotalPPN(),
        cash,
        cashback
    );
    this.arrayListReceiptHistory.add(receipt);

    return receipt;
  }

  // fungsi untuk menginisialisasi daftar smartphone
  // ke dalam map smartphone
  private void initializeSmartphones() {
    Smartphone[] smartphones = {
        new Smartphone("Xiaomi 12T", 5999000, 10),
        new Smartphone("Xiaomi 12", 6999000, 10),
        new Smartphone("Xiaomi 12 Pro", 12999000, 10),
        new Smartphone("Redmi Note 12", 2099000, 10),
        new Smartphone("Redmi Note 12 Pro", 3199000, 10),
        new Smartphone("Poco F5", 4999000, 10),
        new Smartphone("Poco X5", 3799000, 10),
        new Smartphone("Poco M5", 1599000, 10),
    };
    for (Smartphone smartphone : smartphones)
      this.mapSmartphones.put(
          Utils.Format.key(smartphone.getName()),
          smartphone
      );
  }

  // sebagai fungsi getter, untuk mendapatkan list smartphone
  // dalam kondisi terurut berdasarkan nama
  public List<Smartphone> getListSmartphones() {
    List<Smartphone> list = new ArrayList<>(this.mapSmartphones.values());
    list.sort(Comparator.comparing(Smartphone::getName));
    return list;
  }

  // sebagai fungsi getter, untuk mendapatkan history receipt
  public List<Receipt> getListReceiptHistory() {
    return this.arrayListReceiptHistory;
  }

  // sebagai fungsi getter, untuk mendapatkan object smartphone
  // berdasarkan key smartphone
  public Smartphone getSmartphoneByKey(String key) {
    return this.mapSmartphones.get(key);
  }
}
