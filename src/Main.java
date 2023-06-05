import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        InputOutputProcessor inputOutput = InputOutputProcessor.getInstance();
        Scanner sc = inputOutput.getScanner();
        Server.readDataFromServer();

        File file = new File("graph.txt");
        Scanner raeder = new Scanner(file);
        String[] firstline = raeder.nextLine().split(" ");
        int node = Integer.parseInt(firstline[0]);
        Graph graph = new Graph(node);
        int mline = Integer.parseInt(firstline[1]);
        for (int i = 0; i < mline; i++) {
            String[] lines = raeder.nextLine().split(" ");
            int source = Integer.parseInt(lines[0]);
            int dest = Integer.parseInt(lines[1]);
            int weight = Integer.parseInt(lines[2]);
            graph.addEdge(source - 1, dest - 1, weight);
        }

        while (true) {
            String[] dastoor = sc.nextLine().split(" ");
            if(dastoor[0].equals("exit")) {
                Owner.setLoggedInOwner(null);
                Costumer.setLoggedInCostumer(null);
                Server server = new Server();
                Server.writeDataToServer(server);
                break;
            }


            else if(dastoor[0].equals("ADD") && dastoor[1].equals("COSTUMER"))
                Costumer.addNewCostumer(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("OWNER"))
                Owner.addNewOwner(dastoor[2], dastoor[3]);

            else if(dastoor[0].equals("LOGIN") && dastoor[1].equals("COSTUMER"))
                Costumer.loginCostumer(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("LOGIN") && dastoor[1].equals("OWNER"))
                Owner.loginOwner(dastoor[2], dastoor[3]);
            else if(dastoor[0].equals("LOGOUT")) {
                if(Owner.getLoggedInOwner() != null)
                    Owner.logoutOwner();
                else
                    Costumer.logoutCostumer();
            }

            else if(dastoor[0].equals("ADD") && dastoor[1].equals("NEW") && dastoor[2].equals("RESTAURANT"))
                Restaurant.addNewRestaurant(dastoor[3], Integer.parseInt(dastoor[4]), dastoor[5]);

            else if(dastoor[0].equals("SELECT") && dastoor[1].equals("MENU"))
                Owner.selectMenu();
            else if(dastoor[0].equals("SELECT") && dastoor[1].equals("FOOD"))
                Owner.selectFood(Integer.parseInt(dastoor[2]));


            else if(dastoor[0].equals("SELECT")) {
                if(Owner.getLoggedInOwner() != null)
                    Owner.selectRestaurant(Integer.parseInt(dastoor[1]));
                else if(Costumer.getLoggedInCostumer() != null) {
                    if(Costumer.getLoggedInCostumer().getSelectedRestaurant() == null)
                        Costumer.selectRestaurant(Integer.parseInt(dastoor[1]));
                    else
                        Costumer.selectFood(Integer.parseInt(dastoor[1]));
                }
                else
                    inputOutput.printer(CheckResult.INVALID_COMMAND);
            }
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
            else if(dastoor[0].equals("DELETE") && dastoor[1].equals("FOOD"))
                Owner.deleteFood(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("DEACTIVE") && dastoor[1].equals("FOOD"))
                Restaurant.deactiveFood(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("ACTIVE") && dastoor[1].equals("FOOD"))
                Restaurant.activeFood(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("FOOD") && dastoor[3].equals("NAME"))
                Owner.editFoodName(Integer.parseInt(dastoor[2]), dastoor[4]);
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("FOOD") && dastoor[3].equals("PRICE"))
                Owner.editFoodPrice(Integer.parseInt(dastoor[2]), Double.parseDouble(dastoor[4]));
            else if(dastoor[0].equals("DISCOUNT") && dastoor[1].equals("FOOD"))
                Owner.discountFood(Integer.parseInt(dastoor[2]), Integer.parseInt(dastoor[3]), Integer.parseInt(dastoor[4]));

            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("COMMENTS"))
                Costumer.displayComments();
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("NEW") && dastoor[2].equals("COMMENT"))
                Costumer.addNewComment(dastoor[3]);
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("COMMENT"))
                Costumer.editComment(Integer.parseInt(dastoor[2]), dastoor[3]);
            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("RATING"))
                Costumer.displayRating();
            else if(dastoor[0].equals("SUBMIT") && dastoor[1].equals("RATING"))
                Costumer.submitRate(Integer.parseInt(dastoor[2]));

            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("RATINGS"))
                Food.displayRatings();
            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("COMMENTS"))
                Food.displayComments();
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("NEW") && dastoor[2].equals("RESPONSE"))
                Owner.addNewResponse(Integer.parseInt(dastoor[3]), dastoor[4]);
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("RESPONSE"))
                Owner.addNewResponse(Integer.parseInt(dastoor[2]), dastoor[3]);
            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("OPEN") && dastoor[2].equals("ORDERS"))
                    Owner.displayOpenOrders();
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("ORDER") && dastoor[3].equals("STATUS") && dastoor[4].equals("SENT"))
                Owner.editOrderStatusSent(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("SHOW") && dastoor[1].equals("ORDER") && dastoor[2].equals("HISTORY"))
                Owner.showOrderHistory();

//            else if(dastoor[0].equals("SHOW") && dastoor[1].equals("RESTAURANT"))
//                Costumer.showRestaurantsList();
            else if(dastoor[0].equals("SEARCH") && dastoor[1].equals("RESTAURANT"))
                Costumer.searchRestaurant(dastoor[2]);
            else if(dastoor[0].equals("SEARCH"))
                Costumer.searchFood(dastoor[1]);
            else if(dastoor[0].equals("EDIT") && dastoor[1].equals("RATING"))
                Costumer.editRate(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("ADD") && dastoor[1].equals("THIS") && dastoor[2].equals("FOOD") && dastoor[3].equals("TO") && dastoor[4].equals("CART"))
                Costumer.addFoodToCart();
            else if(dastoor[0].equals("ACCESS") && dastoor[1].equals("ORDER") && dastoor[2].equals("HISTORY"))
                Costumer.accessOrderHistory();
            else if(dastoor[0].equals("SELECT") && dastoor[1].equals("ORDER"))
                Costumer.selectOrder(Integer.parseInt(dastoor[2]));
            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("CART") && dastoor[2].equals("STATUS"))
                Costumer.displayCartStatus();
            else if(dastoor[0].equals("CONFIRM") && dastoor[1].equals("ORDER"))
                Costumer.confirmOrder();
            else if(dastoor[0].equals("CHARGE") && dastoor[1].equals("ACCOUNT"))
                Costumer.chargeAccount(Double.parseDouble(dastoor[2]));
            else if(dastoor[0].equals("DISPLAY") && dastoor[1].equals("ACCOUNT") && dastoor[2].equals("CHARGE"))
                Costumer.displayAccountCharge();
            else if(dastoor[0].equals("SHOW") && dastoor[1].equals("ESTIMATED") && dastoor[2].equals("DELIVERY") && dastoor[3].equals("TIME"))
                Costumer.showEstimatedDeliveryTime();




            else if(dastoor[0].equals("RETURN")) {
                if(Owner.getLoggedInOwner() != null)
                    Owner.getLoggedInOwner().back();
                else if(Costumer.getLoggedInCostumer() != null)
                    Costumer.getLoggedInCostumer().back();
                else
                    System.out.println(CheckResult.INVALID_COMMAND);
            }

            else
                inputOutput.printer(CheckResult.INVALID_COMMAND);

//
        }

    }
}