package CompressionEngine;

import CompressionUtilities.FrequencyList;
import CompressionUtilities.HuffmanTree;
import GeneralUtilties.Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Compressor {

    public static void Compress(String fileName){
        HuffmanTree h = new HuffmanTree();
        h.executePreProcessing(FrequencyList.readFromFileAndCreateFrequencyList(fileName));
        String[] codeWordList = h.characterToStringMapping;

        byte[] inputDataBuffer = new byte[2048];
        byte[] compressedDataBuffer = new byte[2048];

        FileInputStream fin = null;
        FileOutputStream fout = null;

        try{
            fin  = new FileInputStream(fileName);
        }catch(Exception e){
            System.out.println("Input file could not be opened for compression operation.");
            e.printStackTrace();
            return;
        }

        try{
            fout  = new FileOutputStream(fileName.substring(0,fileName.length()-4)+"_compressed.txt");
        }catch(Exception e){
            System.out.println("Output file could not be opened for compression operation.");
            e.printStackTrace();
            return;
        }

        System.out.println("Compression operation is starting now....");

        int inputReadAmount = 0;
        String tempStringBuffer = "";
        int compressedIndex = 0;
        boolean writeFlag = false;

        while(inputReadAmount!=-1){
            //System.out.println("Size of the tempStringBuffer now: "+tempStringBuffer.length() + " in Bytes: "+tempStringBuffer.length()/8);
            try{
                inputReadAmount = fin.read(inputDataBuffer); // Input File read

                for(int k = 0 ; k < inputReadAmount; k++){
                    int indexOftheSymbol = (int)inputDataBuffer[k];
                    if(codeWordList[indexOftheSymbol] == null){
                        continue;
                    }else{
                        tempStringBuffer +=  codeWordList[indexOftheSymbol]; // String formatted message;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                break;
            }

            //System.out.println("The length of the string now: "+tempStringBuffer.length()/8);

            int limit = tempStringBuffer.length()/8;
            int leftOver = tempStringBuffer.length()%8;

            boolean subStringFlag = true;

            for(int k=0;k<limit;k++){

                if(compressedIndex >= 2048){
                    writeFlag = true;
                    compressedIndex = 0;
                    tempStringBuffer = tempStringBuffer.substring(k*8);
                    subStringFlag = false;
                    break;
                }else{
                    compressedDataBuffer[compressedIndex] = Parser.parseByteFromString(tempStringBuffer.substring(k*8, (k+1)*8));
                    compressedIndex++;
                }
            }

            if(subStringFlag && leftOver>0){
                tempStringBuffer = tempStringBuffer.substring(limit*8);
            }else if(subStringFlag){
                tempStringBuffer = "";
            }

            //System.out.println("value of the compression index now: "+compressedIndex+" tempStringBuffer size: "+tempStringBuffer.length()+ "\n");

            if(limit > 0 && inputReadAmount == -1){
                writeFlag = true;
            }

            if(writeFlag){
                try{
                    fout.write(compressedDataBuffer);
                    writeFlag = false;
                }catch(Exception e){
                    System.out.println("Umm, compressed data write error");
                    e.printStackTrace();
                    return;
                }
            }
        }

        try{
            fin.close();
            fout.close();
        }catch(Exception e){
            System.out.println("Input or output file could not be closed after compression operation.");
            e.printStackTrace();
        }

    }
}
