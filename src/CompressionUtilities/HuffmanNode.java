package CompressionUtilities;

public class HuffmanNode {
    public char symbol; // The source symbol
    public int count ; // The  number of times the symbol appeared in the input file
    public boolean isLeafNode ; // Is it is a leaf node or not

    public HuffmanNode leftChild,rightChild; // if internal node of the tree then it would have children

    public String prefix; // The prefix code up to this node.

    public HuffmanNode(){
        symbol = '\0';
        count = 0;
        isLeafNode = false;
        leftChild = null;
        rightChild = null;
        prefix = "";
    }

    public HuffmanNode(char ch, int n){
        symbol = ch;
        count = n;
        isLeafNode = true;
        leftChild = null;
        rightChild = null;
        prefix = "";
    }

    @Override
    public String toString(){
        String ret = "";
        ret += "symbol : "+symbol+"\n";
        ret += "count : "+count+"\n";
        ret += "prefix : "+prefix+"\n";
        ret += "leftChild : "+leftChild+"\n";
        ret += "rightChild : "+rightChild+"\n";
        return ret;
    }
}
