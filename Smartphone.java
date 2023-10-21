/*
  class object smartphone
 */

public class Smartphone {
  private final String name;
  private final int price;
  private int stock;

  public Smartphone(
      String name,
      int price,
      int stock
  ) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public String getName() {
    return this.name;
  }

  public int getPrice() {
    return this.price;
  }

  public int getStock() {
    return this.stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
