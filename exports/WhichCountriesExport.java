/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Mohamed Ibrahim
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportOfInterest)) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public void whoExportsCoffee() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");
    }

    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(countryInfo(parser, "Nauru"));
        
        CSVParser parser2 = fr.getCSVParser();
        listExportersTwoProducts(parser2, "cotton", "flowers");
        
        CSVParser parser3 = fr.getCSVParser();
        System.out.println("number Of cocoa Exporters: " + 
        numberOfExporters(parser3, "cocoa") );
        
        CSVParser parser4 = fr.getCSVParser();
        System.out.println("number Of sugar Exporters: " + 
        numberOfExporters(parser4, "sugar") );
        
        CSVParser parser5 = fr.getCSVParser();
        System.out.println("Countery's Value (dollars) above $999,999,999,999");
        bigExporters(parser5, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country){
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Country" column
            String currentCountry = record.get("Country");
            //Check if it contains country
            if (currentCountry.equals(country)) {
                //If so, write down the "Exports" from that row
                String exports = record.get("Exports");
                String value = record.get(2);
                return country + ": " + exports + " : " + value;
            } 
        }
        return "NOT FOUND";
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1
    , String exportItem2){
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportItem1) && export.contains(exportItem2)) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportItem)) {
                count++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String value = record.get("Value (dollars)");
            //Check if it contains exportOfInterest
            if (value.length()  > amount.length()) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
}
