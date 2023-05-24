import java.util.ArrayList;

public class Costumer{
    private String password;
    private String userName;
    private static Costumer loggedInCostumer;
    private int location;
    private ArrayList<Food> shopingList;
    private ArrayList<Order> orders;
    private double cash;
    private ArrayList<Discount> discounts;
    final private static ArrayList<Costumer> costumers = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private Costumer(String password, String userName) {
        this.password = password;
        this.userName = userName;
        shopingList = new ArrayList<>();
        orders = new ArrayList<>();
        discounts = new ArrayList<>();
        loggedInCostumer = null;
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
            inputOutput.printer(CheckResult.SUCCESSFUL);

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

    static Costumer getLoggedInCostumer() {
        return loggedInCostumer;
    }

    public static ArrayList<Costumer> getCostumers() {
        return costumers;
    }
}
