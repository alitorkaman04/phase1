public enum Rate {
    Like, Dislike;

    Rate() {
    }
    static Rate stringToRate(String rate) {
        if (rate.equals("Like"))
            return Like;
        else
            return Dislike;
    }
//    public static Rate valueOf(String ) {
//
//    }
//    public static Rate[] values() {
//
//    }
}
