import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Food {
    private String name;
    private int id;
    private double price;
    private boolean isActive;
    private Discount discount;
    private ArrayList<Rate> rates;
    private ArrayList<Comment> comments;
    private static Random randNum = new Random();
    private static Set<Integer> set = new LinkedHashSet<Integer>();

    final private static ArrayList<Food> foods = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
        set.add(randNum.nextInt(1000));
        id = (int)set.toArray()[set.size()-1];
        isActive = true;
        discount = null;
        rates = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public static ArrayList<Food> getFoods() {
        return foods;
    }

    public void deactiveFood() {
        isActive = false;
    }
    public void activeFood() {
        isActive = true;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean isActive() {
        return isActive;
    }
    public Discount getDiscount() {
        return discount;
    }
    public double getPrice() {
        return price;
    }

}
