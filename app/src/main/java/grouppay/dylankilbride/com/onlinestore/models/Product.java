package grouppay.dylankilbride.com.onlinestore.models;

public class Product {

  private String manufacturer;
  private String title;
  private double price;
  private int stock;

  public Product(String manufacturer, String title, double price, int stock) {
    this.manufacturer = manufacturer;
    this.title = title;
    this.price = price;
    this.stock = stock;
  }

  public Product() {
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPrice() {
    return price;
  }

  public String getPriceString() {
    return "€" + price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getStockString() {
    if (stock != 0) {
      return stock + " In Stock";
    } else {
      return "Out of Stock!";
    }
  }

  public int getStock(){
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
