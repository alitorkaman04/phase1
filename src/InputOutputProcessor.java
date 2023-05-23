import java.util.Scanner;

public class InputOutputProcessor {
    private static final InputOutputProcessor instance = new InputOutputProcessor();
    final Scanner scanner = new Scanner(System.in);
    private InputOutputProcessor() {

    }
    boolean printer(CheckResult a) {
        if(a == CheckResult.SUCCESSFUL) {
            System.out.println("Successful!");
            return true;
        }
        if(a == CheckResult.COST_ERROR) {
            System.out.println("Invalid Cost!");
            return true;
        }
        if(a == CheckResult.ID_ERROR) {
            System.out.println("Invalid id!");
            return true;
        }
        if(a == CheckResult.INVALID_COMMAND) {
            System.out.println("Invalid Command!");
            return true;
        }
        if(a == CheckResult.NOT_ENOUGH_CREDIT) {
            System.out.println("Low Credit!");
            return true;
        }
        if(a == CheckResult.PASSWORD_ERROR) {
            System.out.println("Invalid Password!");
            return true;
        }
        if(a == CheckResult.USER_NAME_ERROR) {
            System.out.println("Invalid Username!");
            return true;
        }
        return false;
    }
    public static InputOutputProcessor getInstance() {
        return instance;
    }

    public Scanner getScanner() {
        return this.scanner;
    }
}


