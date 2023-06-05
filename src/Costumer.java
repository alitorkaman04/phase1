import java.util.ArrayList;
import java.util.Collections;

public class Costumer{
    private String password;
    private String userName;
    private static Costumer loggedInCostumer;
    private Food selectedFood;
    private Restaurant selectedRestaurant;
    private boolean orderHistoryMenu;
    private boolean cartMenu;
    private int location;
    private ArrayList<Food> cart;
    private ArrayList<Order> orders;
    private ArrayList<Order> ordersHistory;
    private ArrayList<Comment> comments;
    private double cash;
    private ArrayList<Discount> discounts;
    final private static ArrayList<Costumer> costumers = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private Costumer(String password, String userName) {
        this.password = password;
        this.userName = userName;
        location = 10;
        cart = new ArrayList<>();
        orders = new ArrayList<>();
        ordersHistory = new ArrayList<>();
        discounts = new ArrayList<>();
        comments = new ArrayList<>();
        loggedInCostumer = null;
        selectedFood = null;
        selectedRestaurant = null;
        orderHistoryMenu = false;
        cartMenu = false;
        cash = 100;
    }
    static void addNewCostumer(String userName, String password) {
        if(loggedInCostumer != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getCostumer(userName) != null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8)
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            costumers.add(new Costumer(password, userName));
        }
    }
    static void loginCostumer(String userName, String password) {
        if(loggedInCostumer != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getCostumer(userName) == null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8 || !(getCostumer(userName).password.equals(password)))
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            loggedInCostumer = getCostumer(userName);
            Owner.setLoggedInOwner(null);
            loggedInCostumer.orderHistoryMenu = false;
            loggedInCostumer.cartMenu = false;
            loggedInCostumer.selectedRestaurant = null;
            loggedInCostumer.selectedFood = null;
            inputOutput.printer(CheckResult.SUCCESSFUL);
            showRestaurantsList();
        }
    }

    public static void logoutCostumer() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInCostumer = null;
        }
    }

    public static void showRestaurantsList() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            ArrayList<Restaurant> list = new ArrayList<>(Restaurant.getRestaurants());
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getName() + " " + list.get(i).getId());
            }
        }
    }

    public static void searchRestaurant(String name) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            ArrayList<Restaurant> list = new ArrayList<>(Restaurant.getRestaurants());
            Collections.sort(list);
            boolean check = false;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getName().equals(name)) {
                    System.out.println(list.get(i).getName() + " " + list.get(i).getId());
                    check = true;
                }
            }
            if(!check)
                System.out.println("NOT_FOUND");
        }
    }

    public static void searchFood(String name) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            ArrayList<Food> list = new ArrayList<>(loggedInCostumer.selectedRestaurant.getMenu());
            boolean check = false;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getName().equals(name)) {
                    System.out.println(list.get(i).getName() + " " + list.get(i).getId());
                    check = true;
                }
            }
            if(!check)
                System.out.println("NOT_FOUND");
        }
    }

    static Costumer getCostumer(String userName) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < costumers.size(); i++) {
            if(costumers.get(i).userName.equals(userName)) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return costumers.get(d);
        return null;
    }

//    public static void selectFood(int id) {
//        if(loggedInCostumer.selectedRestaurant == null)
//            System.out.println(CheckResult.INVALID_COMMAND);
//        else if(loggedInCostumer.selectedRestaurant.getFood(id) == null)
//            System.out.println(CheckResult.ID_ERROR);
//        else
//            loggedInCostumer.selectedFood = loggedInCostumer.selectedRestaurant.getFood(id);
//    }


