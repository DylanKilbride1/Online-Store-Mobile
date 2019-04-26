package grouppay.dylankilbride.com.onlinestore.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

  private List<Product> products = new ArrayList<>();

  public Cart() {

  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public void addProductToCart(Product product) {
    products.add(product);
  }
}
