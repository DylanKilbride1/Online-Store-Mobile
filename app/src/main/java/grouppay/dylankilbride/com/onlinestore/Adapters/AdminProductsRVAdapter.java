package grouppay.dylankilbride.com.onlinestore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.Product;

public class AdminProductsRVAdapter extends RecyclerView.Adapter<AdminProductsRVAdapter.ViewHolder> {

  public List<Product> productList;
  private int itemLayout;
  private Context context;
  private static ItemClickListener clickInterface;

  public AdminProductsRVAdapter(List<Product> productList, int itemLayout) {
    this.productList = productList;
    this.itemLayout = itemLayout;
  }

  public AdminProductsRVAdapter(List<Product> productList, Context context) {
    this.productList = productList;
    this.context = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
    final Product product = productList.get(position);
    viewHolder.productTitle.setText(product.getTitle());
    viewHolder.productManufacturer.setText(product.getManufacturer());
    viewHolder.productPrice.setText(product.getPriceString());
    viewHolder.productStock.setText(product.getStockString());
    viewHolder.addStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        product.setStock(product.getStock() + 1);
        notifyDataSetChanged();
      }
    });
    viewHolder.removeStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        product.setStock(product.getStock() - 1);
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productTitle, productManufacturer, productPrice, productStock;
    public Button addStock, removeStock;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      productTitle = itemView.findViewById(R.id.productTitle);
      productManufacturer = itemView.findViewById(R.id.productManufacturer);
      productPrice = itemView.findViewById(R.id.productPrice);
      productStock = itemView.findViewById(R.id.productStock);
      addStock = itemView.findViewById(R.id.adminAddStockBtn);
      removeStock = itemView.findViewById(R.id.adminRemoveStockBtn);
    }

    @Override
    public void onClick(View view) {

    }
  }
}
