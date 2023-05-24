import java.util.ArrayList;

public class App {
    private static ArrayList<App> apps = new ArrayList<>();
    private ArrayList<Comment> comments;
    private static double tax;
    private User developer;
    private int cost;
    private Category category;
    private String name;
    private static InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();

    private App(User developer, int cost, Category category, String name) {
        this.developer = developer;
        this.cost = cost;
        this.category = category;
        this.name = name;
        comments = new ArrayList<>();
        tax = .1;
    }
    static void rateApp(Comment comment, String name) {
        boolean check = false;
        int d = 0;
        for (int i = 0; i < getApp(name).comments.size(); i++) {
            if(getApp(name).comments.get(i).getUser().equals(comment.getUser())) {
                check = true;
                d = i;
            }
        }
        if(check)
            getApp(name).comments.remove(d);

        getApp(name).comments.add(comment);
        inputOutput.printer(CheckResult.SUCCESSFUL);

    }
    public static ArrayList<App> getApps() {
        return apps;
    }
    static void newApp(String name, int cost, Category category) {
        if(User.getLoggedInUser() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
//        else if(name.length() < 5 || getApp(name) != null)
//            inputOutput.printer(CheckResult.NAME_ERROR);
        else if(cost < 0)
            inputOutput.printer(CheckResult.COST_ERROR);
        else {
            apps.add(new App(User.getLoggedInUser(), cost, category, name));
            inputOutput.printer(CheckResult.SUCCESSFUL);
        }
    }

    public static void setTax(double t) {
        tax = t/100.0;
        inputOutput.printer(CheckResult.SUCCESSFUL);
    }

//    public static void printComments(String name) {
//        if(getApp(name) == null)
//            inputOutput.printer(CheckResult.INVALID_COMMAND);
//        else {
//            for (int i = 0; i < getApp(name).comments.size(); i++) {
//                System.out.println(getApp(name).comments.get(i).getUser().getName() + " - " + getApp(name).comments.get(i).getRate() + " - " + getApp(name).comments.get(i).getComment());
//            }
//        }
//    }

    public int getTaxCalculatedCost() {
        double cash = 0;
        cash = cost + tax * cost;
        return (int) Math.ceil(cash);
    }
    static App getApp(String name) {
        int d = 0;
        boolean check = false;
        for (int i = 0; i < apps.size(); i++) {
            if(apps.get(i).name.equals(name)) {
                d = i;
                check = true;
                break;
            }
        }
        if(check)
            return apps.get(d);
        return null;
    }

    static void printAllApps(Category category) {
        if(User.getLoggedInUser() == null)
            inputOutput.printer(CheckResult.INVALID_COMMAND);
        else {
            if(category.equals(Category.ALL)) {
                    for (int i = 0; i < apps.size(); i++) {
                        for (int j = 0; j < 9; j++) {
                            if (apps.get(i).category.equals(Category.values()[j]))
                                System.out.println(apps.get(i).name + " - " + apps.get(i).category + " - " + apps.get(i).getTaxCalculatedCost() + " $");
                        }
                    }
            }
            else {
                for (int i = 0; i < apps.size(); i++) {
                    if (apps.get(i).category.equals(category))
                        System.out.println(apps.get(i).name + " - " + apps.get(i).category + " - " + apps.get(i).getTaxCalculatedCost() + " $");
                }
            }

        }
    }

    public User getDeveloper() {
        return developer;
    }
    public int getCost() {
        return cost;
    }
    public String getName() {
        return name;
    }
    public Category getCategory() {
        return category;
    }
    public boolean setCost(int cost, String userName) {
        if(User.getUser(userName).getUserName() != getDeveloper().getUserName() || getApp(name) == null) {
            inputOutput.printer(CheckResult.INVALID_COMMAND);
            return false;
        }
        else {
            inputOutput.printer(CheckResult.SUCCESSFUL);
            getApp(name).cost = cost;
            return true;
        }
    }

}
