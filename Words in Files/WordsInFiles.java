
/**
 * program to determine which words occur in the greatest number of files,
 * and for each word, which files they occur in. 
 * 
 * @author mohamed Ibrahim 
 * @version 1.0
 */

import edu.duke.*;
import java.util.*;
import java.io.*;
public class WordsInFiles {

    private HashMap<String, ArrayList<String>> wordsMap;
    public WordsInFiles(){
        wordsMap = new HashMap<String, ArrayList<String>>();
    }

    private void addWordsFromFile(File f){
        String fileName = f.getName();
        FileResource resource = new FileResource(f);
        for(String word : resource.words()){
            if(wordsMap.containsKey(word)){
                if(!wordsMap.get(word).contains(fileName)){
                    wordsMap.get(word).add(fileName);
                }
            } else{
                ArrayList<String> filesList = new ArrayList<String>();
                filesList.add(fileName);
                wordsMap.put(word, filesList);
            }
        }
    }
    
    public void buildWordFileMap(){
        wordsMap.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    
    private int maxNumber(){
        int maxNumber = 0;
        for(ArrayList<String>  arr: wordsMap.values()){
            if(maxNumber < arr.size()){
                maxNumber = arr.size();
            }
        }
        return maxNumber;
    }
    
    private ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> wordsArray = new ArrayList<String>();
        for(String word : wordsMap.keySet()){
            if( number == wordsMap.get(word).size() ){
                wordsArray.add(word);
            }
        }
        return wordsArray;
    }
    
    private void printFilesIn(String word){
        System.out.println(word + " appear in :");
        ArrayList<String> files = wordsMap.get(word);
        for(String f : files){
            System.out.println(f);
        }
    }
    
    public void tester(){
        buildWordFileMap();
        //printFilesIn("cats");
        System.out.println("maxNumber: " + maxNumber());
        ArrayList<String> wordsList = wordsInNumFiles(7);
        for(String w : wordsList){
            System.out.println(w);
        }
        System.out.println("size of word List in 7 files: " + wordsList.size());
        printFilesIn("tree");
        printFilesIn("sea");
    }
}
