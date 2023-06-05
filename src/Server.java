import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private ArrayList<Owner> owners;
    private ArrayList<Costumer> costumers;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods;
//    private ArrayList<Delivery> deliveries;

    private static Gson gson = new Gson();

    public Server() {
        owners = new ArrayList<>(Owner.getOwners());
        restaurants = new ArrayList<>(Restaurant.getRestaurants());
        costumers = new ArrayList<>(Costumer.getCostumers());
        foods = new ArrayList<>(Food.getFoods());
//        deliveries = new ArrayList<>(Delivery.getDeliveries());
    }

    public static void writeDataToServer(Server server) {
        String json = gson.toJson(server);
        try (FileWriter fileWriter = new FileWriter("server.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readDataFromServer() {
        String json = null;
        try (FileReader fileReader = new FileReader("server.json")) {
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server loadedServer = gson.fromJson(json, Server.class);
        Owner.getOwners().addAll(loadedServer.owners);
        Costumer.getCostumers().addAll(loadedServer.costumers);
        Food.getFoods().addAll(loadedServer.foods);
        Restaurant.getRestaurants().addAll(loadedServer.restaurants);
//        Delivery.getDeliveries().addAll(loadedServer.deliveries);

//        for (int i = 0; i < loadedServer.owners.size(); i++) {
//            Restaurant.getRestaurants().addAll(loadedServer.owners.get(i).getRestaurants());
//        }

    }

}
