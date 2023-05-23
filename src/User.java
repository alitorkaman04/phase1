import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String password;
    private String userName;
    private static User loggedInUser;
    private ArrayList<App> cart;
    private ArrayList<App> downloads;
    private int credit;
    final private static ArrayList<User> users = new ArrayList<>();
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private User(String password, String userName) {
        this.password = password;
        this.userName = userName;
        cart = new ArrayList<>();
        downloads = new ArrayList<>();
        loggedInUser = null;
        credit = 100;
    }

    static void addNewUser(String password, String userName) {
        if(loggedInUser != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getUser(userName) != null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8)
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            users.add(new User(password, userName));
        }
    }

    static void loginUser(String userName, String password) {
        if(loggedInUser != null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else if(getUser(userName) == null || userName.length() < 8)
            inputOutput.printer(CheckResult.USER_NAME_ERROR);
        else if(password.length() < 8 || !getUser(userName).password.equals(password))
            inputOutput.printer(CheckResult.PASSWORD_ERROR);
        else {
            loggedInUser = getUser(userName);
            inputOutput.printer(CheckResult.SUCCESSFUL);
        }
    }

    public static void logoutUser() {
        if(loggedInUser == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInUser = null;
        }
    }

    static User getUser(String userName) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).userName.equals(userName)) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return users.get(d);
        return null;
    }

    void byCart() {
        int mablagh = 0;
        for (int i = 0; i < cart.size(); i++) {
            mablagh += cart.get(i).getTaxCalculatedCost();
        }
        if(credit < mablagh)
            inputOutput.printer(CheckResult.NOT_ENOUGH_CREDIT);
        else {
            credit -= mablagh;
            inputOutput.printer(CheckResult.SUCCESSFUL);
            for (int i = 0; i < cart.size(); i++) {
                downloads.add(cart.get(i));
                cart.get(i).getDeveloper().credit += cart.get(i).getCost();
            }
            cart.clear();
        }
    }

    boolean isAppDownloaded(App app) {
        for (int i = 0; i < downloads.size(); i++) {
            if(app.equals(downloads.get(i)))
                return true;
        }
        return false;
    }

    void addAppToCart(App app) {
        if(app.getDeveloper().userName.equals(userName) || loggedInUser == null || isAppDownloaded(app))
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            loggedInUser.cart.add(app);
        }
    }

    void increasCredit(int money) {
        credit += money;
        inputOutput.printer(CheckResult.SUCCESSFUL);
    }

    static User getLoggedInUser() {
        return loggedInUser;
    }

    void printDownloads() {
        for (int i = 0; i < downloads.size(); i++) {
            System.out.println(downloads.get(i).getName() + " - " + downloads.get(i).getCategory() + " - " + downloads.get(i).getTaxCalculatedCost() + " $");
        }
    }

    void printCart() {
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i).getName() + " - " + cart.get(i).getCategory() + " - " + cart.get(i).getTaxCalculatedCost() + " $");
        }
    }

    int getUploadNum() {
        int counter = 0;
        for (int i = 0; i < App.getApps().size(); i++) {
            if(App.getApps().get(i).getDeveloper().userName.equals(userName))
                counter++;
        }
        return counter;
    }

//    static void printUserslist() {
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.get(i).name + " - " + users.get(i).getDownloadNum() + " - " + users.get(i).getUploadNum() + " - " + users.get(i).credit + " $");
//        }
//    }

    int getDownloadNum() {
        return downloads.size();
    }

    public String getUserName() {
        return userName;
    }
}
