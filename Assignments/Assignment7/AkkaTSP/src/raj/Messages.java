package raj;

/**
 * Messages that are passed around the actors are usually immutable classes.
 * Think how you go about creating immutable classes:) Make them all static
 * classes inside the Messages class.
 * 
 * This class should have all the immutable messages that you need to pass
 * around actors. You are free to add more classes(Messages) that you think is
 * necessary
 * 
 * @author Akash Nagesh and M. Kokar
 *
 */
public class Messages {
	double[][] cityMatrix;
	double maxLength;
	int city;

    public Messages(double[][] cityMatrix, double maxLength, int city) {
        this.cityMatrix = cityMatrix;
        this.maxLength = maxLength;
        this.city = city;
    }

    public double[][] getCityMatrix() {
        return cityMatrix;
    }

    public void setCityMatrix(double[][] cityMatrix) {
        this.cityMatrix = cityMatrix;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}