/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Mohamed Ibrahim 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                    " Gender " + rec.get(1) +
                    " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int boysNames = 0;
        int girlsNames = 0;
        int totalNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                boysNames++;
            }
            else {
                totalGirls += numBorn;
                girlsNames++;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);

        totalNames = girlsNames + boysNames;
        System.out.println("the total names = " + totalNames);
        System.out.println("the number of girls names = " + girlsNames);
        System.out.println("the number of boys names = " + boysNames);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob1905.csv");
        totalBirths(fr);
    }

    public int getRank(int year, String name, String gender){
        int rank = 0;
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if(rec.get(0).equals(name)){
                    return rank;
                }
            }
        }
        return -1;
    }

    public void testGetRank(){
        int rank = getRank(1971, "Frank", "M");
        System.out.println("the Rank of Frank is " + rank);
    }

    public String getName(int year, int rank, String gender){
        int count = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                count++;
                if(count == rank){
                    return rec.get(0);
                }
            }
        }

        return "NO NAME";
    }

    public void testGetName(){
        String name = getName(1980, 350, "F");
        System.out.println("the name of the person in the file at this rank " + name);
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int nameRank = getRank(year, name, gender);
        String newName = getName(newYear, nameRank, gender);
        System.out.println(name + " born in "+ year +" would be " + newName + " if she was born in " + newYear);
    }

    public void testWhatIsNameInYear(){
        whatIsNameInYear("Isabella", 2012, 2014, "F"); 
    }

    // public int getRank(FileResource fr, String name, String gender){
        // int rank = 0;
        // for (CSVRecord rec : fr.getCSVParser(false)) {
            // if (rec.get(1).equals(gender)) {
                // rank++;
                // if(rec.get(0).equals(name)){
                    // return rank;
                // }
            // }
        // }
        // return -1;
    // }

    public int yearOfHighestRank(String name, String gender){
        int highRank = -1;
        int highYear = -1;
        DirectoryResource fd = new DirectoryResource();
        for(File f : fd.selectedFiles()){
            String fileName = f.getName();
            int startIndex = 3;
            int year = Integer.parseInt( fileName.substring(startIndex, (startIndex + 4) ) );
            //FileResource fr = new FileResource(f);
            int currentRank = getRank(year, name, gender);
            if(currentRank != -1){
                if(highRank == -1){
                    highRank = currentRank;
                    highYear = year;
                } else{
                    if(currentRank < highRank){
                        highRank = currentRank;
                        highYear = year;
                    }
                }
            }
        }
        return highYear;
    }

    public void testYearOfHighestRank(){
        int year = yearOfHighestRank("Mich", "M");
        System.out.println("highest ranking was in " + year);
    }
    
    public double getAverageRank(String name, String gender){
        double rankSum = 0;
        double rankCount = 0;
        
        DirectoryResource fd = new DirectoryResource();
        for(File f : fd.selectedFiles()){
            String fileName = f.getName();
            int startIndex = 3;
            int year = Integer.parseInt( fileName.substring(startIndex, (startIndex + 4) ) );
            int currentRank = getRank(year, name, gender);
            if(currentRank != -1){
                rankSum += currentRank; 
                rankCount++;
            }
        }
        return rankSum / rankCount;
    }
    
    public void testGetAverageRank(){
        double avg = getAverageRank("Robert", "M");
        System.out.println("average rank of the name and gender is " + avg);
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int total = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if(rec.get(0).equals(name)){
                    break;
                }
                total += Integer.parseInt(rec.get(2));
            }
        }
        return total;
    }
    
    public void testGetTotalBirthsRankedHigher(){
        int total = getTotalBirthsRankedHigher(1990, "Drew", "M");
        System.out.println("number of births ranked higher is " + total);
    }
}
