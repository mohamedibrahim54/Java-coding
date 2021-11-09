/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Mohamed Ibrahim 
 */
import edu.duke.*;
import java.io.*;

public class TagFinder {
    public String findProtein(String dna) {
        int start = dna.indexOf("atg");
        if (start == -1) {
            return "";
        }
        int stop = dna.indexOf("tag", start+3);
        if ((stop - start) % 3 == 0) {
            return dna.substring(start, stop+3);
        }
        else {
            return "";
        }
    }

    public void testing() {
        String a = "cccatggggtttaaataataataggagagagagagagagttt";
        String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        //String a = "ATGCCCTAG";
        //String ap = "ATGCCCTAG";
        String result = findProtein(a);
        if (ap.equals(result)) {
            System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }

    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();
            System.out.println("read " + s.length() + " characters");
            //String result = findProtein(s);
            int result = countGenes(s);
            System.out.println("found: " + result);
        }
    }

    public double cgRatio(String dna){
        int count = 0;
        int totalChars = 0;
        for(char c : dna.toLowerCase().toCharArray() ){
            if(c == 'c' || c == 'g'){
                count++;
            }
            totalChars++;
        }
        return (double)count / totalChars;
    }

    public void testcgRatio(){
        System.out.println("cgRatio is " + cgRatio("ATGCCATAG"));
    }

    public int countCTG(String dna){
        int count = 0;
        int index = dna.indexOf("CTG");
        while (true) {
            if (index == -1 || index >= dna.length() - 3) {
                break;
            }
            //System.out.println("index" + (index) + " and index+3 " + (index+3) );
            String found = dna.substring(index, index+3); 
            //System.out.println(found);
            count ++;
            index = dna.indexOf("CTG", index+3);
        }
        return count;
    }

    public void processGenes(StorageResource sr){
        int longer9 = 0;
        int higherCG = 0;
        int longest = 0;
        int totalGenes = 0;
        for (String item : sr.data()) {
            totalGenes ++;
            if(item.length() > 60){
                System.out.println("longer than 60: " + item);
                longer9 ++;
            }
            if(cgRatio(item) > 0.35){
                System.out.println("C-G-ratio is higher than 0.35: " + item);
                higherCG ++;
            }
            if (longest == 0){
                longest = item.length();
            } else{
                if(item.length() > longest){
                    longest = item.length();
                }
            }
        }
        System.out.println("total number of Genes: " + totalGenes);
        System.out.println("number of Strings longer than 9: " + longer9);
        System.out.println("number of Strings C-G-ratio is higher than 0.35: " + higherCG);
        System.out.println("length of the longest gene in sr: " + longest);
    }

    public void testProcessGenes(){
        FileResource fr = new FileResource("brca1.fa");
        //String dna = fr.asString();
        StorageResource sr = new StorageResource();
        for (String line : fr.lines()) {
            sr.add(line);
        }
        processGenes(sr);
    }

    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1){

            if ((currIndex - startIndex) % 3 == 0) {
                return currIndex;
            } else{
                currIndex = dna.indexOf(stopCodon, currIndex + 3);
            }
        }
        return dna.length();
    }

    public void testFindStopCodon(){
        String a = "cccatggggtttaaataataataggagagagagagagagttt";
        String ap = "atggggtttaaataataatag";
        System.out.println( findStopCodon(a, 3, "tag") );
        System.out.println(countGenes(a));
    }

    public String findGene(String dna, int where){
        String smallCharDNA = dna.toLowerCase();
        int start = smallCharDNA.indexOf("atg", where);
        if (start == -1) {
            return "";
        }
        int stopTAA = findStopCodon(smallCharDNA, start, "taa");
        int stopTAG = findStopCodon(smallCharDNA, start, "tag");
        int stopTGA = findStopCodon(smallCharDNA, start, "tga");
        int temp = Math.min(stopTAA, stopTAG);
        int minIndex = Math.min(temp, stopTGA);
        if(minIndex == dna.length()){
            return "";

        }
        return dna.substring(start, minIndex+3);
    }

    public StorageResource getAllGenes(String dna) {
        StorageResource store = new StorageResource();
        int startIndex = 0;

        while (true) {
            String currentGene = findGene(dna,startIndex);
            System.out.println(currentGene);
            if (currentGene.isEmpty()) {
                break;
            }
            else {
                store.add(currentGene);
                startIndex = dna.indexOf(currentGene,startIndex)+currentGene.length();
            }
        }
        return store;
    }

    public void testGetAllGenes(){
        FileResource fr = new FileResource();
        String s = fr.asString();
        StorageResource store = getAllGenes(s);
        for (String item : store.data()) {
            System.out.println(item);
        }
        processGenes(store);
        System.out.println("times does the codon CTG appear in this strand of DNA: " + countCTG(s) );
    }

    // tested: work as expected
    public int countGenes(String dna){
        String smallCharDNA = dna.toLowerCase();
        int count = 0;
        int stopIndex = dna.length();
        int start = smallCharDNA.indexOf("atg");

        while(true){
            stopIndex = dna.length();
            System.out.println("start index: " + start);
            if (start == -1) {
                break;
            }
            int stopTAA = findStopCodon(smallCharDNA, start, "taa");
            int stopTAG = findStopCodon(smallCharDNA, start, "tag");
            int stopTGA = findStopCodon(smallCharDNA, start, "tga");
            System.out.println("stopTAA: " + stopTAA);
            System.out.println("stopTAG: " + stopTAG);
            System.out.println("stopTGA: " + stopTGA);
            if(stopTAA < stopTAG && stopTAA < stopTGA){
                stopIndex = stopTAA;
                System.out.println( dna.substring(start, stopTAA+3) );
                count ++;
            }

            if(stopTAG < stopTAA && stopTAG < stopTGA){
                stopIndex = stopTAG;
                System.out.println( dna.substring(start, stopTAG+3) );
                count ++;
            }

            if(stopTGA < stopTAG && stopTGA < stopTAA){
                stopIndex = stopTGA;
                System.out.println( dna.substring(start, stopTGA+3) );
                count ++;
            }
            System.out.println("stop index: " + stopIndex);
            start = smallCharDNA.indexOf("atg" , stopIndex + 3);
        }
        return count;
    }

}
