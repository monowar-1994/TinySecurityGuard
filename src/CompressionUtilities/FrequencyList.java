package CompressionUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class FrequencyList {

    static byte[] buffer = new byte[1024];

    public static int[] readFromFileAndCreateFrequencyList(String fileName){
        int[] fList = new int[HuffmanTree.MAX_SOURCE_SYMBOL_NUMBER];
        Arrays.fill(fList,0);
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        int readAmount = 0;
        while(readAmount != -1){
            try{
                readAmount =  fin.read(buffer);
                for (int i = 0; i < readAmount; i++) {
                    int index = (int)buffer[i];
                    if(index>=0){
                        fList[index] = fList[index]+1;
                    }
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
                return null;
            }
        }

        try{
            fin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return fList;
    }
}
