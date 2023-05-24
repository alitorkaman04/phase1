import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private ArrayList<Owner> owners;
    private static Gson gson = new Gson();

    public Server() {
        this.owners = new ArrayList<>(Owner.getOwners());
    }

    public static void saveDataToServer(Server server) {
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
    }

}
