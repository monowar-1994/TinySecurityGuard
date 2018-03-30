package CompressionUtilities;

import java.util.Comparator;

public class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {

        if(o1.count > o2.count){
            return 1;
        }else if(o1.count < o2.count){
            return -1;
        }else{
            return 0;
        }

    }
}
