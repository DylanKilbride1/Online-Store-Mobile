package grouppay.dylankilbride.com.onlinestore.models;

public class PaymentReceipt {

    private String success;

    public PaymentReceipt(String success) {
      this.success = success;
    }

    public PaymentReceipt() {

    }

    public String getSuccess() {
      return success;
    }

    public void setSuccess(String success) {
      this.success = success;
    }

  }
