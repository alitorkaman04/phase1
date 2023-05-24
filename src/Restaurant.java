import java.util.ArrayList;

public class Restaurant implements Comparable<Restaurant>{
    private String name;
    private int id;
    private Owner owner;
    private int location;
    private static Restaurant selectedRestaurant;
    private String foodType;
    private ArrayList<Food> menu;
    private ArrayList<Order> orders;

    final private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    public Restaurant(String name, Owner owner, int location, String foodType) {
        this.name = name;
        id = restaurants.size();
        this.location = location;
        this.owner = owner;
        this.foodType = foodType;
        menu = new ArrayList<>();
        orders = new ArrayList<>();
        selectedRestaurant = null;
    }
    static void addNewRestaurant(String name, int location, String foodType) {
        if(Owner.getLoggedInOwner() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            restaurants.add(new Restaurant(name, Owner.getLoggedInOwner(), location, foodType));
            Owner.getLoggedInOwner().getRestaurants().add(restaurants.get(restaurants.size()-1));
        }
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

    Food getFood(int id) {
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

    public static void deactiveFood(int id) {
        if(Owner.getLoggedInOwner() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Owner.getLoggedInOwner().getSelectedRestaurant().getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
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

    @Override
    public int compareTo(Restaurant restaurant) {
        if(this.name.compareTo(restaurant.name) != 0)
            return this.name.compareTo(restaurant.name);
        else
            return this.id-restaurant.id;
    }
}
