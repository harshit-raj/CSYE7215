package raj;

import java.util.List;

public class SolutionMessage {
    double weight;
    List<Integer> path;

    public SolutionMessage(double weight, List<Integer> path) {
        this.weight = weight;
        this.path = path;
    }

    public SolutionMessage() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SolutionMessage{" +
                "weight=" + weight +
                ", path=" + path.toString() +
                '}';
    }
}
