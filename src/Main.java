import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();
        Scanner sc = inputOutput.getScanner();

        while (true) {
            String[] dastoor = sc.nextLine().split(" ");
            if(dastoor[0].equals("exit"))
                break;
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("USER"))
                User.addNewUser(dastoor[3], dastoor[2]);

            else
                System.out.println(CheckResult.INVALID_COMMAND);

//TODO : Checking
        }

    }
}