package CompressionUtilities;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

public class HuffmanTree {

    public PriorityQueue<HuffmanNode> huffmanQueue = null;
    public String[] characterToStringMapping = null;
    public static int MAX_SOURCE_SYMBOL_NUMBER = 2048;

    public HuffmanTree(){
        huffmanQueue = new PriorityQueue<HuffmanNode>(new HuffmanNodeComparator());
        characterToStringMapping = new String[MAX_SOURCE_SYMBOL_NUMBER];
        Arrays.fill(characterToStringMapping,null);
    }

    public void initializeQueue(int[] frequencyList){

        if(frequencyList == null){
            System.out.println("Array FrequencyList with the frequency of the alphabet is null. Exiting now.");
            return;
        }
        if(huffmanQueue == null){
            System.out.println("Huffman Queue is empty. Did you instantiate the huffman tree object ?. Exiting now.");
            return;
        }


        for(int k=0; k<frequencyList.length; k++){
            if(frequencyList[k] != 0){
                HuffmanNode tempNode = new HuffmanNode((char)k,frequencyList[k]);
                huffmanQueue.add(tempNode);
            }
        }
    }

    public void constructTree(){

        if(huffmanQueue.isEmpty()){
            System.out.println("Queue is empty.Exiting now.");
            return;
        }

        HuffmanNode topObject = null;
        HuffmanNode secondTop = null;
        System.out.println("Size of the Huffman Queue is: "+huffmanQueue.size());
        while(huffmanQueue.size()>1){

            topObject = huffmanQueue.poll();
            secondTop = huffmanQueue.poll();

            if(topObject == null){
                System.out.println("The top of the huffman Queue is null. Error occured. Please check.");
                return;
            }

            if(secondTop == null){
                continue;
            }

            HuffmanNode mergedNode  = new HuffmanNode();
            mergedNode.count = topObject.count + secondTop.count;
            mergedNode.leftChild = topObject;
            mergedNode.rightChild = secondTop;
            huffmanQueue.add(mergedNode);
        }
        System.out.println("Size of the Huffman Queue is: "+huffmanQueue.size());
    }



    public void getHuffmanCodeStrings(){
        if(huffmanQueue.isEmpty()){
            System.out.println("The queue is empty. Tree not found.");
            return;
        }
        if(huffmanQueue.size()>1){
            System.out.println("Are you sure the tree is built ? I see multiple elements in the queue");
            return;
        }

        HuffmanNode codeTree = huffmanQueue.peek();

        if(codeTree == null){
            System.out.println("The tree is null. Please check.");
            return;
        }

        // DFS
        Stack<HuffmanNode> dfsStack = new Stack<HuffmanNode>();
        dfsStack.push(codeTree);
        codeTree.prefix = "0";

        while(!dfsStack.empty()){
            HuffmanNode top = dfsStack.peek();
            dfsStack.pop();

            if(top.leftChild != null){
                dfsStack.push(top.leftChild);
                top.leftChild.prefix = top.prefix+ "0";
            }
            if(top.rightChild != null){
                dfsStack.push(top.rightChild);
                top.rightChild.prefix = top.prefix+ "1";
            }

            if(top.isLeafNode){
                int index = (int)top.symbol;
                characterToStringMapping[index] = top.prefix;
            }
        }
    }

    public void executePreProcessing(int[] frequency){

        initializeQueue(frequency);
        constructTree();
        getHuffmanCodeStrings();

        int total = 0;

        for(int k=0; k<MAX_SOURCE_SYMBOL_NUMBER;k++){
            if(characterToStringMapping[k] != null){
                System.out.println((char)k +" -> "+frequency[k] +" -> "+characterToStringMapping[k]+" -> "
                +characterToStringMapping[k].length());
                total += (frequency[k] *characterToStringMapping[k].length());
            }
        }

        System.out.println(total/(1024*8));
    }

}
