package raj;

import java.io.FileReader;
import java.util.Scanner;

public class ReadCities {
    public static double[][] read(String fName) throws Exception {
        FileReader fileReader = new FileReader(fName);
        Scanner scanner = new Scanner(fileReader);
        int numCities = -1;
        int numRows = 0;
        double[][] cityMatrix = null;
        while(scanner.hasNextLine()){
            String[] cityLine = scanner.nextLine().trim().split(" ");
            if(numCities == -1){
                numCities = cityLine.length;
                cityMatrix = new double[numCities][numCities];
            }
            if(numCities == cityLine.length){

                for(int i = 0; i<cityLine.length;i++){
                    cityMatrix[numRows][i] = Double.parseDouble(cityLine[i]);

                }
                numRows++;


            }else{
                throw new Exception("file incorrect");
            }


        }
        if(numRows == numCities){
            return cityMatrix;
        }else {
            throw new Exception("Incomplete matrix "+ numRows + " Rows for "+numCities+ " Cities" );
        }

    }
}
