import java.util.ArrayList;

public class Path {
    private int location1;
    private int location2;
    private ArrayList<Integer> nodes;
    private int time;

    public Path(int location1, int location2) {
        this.location1 = location1;
        this.location2 = location2;
        nodes = new ArrayList<>(Graph.aStar(location1 - 1, location2 - 1));
        time = 0 ;
        for (int i = 0; i < nodes.size() - 1; i++) {
            int a = nodes.get(i);
            int b = nodes.get(i + 1);
            time += Graph.getEdgeWeight(a,b);
        }
    }

    public int getLocation1() {
        return location1;
    }

    public int getLocation2() {
        return location2;
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public int getTime() {
        return time;
    }
}
