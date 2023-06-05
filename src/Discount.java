public class Discount {
    private String code;
    private int timeStamp;
    private int discountPercent;
    private boolean isActive;

    public Discount(int timeStamp, int discountPercent) {
        isActive = true;
        this.timeStamp = timeStamp;
        this.discountPercent = discountPercent;
    }

    public boolean isActive() {
        return isActive;
    }
}
