import java.util.*;

public class Owner{
    private String password;
    private String userName;
    private Restaurant selectedRestaurant;
    private static Owner loggedInOwner;
    private Food selectedFood;
    private ArrayList<Restaurant> restaurants;

    final private static ArrayList<Owner> owners = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private Owner(String password, String userName) {
        this.password = password;
        this.userName = userName;
        selectedRestaurant = null;
        selectedFood = null;
        restaurants = new ArrayList<>();

    }
    static void addNewOwner(String userName, String password) {
        if(loggedInOwner != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getOwner(userName) != null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8)
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            owners.add(new Owner(password, userName));
        }
    }
    static void loginOwner(String userName, String password) {
        if(loggedInOwner != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getOwner(userName) == null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8 || !(getOwner(userName).password.equals(password)))
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            loggedInOwner = getOwner(userName);
            Costumer.setLoggedInCostumer(null);
            loggedInOwner.selectedRestaurant = null;
            loggedInOwner.selectedFood = null;
            inputOutput.printer(CheckResult.SUCCESSFUL);
            showRestaurantsList();
            if(loggedInOwner.restaurants.size() == 1)
                Restaurant.selectRestaurant(loggedInOwner.restaurants.get(0).getId());

        }
    }

    public static void logoutOwner() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner = null;
        }
    }

    public static void selectRestaurant(int id) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Restaurant.getRestaurant(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant = Restaurant.getRestaurant(id);
        }
    }


    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    static Owner getOwner(String userName) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < owners.size(); i++) {
            if(owners.get(i).userName.equals(userName)) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return owners.get(d);
        return null;
    }

    public static void showLocation() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            System.out.println(loggedInOwner.selectedRestaurant.getLocation());
        }
    }
    public static void showFoodType() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            System.out.println(loggedInOwner.selectedRestaurant.getFoodType());
        }
    }
    public static void editLocation(int location) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            loggedInOwner.selectedRestaurant.editLocation(location);
            inputOutput.printer(CheckResult.SUCCESSFUL);
        }
    }
    public static void editFoodType(String foodType) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getOrders().size() != 0)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE?");
            Scanner sc = new Scanner(System.in);
            if(sc.next().equals("YES")){
                inputOutput.printer(CheckResult.SUCCESSFUL);
                loggedInOwner.selectedRestaurant.editFoodType(foodType);
            }
        }
    }
    public static void editFoodName(int id, String name) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getFood(id).setName(name);
        }
    }
    public static void editFoodPrice(int id, double price) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getFood(id).setPrice(price);
        }
    }
    public static void discountFood(int id, int percent, int time) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else if(loggedInOwner.selectedRestaurant.getFood(id).getDiscounts().size() > 0) {
            boolean check = false;
            for (int i = 0; i < loggedInOwner.selectedRestaurant.getFood(id).getDiscounts().size(); i++) {
                if(loggedInOwner.selectedRestaurant.getFood(id).getDiscounts().get(i).isActive()) {
                    check = true;
                    break;
                }
            }
            if(check)
                inputOutput.printer(CheckResult.INVALID_COMMAND);
            else {
                inputOutput.printer(CheckResult.SUCCESSFUL);
                loggedInOwner.selectedRestaurant.getFood(id).getDiscounts().add(new Discount(time, percent));
            }
        }
        else if(percent > 50)
            inputOutput.printer(CheckResult.INVALID_PERCENT);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getFood(id).getDiscounts().add(new Discount(time, percent));
        }
    }

    public static void addNewFood(String name, double price) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            Restaurant.getRestaurants().get(loggedInOwner.selectedRestaurant.getId()-1).getMenu().add(new Food(name, price, loggedInOwner.selectedRestaurant.getId()));
            Food.getFoods().add(new Food(name, price, loggedInOwner.selectedRestaurant.getId()));
        }
    }

    static void deleteFood(int id) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            Restaurant.getRestaurants().get(loggedInOwner.selectedRestaurant.getId()-1).getMenu().remove(loggedInOwner.selectedRestaurant.getFood(id));
            Food.getFoods().remove(loggedInOwner.selectedRestaurant.getFood(id));
        }
    }

    public static void selectMenu() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            for (int i = 0; i < loggedInOwner.selectedRestaurant.getMenu().size(); i++) {
                Food food = loggedInOwner.selectedRestaurant.getMenu().get(i);
                System.out.println(food.getName() + " " + food.getId() + " " + food.getPrice() + " " + food.getDiscounts() + " " + food.isActive());
            }
        }
    }

    public static void showRestaurantsList() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            ArrayList<Restaurant> list = new ArrayList<>(getLoggedInOwner().restaurants);
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getName() + " " + list.get(i).getId());
            }
        }
    }

    public static void addNewResponse(int commentId, String message) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedFood == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedFood.getComments().size() < commentId)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            loggedInOwner.selectedFood.getComments().get(commentId-1).setMessage(message);
        }
    }

    public static void selectFood(int id) {
        if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else
            loggedInOwner.selectedFood = loggedInOwner.selectedRestaurant.getFood(id);
    }

    public static void displayOpenOrders() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            System.out.println(loggedInOwner.selectedRestaurant.getOrders().size());
            for (int i = 0; i < loggedInOwner.selectedRestaurant.getOrders().size(); i++) {
                if(!loggedInOwner.selectedRestaurant.getOrders().get(i).statusSent())
                    System.out.println(loggedInOwner.selectedRestaurant.getOrders().get(i).getUserName() + " " + loggedInOwner.selectedRestaurant.getOrders().get(i).getFoodId() + " " + loggedInOwner.selectedRestaurant.getOrders().get(i).statusSent());
            }
        }
    }

    public static void showOrderHistory() {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            for (int i = 0; i < loggedInOwner.selectedRestaurant.getOrders().size(); i++) {
                System.out.println(loggedInOwner.selectedRestaurant.getOrders().get(i).getUserName() + " " + loggedInOwner.selectedRestaurant.getOrders().get(i).getFoodId() + " " + loggedInOwner.selectedRestaurant.getOrders().get(i).statusSent());
            }
        }
    }

    public static void editOrderStatusSent(int orderId) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getOrder(orderId) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getOrder(orderId).setStatusSent(true);
        }
    }

    public void back() {
        if(selectedFood != null)
            selectedFood = null;
        else if(selectedRestaurant != null)
            selectedRestaurant = null;
        else
            inputOutput.printer(CheckResult.INVALID_COMMAND);
    }

    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant;
    }

    static Owner getLoggedInOwner() {
        return loggedInOwner;
    }

    public String getUserName() {
        return userName;
    }

    public void setSelectedFood(Food selectedFood) {
        this.selectedFood = selectedFood;
    }

    public static ArrayList<Owner> getOwners() {
        return owners;
    }

    public static void setLoggedInOwner(Owner loggedInOwner) {
        Owner.loggedInOwner = loggedInOwner;
    }
}
