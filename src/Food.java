import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Food {
    private String name;
    private int restaurantId;
    private int id;
    private double price;
    private boolean isActive;
    private ArrayList<Discount> discounts;
    private ArrayList<Rate> rates;
    private ArrayList<Comment> comments;
    private static Food selectedFood;
    private static Random randNum = new Random();
    private static Set<Integer> set = new LinkedHashSet<Integer>();

    final private static ArrayList<Food> foods = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    public Food(String name, double price, int restaurantId) {
        this.name = name;
        this.price = price;
        set.add(randNum.nextInt(1000));
        id = (int)set.toArray()[set.size()-1];
        isActive = true;
        discounts = new ArrayList<>();
        rates = new ArrayList<>();
        comments = new ArrayList<>();
        selectedFood = null;
        this.restaurantId = restaurantId;
    }

    public static ArrayList<Food> getFoods() {
        return foods;
    }

    public static Food getFood(int id) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < foods.size(); i++) {
            if(foods.get(i).id == id) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return foods.get(d);
        return null;
    }



    public static void displayRatings() {
        if(selectedFood == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            for (int i = 0; i < selectedFood.rates.size(); i++) {
                System.out.println(selectedFood.rates.get(i).getRate());
            }
        }
    }
    public static void displayComments() {
        if(selectedFood == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            for (int i = 0; i < selectedFood.comments.size(); i++) {
                System.out.println(selectedFood.comments.get(i).getComment());
            }
        }
    }

    public ArrayList<Comment> getComments() {
        return comments;
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
    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }
    public double getPrice() {
        return price;
    }
    public static Food getSelectedFood() {
        return selectedFood;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public Rate getRate(String username) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < rates.size(); i++) {
            if(rates.get(i).getUserName() == username) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return rates.get(d);
        return null;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }


    //    public static void setSelectedFood(int id) {
//        if(getFood(id) == null)
//            System.out.println(CheckResult.ID_ERROR);
//        else {
//            selectedFood = getFood(id);
//            if(Owner.getLoggedInOwner().getUserName().equals(getFood(id).ownerUserName)) {
//                Owner.getLoggedInOwner().setSelectedFood(getFood(id));
//            }
//            else
//                Costumer.getLoggedInCostumer().setSelectedFood(getFood(id));
//        }
//    }
}
