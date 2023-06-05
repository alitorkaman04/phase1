import java.util.ArrayList;

public class Order {
    private int id;
    private String restaurantName;
    private int restaurantLocation;
    private int costumerLocation;
    private Path path;
    private Double deliveryPrices;
    private double foodPrice;
    private String deliveryUserName;
    private int foodId;
    private String userName;
    private boolean statusSent;
    final private static ArrayList<Order> orders = new ArrayList<>();


    public Order(String userName, Food food) {
        restaurantLocation = Restaurant.getRestaurants().get(food.getRestaurantId()-1).getLocation();
        restaurantName = Restaurant.getRestaurants().get(food.getRestaurantId()-1).getName();
        costumerLocation = Costumer.getCostumer(userName).getLocation();
        this.path = new Path(restaurantLocation, costumerLocation);
        foodPrice = food.getPrice();
        foodId = food.getId();
        statusSent = false;
        id = orders.size();/////////////////////////////////////////////////////////////////////////////////////////////
        this.userName = userName;
        deliveryUserName = null;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public boolean statusSent() {
        return statusSent;
    }

    public void setStatusSent(boolean statusSent) {
        this.statusSent = statusSent;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Path getPath() {
        return path;
    }

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    public String getDeliveryUserName() {
        return deliveryUserName;
    }

    public void setDeliveryUserName(String deliveryUserName) {
        this.deliveryUserName = deliveryUserName;
    }
}
