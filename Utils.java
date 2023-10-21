/*
  class utilitas
 */

import java.text.DecimalFormat;
import java.util.Scanner;

public class Utils {
  public static class Format {
    // untuk memformat angka
    public static String currency(int num) {
      DecimalFormat decimalFormat = new DecimalFormat("#,###");
      return decimalFormat.format(num);
    }

    // untuk memformat nama smartphone menjadi key smartphone
    public static String key(String str) {
      return str.toLowerCase().trim().replace(" ", "_");
    }
  }

  public static class Output {
    // untuk mencetak garis di console sesuai dengan jumlah `count`
    public static void printLine(String type, int count) {
      for (int a = 0; a < count; a++)
        System.out.print(type);
      System.out.println();
    }
  }

  public static class Input {
    // untuk mendapatkan input string dari console
    public static String getString(Scanner scanner) {
      System.out.print("> ");
      return scanner.nextLine();
    }

    // untuk mendapatkan input int dari console
    public static int getInt(Scanner scanner) {
      int result;
      while (true) {
        try {
          System.out.print("> ");
          result = scanner.nextInt();
          scanner.nextLine();
          break;
        } catch (Exception e) {
          // jika inputan tidak valid int, misal berupa huruf atau lainnya
          // user akan diminta untuk memasukkan ulang input hingga valid
          System.out.println("Error: Masukkan yang Anda berikan tidak valid!");
          scanner.nextLine();
        }
      }
      return result;
    }

    // meminta user menekan enter untuk melanjutkan
    public static void enterToContinue(Scanner scanner) {
      System.out.println("\n$ Tekan <enter> untuk melanjutkan...");
      String a = scanner.nextLine();
    }
  }
}
