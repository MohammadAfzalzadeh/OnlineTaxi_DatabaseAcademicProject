package Models;

public enum PaymentMethod {
    BY_CREDIT , BY_MONEY;

    @Override
    public String toString() {
        return super.toString() == "BY_CREDIT" ? "اعتباری" : "نقدی";
    }
}
