import java.util.*;

public class Owner{
    private String password;
    private String userName;
    private Restaurant selectedRestaurant;
    private static Owner loggedInOwner;
    private ArrayList<Restaurant> restaurants;

    final private static ArrayList<Owner> owners = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private Owner(String password, String userName) {
        this.password = password;
        this.userName = userName;
        selectedRestaurant = null;
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
        else if(loggedInOwner.getRestaurant(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant = loggedInOwner.getRestaurant(id);
        }
    }

    Restaurant getRestaurant(int id) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < restaurants.size(); i++) {
            if(restaurants.get(i).getId() == id) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return restaurants.get(d);
        return null;
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

    static void addNewFood(String name, double price) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getMenu().add(new Food(name, price));
            Food.getFoods().add((loggedInOwner.selectedRestaurant.getMenu().get(loggedInOwner.selectedRestaurant.getMenu().size()-1)));
        }
    }

    static void deleteFood(int id) {
        if(loggedInOwner == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInOwner.selectedRestaurant.getFood(id) == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInOwner.selectedRestaurant.getMenu().remove(loggedInOwner.selectedRestaurant.getFood(id));
            Food.getFoods().remove(loggedInOwner.selectedRestaurant.getFood(id));
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

    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant;
    }

    static Owner getLoggedInOwner() {
        return loggedInOwner;
    }

    public static ArrayList<Owner> getOwners() {
        return owners;
    }
}
