package setraders.tradingitem;

import java.util.Random;

public class PriceSimulator {
    public static double cryptoPrice;
    public static double forexPrice;
    public static double stockPrice;
    public static Random random = new Random();

    public static void randomGenCrypto() {
        cryptoPrice = 0 + (15000 - 4000) * random.nextDouble();
    }

    public static void randomGenForex() {
        forexPrice = 0 + (4 - 1) * random.nextDouble();
    }

    public static void randomGenStock() {
        stockPrice = 0 + (500 - 200) * random.nextDouble();
    }
}