package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String loadFileAsString(String pPath){
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br =  new BufferedReader(new FileReader(pPath));
            String line;
            while ((line = br.readLine()) != null){
                builder.append(line + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static int parseInt(String pNumber){
        try {
            return Integer.parseInt(pNumber);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}
