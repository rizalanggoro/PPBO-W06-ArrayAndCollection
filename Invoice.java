/*
  class object invoice
  digunakan sebagai objek untuk menyimpan total harga
  dan total ppn yang harus dibayarkan oleh user
 */

import java.util.Map;

public class Invoice {
  private final Map<String, Integer> mapOrder;
  private final int totalPrice;
  private final int totalPPN;

  public Invoice(
      Map<String, Integer> mapOrder,
      int totalPrice,
      int totalPPN
  ) {
    this.mapOrder = mapOrder;
    this.totalPrice = totalPrice;
    this.totalPPN = totalPPN;
  }

  public Map<String, Integer> getMapOrder() {
    return this.mapOrder;
  }

  public int getTotalPrice() {
    return this.totalPrice;
  }

  public int getTotalPPN() {
    return this.totalPPN;
  }
}
