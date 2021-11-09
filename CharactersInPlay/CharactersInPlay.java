
/**
 * determine the characters in one of Shakespeare’s plays.
 * 
 * @author Mohamed Ibrahim 
 * @version 1.0
 */

import edu.duke.*;
import java.util.*;
public class CharactersInPlay {
    private ArrayList<String> charsList;
    private ArrayList<Integer> freqsList;

    public CharactersInPlay(){
        charsList = new ArrayList<String>();
        freqsList = new ArrayList<Integer>();
    }

    public void update(String person){
        if(charsList.contains(person)){
            int charIndex = charsList.indexOf(person);
            int freq = freqsList.get(charIndex);
            freqsList.set(charIndex, freq + 1);
        } else{
            charsList.add(person);
            freqsList.add(1);
        }
    }

    public void findAllCharacters(){
        charsList.clear();
        freqsList.clear();
        FileResource resource = new FileResource();

        for(String line : resource.lines()){
            int periodIndex = line.indexOf(".");
            if(periodIndex != -1){
                String name = line.substring(0, periodIndex);
                update(name.toLowerCase());
            }
        }
    }

    public void tester (){
        findAllCharacters();
        for(int k=0; k < freqsList.size(); k++){
            System.out.println(freqsList.get(k) + "\t" + charsList.get(k));
        }
        int index = findMax();
        System.out.println("main character: "+charsList.get(index)+" "+freqsList.get(index));
        charactersWithNumParts(10, 100);
    }

    public int findMax(){
        int max = freqsList.get(0);
        int maxIndex = 0;
        for(int k=0; k < freqsList.size(); k++){
            if (freqsList.get(k) > max){
                max = freqsList.get(k);
                maxIndex = k;
            }
        }
        return maxIndex;
    }

    public void charactersWithNumParts(int num1, int num2){
        System.out.println(" names of characters with freq >= "
            + num1 + " and <= " + num2);

        for(int k = 0; k < freqsList.size(); k++){
            int freq = freqsList.get(k);
            if(freq >= num1 && freq <= num2){
                System.out.println(freq + "\t" + charsList.get(k));
            }
        }
    }
}
