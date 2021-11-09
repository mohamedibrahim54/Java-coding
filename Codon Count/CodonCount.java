
/**
 * a program to find out how many times each codon occurs in a strand of DNA
 * based on reading frames. A strand of DNA is made up of the symbols C, G, T, 
 * and A. A codon is three consecutive symbols in a strand of DNA 
 * such as ATT or TCC. A reading frame is a way of dividing a strand of DNA
 * into consecutive codons.
 * 
 * @author Mohamed Ibrahim 
 * @version 1.0
 */

import edu.duke.*;
import java.util.*;
public class CodonCount {

    private HashMap<String, Integer> codonMap;

    public CodonCount(){
        codonMap = new HashMap<String, Integer>();
    }

    public void buildCodonMap(int start, String dna){
        codonMap.clear();
        for(int k = start; k < (dna.length() - 2); k += 3){
            String codon = dna.substring(k, k + 3);
            if (!codonMap.containsKey(codon)){
                codonMap.put(codon,1);
            }
            else {
                codonMap.put(codon,codonMap.get(codon)+1);
            }
        }
    }

    public String getMostCommonCodon(){
        String commonCodon = "";
        int count = 0;
        for(String codon : codonMap.keySet()){
            int value = codonMap.get(codon);
            if (count < value){
                count = value;
                commonCodon = codon;
            }
        }
        return commonCodon;
    }
    
    public void printCodonCounts(int start, int end){
        System.out.println(" Codons with count >= "
            + start + " and <= " + end);

        for(String codon : codonMap.keySet()){
            int value = codonMap.get(codon);
            if(value >= start && value <= end){
                System.out.println(value+"\t"+codon);
            }
        }
    }
    
    public void printNumberOfCodons(){
        System.out.println("Number Of Codons: " + codonMap.size());
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        String dnaStrand = fr.asString().trim();
        buildCodonMap(1, dnaStrand);
        String commonCodon = getMostCommonCodon();
        System.out.println("most common codon is " + commonCodon
         + " with count " + codonMap.get(commonCodon));
         printCodonCounts(7, 7);
         printNumberOfCodons();
    }
}
