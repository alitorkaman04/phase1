import java.util.ArrayList;

public class Delivery {
    private String password;
    private String userName;
    private int location;
    private static Delivery loggedInDelivery;
    private Order selectedOrder;
    final private static ArrayList<Delivery> deliveries = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    public Delivery(String password, String userName) {
        this.password = password;
        this.userName = userName;
        this.location = 10;
        selectedOrder = null;
        loggedInDelivery = null;
    }

    static void addNewDelivery(String userName, String password) {
        if(loggedInDelivery != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getDelivery(userName) != null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8)
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            deliveries.add(new Delivery(password, userName));
        }
    }
    static void loginDelivery(String userName, String password) {
        if(loggedInDelivery != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getDelivery(userName) == null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8 || !(getDelivery(userName).password.equals(password)))
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            loggedInDelivery = getDelivery(userName);
            Owner.setLoggedInOwner(null);
            loggedInDelivery.selectedOrder = null;
            inputOutput.printer(CheckResult.SUCCESSFUL);
            showOrdersList();
        }
    }

    public static void logoutDelivery() {
        if(loggedInDelivery == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInDelivery = null;
        }
    }

    public static void showOrdersList() {
        if(loggedInDelivery == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            for (int i = 0; i < Order.getOrders().size(); i++) {
                if(Order.getOrders().get(i).getDeliveryUserName() == null)
                    System.out.println(Order.getOrders().get(i).getPath().getLocation1() + " " + Order.getOrders().get(i).getPath().getLocation2() + " " + Order.getOrders().get(i).getPath().getTime());
            }
        }
    }

    static Delivery getDelivery(String userName) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < deliveries.size(); i++) {
            if(deliveries.get(i).userName.equals(userName)) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return deliveries.get(d);
        return null;
    }


    public static void selectOrder(int orderId) {
        if(loggedInDelivery == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(loggedInDelivery.selectedOrder != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(orderId > Order.getOrders().size() || orderId < 1)
            inputOutput.printer(CheckResult.ID_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInDelivery.selectedOrder = Order.getOrders().get(orderId - 1);
            Order.getOrders().get(orderId - 1).setDeliveryUserName(loggedInDelivery.userName);
        }

    }

    public static ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }
}
