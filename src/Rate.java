public class Rate {
    private int rate;
    private String userName;

    public Rate(String userName, int rate) {
        this.rate = rate;
        this.userName = userName;
    }

    public int getRate() {
        return rate;
    }

    public String getUserName() {
        return userName;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

