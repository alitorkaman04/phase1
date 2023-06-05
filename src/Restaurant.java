import java.util.ArrayList;

public class Restaurant implements Comparable<Restaurant>{
    private String name;
    private int id;
    private String ownerUserName;
    private int location;
    private static Restaurant selectedRestaurant;
    private String foodType;
    private ArrayList<Food> menu;
    private ArrayList<Order> orders;
    private ArrayList<Comment> comments;
    private ArrayList<Rate> rates;

    final private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    public Restaurant(String name, String ownerUserName, int location, String foodType) {
        this.name = name;
        id = restaurants.size();
        this.location = location;
        this.ownerUserName = ownerUserName;
        this.foodType = foodType;
        menu = new ArrayList<>();
        orders = new ArrayList<>();
        comments = new ArrayList<>();
        rates = new ArrayList<>();
        selectedRestaurant = null;
    }
    static void addNewRestaurant(String name, int location, String foodType) {
        if(Owner.getLoggedInOwner() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            restaurants.add(new Restaurant(name, Owner.getLoggedInOwner().getUserName(), location, foodType));
            Owner.getLoggedInOwner().getRestaurants().add(new Restaurant(name, Owner.getLoggedInOwner().getUserName(), location, foodType));
        }
    }

    public void addFood(Food food) {
        menu.add(food);
    }

    public void editLocation(int location) {
        this.location = location;
    }

    public void editFoodType(String foodType) {
        this.foodType = foodType;
        menu.clear();
    }

    public static void selectRestaurant(int id) {
        selectedRestaurant = restaurants.get(id-1);
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getLocation() {
        return location;
    }
    public String getFoodType() {
        return foodType;
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public ArrayList<Food> getMenu() {
        return menu;
    }

    public static Restaurant getRestaurant(int id) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < restaurants.size(); i++) {
            if(restaurants.get(i).getId() == id && restaurants.get(i).getOwnerUserName().equals(Owner.getLoggedInOwner().getUserName())) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return restaurants.get(d);
        return null;
    }

    public Food getFood(int id) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < menu.size(); i++) {
            if(menu.get(i).getId() == id) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return menu.get(d);
        return null;
    }

//    Food getFood(String name) {
//        int d = 0;
//        boolean check = false;
//        for (int i = 0; i < menu.size(); i++) {
//            if(menu.get(i).getName().equals(name)) {
//                d = i;
//                check = true;
//                break;
//            }
//        }
//        if(check)
//            return menu.get(d);
//        return null;
//    }

    public static void deactiveFood(int id) {
        if(Owner.getLoggedInOwner() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant().getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            Owner.getLoggedInOwner().getSelectedRestaurant().getFood(id).deactiveFood();
        }
    }

    public static void activeFood(int id) {
        if(Owner.getLoggedInOwner() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant().getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            Owner.getLoggedInOwner().getSelectedRestaurant().getFood(id).activeFood();
        }
    }

    public Order getOrder(int orderId) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getId() == orderId) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return orders.get(d);
        return null;
    }

    @Override
    public int compareTo(Restaurant restaurant) {
        if(this.name.compareTo(restaurant.name) != 0)
            return this.name.compareTo(restaurant.name);
        else
            return this.id-restaurant.id;
    }

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Rate getRate(String username) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < rates.size(); i++) {
            if(rates.get(i).getUserName().equals(username)) {
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

    public String getOwnerUserName() {
        return ownerUserName;
    }
}
