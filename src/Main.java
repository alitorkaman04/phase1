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

            else if(dastoor[0].equals("ADD") && dastoor[1].equals("COSTUMER"))
                Costumer.addNewCostumer(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("OWNER"))
                Owner.addNewOwner(dastoor[2], dastoor[3]);

            else if(dastoor[0].equals("LOGIN") && dastoor[1].equals("COSTUMER"))
                Costumer.loginCostumer(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("LOGIN") && dastoor[1].equals("OWNER"))
                Owner.loginOwner(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("LOGOUT"))
                Owner.logoutOwner();

            else if(dastoor[0].equals("ADD") && dastoor[1].equals("NEW") && dastoor[2].equals("RESTAURANT"))
                Restaurant.addNewRestaurant(dastoor[3], Integer.parseInt(dastoor[4]), dastoor[5]);

            else if(dastoor[0].equals("SELECT"))
                Owner.selectRestaurant(Integer.parseInt(dastoor[1]));
            else if(dastoor[0].equals("SHOW") && dastoor[1].equals("LOCATION"))
                Owner.showLocation();
            else if(dastoor[0].equals("SHOW") && dastoor[1].equals("FOODTYPE"))
                Owner.showFoodType();
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("LOCATION"))
                Owner.editLocation(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("FOODTYPE"))
                Owner.editFoodType(dastoor[2]);

            else if(dastoor[0].equals("ADD") && dastoor[1].equals("FOOD"))
                Owner.addNewFood(dastoor[2], Double.parseDouble(dastoor[3]));



            else
                System.out.println(CheckResult.INVALID_COMMAND);

//
        }

    }
}