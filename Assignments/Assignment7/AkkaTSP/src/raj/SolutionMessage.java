package raj;

import java.util.List;

public class SolutionMessage {
    double weight;
    List<Integer> path;
    boolean solutionFound;

    public SolutionMessage(boolean solutionFound,double weight, List<Integer> path) {
        this.solutionFound = solutionFound;
        this.weight = weight;
        this.path = path;
    }


    public SolutionMessage() {
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public void setSolutionFound(boolean solutionFound) {
        this.solutionFound = solutionFound;
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
