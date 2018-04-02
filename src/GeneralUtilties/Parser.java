package GeneralUtilties;

public class Parser {

    public static byte parseByteFromString(String s){
        byte ret= 0x00;
        String argument = new String(s);
        if(s.length() > 8){
            System.out.println("What kind of bytes has more than 8 bits ? you doing Quantum shits bro ?");
            return ret;
        }
        if(s.length()<8){
            System.out.println("Warning : Got a string representation of a byte that is less than 8. padding now.");
            int diff = 8-s.length();
            for(int k=0;k<diff;k++){
                argument = "0"+argument;
            }
        }


        for(int k=argument.length()-1; k >= 0 ; k--){
            if(argument.charAt(k) == '1'){
                ret = (byte)(ret | 0x01);
            }
            ret = (byte)(ret << 1);
        }

        return ret;
    }


}