//    public static void displayOpenOrders() {
//        if(loggedInCostumer == null)
//            System.out.println(CheckResult.INVALID_COMMAND);
//        else if(loggedInCostumer.selectedRestaurant == null)
//            System.out.println(CheckResult.INVALID_COMMAND);
//        else {
//            for (int i = 0; i < loggedInCostumer.orders.size(); i++) {
//                System.out.println(loggedInCostumer.orders.get(i).getFoodId() + " " + loggedInCostumer.orders.get(i).statusSent());
//            }
//        }
//    }

    static Costumer getLoggedInCostumer() {
        return loggedInCostumer;
    }

    public static ArrayList<Costumer> getCostumers() {
        return costumers;
    }

    public int getLocation() {
        return location;
    }

    public Food getSelectedFood() {
        return selectedFood;
    }

    public static void selectRestaurant(int id) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(Restaurant.getRestaurants().size() < id || id < 1)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            loggedInCostumer.selectedRestaurant = Restaurant.getRestaurants().get(id-1);
            for (int i = 0; i < loggedInCostumer.selectedRestaurant.getMenu().size(); i++) {
                System.out.println(loggedInCostumer.selectedRestaurant.getMenu().get(i).getName() + " " + loggedInCostumer.selectedRestaurant.getMenu().get(i).getPrice() + " " + loggedInCostumer.selectedRestaurant.getMenu().get(i).getId());
            }

        }
    }

    public static void selectFood(int id) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant.getFood(id) == null)
            System.out.println("NAME_ERROR");
        else {
            loggedInCostumer.selectedFood = loggedInCostumer.selectedRestaurant.getFood(id);
        }
    }

    public static void displayComments() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.ID_ERROR);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_PERCENT);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.COST_ERROR);
        else if(loggedInCostumer.selectedFood != null)
            for (int i = 0; i < loggedInCostumer.selectedFood.getComments().size(); i++) {
                System.out.println(loggedInCostumer.selectedFood.getComments().get(i).getComment() + " username: " + loggedInCostumer.selectedFood.getComments().get(i).getUserName());
            }
        else
            for (int i = 0; i < loggedInCostumer.selectedRestaurant.getComments().size(); i++) {
                System.out.println(loggedInCostumer.selectedRestaurant.getComments().get(i).getComment() + " username: " + loggedInCostumer.selectedRestaurant.getComments().get(i).getUserName());
            }

    }

    public static void addNewComment(String comment) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null) {
            boolean check = false;
            for (int i = 0; i < loggedInCostumer.ordersHistory.size(); i++) {
                if(loggedInCostumer.ordersHistory.get(i).getFoodId() == loggedInCostumer.selectedFood.getId()) {
                    check = true;
                    break;
                }
            }
            if(check)
                loggedInCostumer.selectedFood.getComments().add(new Comment(loggedInCostumer.userName, comment));
            else
                inputOutput.printer(CheckResult.INVALID_COMMAND);
        }
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInCostumer.selectedRestaurant.getComments().add(new Comment(loggedInCostumer.userName, comment));
        }
    }

    public static void editComment(int id, String comment) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null) {

        }
        else {

        }
    }

    public static void displayRating() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null)
            for (int i = 0; i < loggedInCostumer.selectedFood.getRates().size(); i++) {
                System.out.println(loggedInCostumer.selectedFood.getRates().get(i).getRate() + " username: " + loggedInCostumer.selectedFood.getRates().get(i).getUserName());
            }
        else
            for (int i = 0; i < loggedInCostumer.selectedRestaurant.getRates().size(); i++) {
                System.out.println(loggedInCostumer.selectedRestaurant.getRates().get(i).getRate() + " username: " + loggedInCostumer.selectedRestaurant.getRates().get(i).getUserName());
            }

    }

    public static void submitRate(int rate) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null) {
            boolean check = false;
            for (int i = 0; i < loggedInCostumer.ordersHistory.size(); i++) {
                if(loggedInCostumer.ordersHistory.get(i).getFoodId() == loggedInCostumer.selectedFood.getId()) {
                    check = true;
                    break;
                }
            }
            if(check)
                loggedInCostumer.selectedFood.getRates().add(new Rate(loggedInCostumer.userName, rate));
            else
                inputOutput.printer(CheckResult.INVALID_COMMAND);
        }
        else
            loggedInCostumer.selectedRestaurant.getRates().add(new Rate(loggedInCostumer.userName, rate));
    }

    public static void editRate(int rate) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu || loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood != null)
            loggedInCostumer.selectedFood.getRate(loggedInCostumer.userName).setRate(rate);
        else
            loggedInCostumer.selectedRestaurant.getRate(loggedInCostumer.userName).setRate(rate);
    }

    public static void addFoodToCart() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedFood == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInCostumer.cart.add(loggedInCostumer.selectedFood);
        }
    }

    public static void accessOrderHistory() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            loggedInCostumer.orderHistoryMenu = true;
            for (int i = 0; i < loggedInCostumer.ordersHistory.size(); i++) {
                System.out.println("order " + i + 1);
            }
        }
    }

    public static void selectOrder(int id) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(!loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(id > loggedInCostumer.ordersHistory.size() || id < 1)
            inputOutput.printer(CheckResult.ID_ERROR);
        else
            System.out.println(loggedInCostumer.ordersHistory.get(id-1).getRestaurantName() + " " + Food.getFood(loggedInCostumer.ordersHistory.get(id-1).getFoodId()).getName() + " " + Food.getFood(loggedInCostumer.ordersHistory.get(id-1).getFoodId()).getPrice());
    }

    public static void displayCartStatus() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            loggedInCostumer.cartMenu = true;
            for (int i = 0; i < loggedInCostumer.cart.size(); i++) {
                System.out.println(loggedInCostumer.cart.get(i).getName() + " " + loggedInCostumer.cart.get(i).getPrice());
            }
        }
    }

    public static void confirmOrder() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(!loggedInCostumer.cartMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cash < cartPrice(loggedInCostumer.cart))
            inputOutput.printer(CheckResult.NOT_ENOUGH_CREDIT);
        else {
            for (int i = 0; i < loggedInCostumer.cart.size(); i++) {
                loggedInCostumer.orders.add(new Order(loggedInCostumer.userName, loggedInCostumer.cart.get(i)));
                loggedInCostumer.cash -= loggedInCostumer.cart.get(i).getPrice();
                Restaurant.getRestaurants().get(loggedInCostumer.cart.get(i).getRestaurantId()-1).getOrders().add(new Order(loggedInCostumer.userName, loggedInCostumer.cart.get(i)));
            }

            loggedInCostumer.cart.clear();
            inputOutput.printer(CheckResult.SUCCESSFUL);
        }
    }
    public static double cartPrice(ArrayList<Food> cart) {
        int price = 0;
        for (int i = 0; i < cart.size(); i++) {
            price += cart.get(i).getPrice();
        }
        return price;
    }

    public static void chargeAccount(double cash) {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInCostumer.cash += cash;
        }
    }

    public static void displayAccountCharge() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            System.out.println(loggedInCostumer.cash);
        }
    }



    public void back() {
        if(selectedFood != null)
            selectedFood = null;
        else if(selectedRestaurant != null)
            selectedRestaurant = null;
        else if(cartMenu)
            cartMenu = false;
        else if(orderHistoryMenu)
            orderHistoryMenu = false;
        else
            inputOutput.printer(CheckResult.INVALID_COMMAND);
    }

    public static void showEstimatedDeliveryTime() {
        if(loggedInCostumer == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.selectedRestaurant != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.cartMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInCostumer.orderHistoryMenu)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            int time = 0;
            for (int i = 0; i < loggedInCostumer.orders.size(); i++) {
                if(loggedInCostumer.orders.get(i).getPath().getTime() > time)
                    time = loggedInCostumer.orders.get(i).getPath().getTime();
            }
            System.out.println(time);
        }
    }



    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant;
    }

    public static void setLoggedInCostumer(Costumer loggedInCostumer) {
        Costumer.loggedInCostumer = loggedInCostumer;
    }
}
