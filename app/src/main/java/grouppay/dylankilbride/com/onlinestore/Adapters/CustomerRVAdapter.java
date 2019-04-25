package grouppay.dylankilbride.com.onlinestore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.Customer;

public class CustomerRVAdapter extends RecyclerView.Adapter<CustomerRVAdapter.ViewHolder> {

  public List<Customer> customerList;
  private int itemLayout;
  private Context context;
  private static ItemClickListener clickInterface;

  public CustomerRVAdapter(List<Customer> customerList, int itemLayout) {
    this.customerList = customerList;
    this.itemLayout = itemLayout;
  }

  public CustomerRVAdapter(List<Customer> customerList, Context context) {
    this.customerList = customerList;
    this.context = context;
  }

  @NonNull
  @Override
  public CustomerRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
    return new CustomerRVAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final CustomerRVAdapter.ViewHolder viewHolder, final int position) {
    final Customer customer = customerList.get(position);
    viewHolder.customerEmail.setText(customer.getEmail());
    viewHolder.customerName.setText(customer.getUsername());
  }

  @Override
  public int getItemCount() {
    return customerList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView customerEmail, customerName;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      customerEmail = itemView.findViewById(R.id.adminViewCustomerEmail);
      customerName = itemView.findViewById(R.id.adminViewCustomerName);
    }

    @Override
    public void onClick(View view) {

    }
  }
}
