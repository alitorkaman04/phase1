public class Comment {
    private User user;
    private String comment;
    private Rate rate;

    public Comment(User user, String comment, Rate rate) {
        this.user = user;
        this.comment = comment.substring(1,comment.length()-1);
        this.rate = rate;
    }
    public User getUser() {
        return user;
    }
    public Rate getRate() {
        return rate;
    }
    public String getComment() {
        return comment;
    }

}
