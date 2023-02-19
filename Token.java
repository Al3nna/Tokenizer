//This program was made by Alenna for the CTE course "Software & Programming Development 2" instructed by Mr. Gross
//Email - alenna.castaneda@oneidaihla.org

//importing required classes
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Token {
    //creating attribute
    static String token;
    static boolean heading;

    //creating arraylists
    static ArrayList<String> letter = new ArrayList<>();
    static ArrayList<Integer> frequency = new ArrayList<>();
    static ArrayList<Float> percentage = new ArrayList<>();
    
    
    //removing ' ' & '"'
    public void clean(String data) {
        //creating attribute
        boolean complete = false;

        //while cleaning process is in progress
        while (complete != true) {
            //looping through data
            for (int i = 0; i < data.length(); i++) {
                //if current char = ' ' or '"'
                if (data.charAt(i) == ' ' || data.charAt(i) == '"') {
                    //if char is not the first char
                    if (i > 0) {
                        data = data.substring(0, i)+data.substring(i+1);
                    //if char is the first char
                    } else {
                        data = data.substring(i+1);
                    }
                    //stopping loop through token
                    i = data.length();
                }
                //if token has been looped through
                if (i == data.length()-1) {
                    //stopping while loop
                    complete = true;
                    token = data;
                }
            }
        }
        //if token is the column header
        if (token.contains("Letter") || token.contains("Frequency") || token.contains("Percentage")) {
            heading = true;
        }
    }

    //printing info
    public void printData() {
        //looping through size of letter
        for (int i = 0; i < letter.size(); i++) {
            //printing data
            System.out.println(letter.get(i)+"  "+frequency.get(i)+", "+percentage.get(i));
        }
    }

    //getting & printing total data
    public void total() {
        //creating attributes
        int totalFrequency = 0;
        int avgFrequency;
        float totalPercent = 0;

        //looping through size of letter
        for (int i = 0; i < letter.size(); i++) {
            //setting totalFrequency
            totalFrequency += frequency.get(i);
            //setting totalPercent
            totalPercent += percentage.get(i);
        }

        //setting avgFrequency
        avgFrequency = totalFrequency / frequency.size();

        //printing total
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Total");
        System.out.println("Average Frequency: "+avgFrequency+"    Total Percentage: "+totalPercent);
        System.out.println("--------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        //creating Token object
        Token base = new Token();
        try {
            //creating scanner that reads from letter frequency file
            Scanner fileScan = new Scanner(new File("letter_frequency.csv"));
            //creating StringTokenizer object
            StringTokenizer tokenizer;
            //creating attributes
            String line = "";
            int column = 1;
            
            //looping while file has another line
            while (fileScan.hasNextLine()) {
                //setting attributes
                line = fileScan.nextLine();
                tokenizer = new StringTokenizer(line);
                //looping while there are more tokens
                while (tokenizer.hasMoreTokens()) {
                    //getting token
                    token = tokenizer.nextToken(",");
                    //resetting variable
                    heading = false;
                    

                    //creating switch case
                    switch(column) {
                        case 1:
                            //running clean function
                            base.clean(token);
                            //if token is not "Letter"
                            if (heading != true) {
                                //adding token to letter arraylist
                                letter.add(token);
                            }
                            break;
                        case 2:
                            //running clean function
                            base.clean(token);
                            //if token is not "Frequency"
                            if (heading != true) {
                                //adding token to frequency arraylist
                                frequency.add(Integer.valueOf(token));
                            }
                            break;
                        case 3:
                            //running clean function
                            base.clean(token);
                            //if token is not "Percentage"
                            if (heading != true) {
                                //adding token to percentage arraylist
                                percentage.add(Float.valueOf(token));
                            }
                    }

                    //cycling column value
                    if (column < 3) {
                        column++;
                    } else {
                        column = 1;
                    }
                }
            }
            //closing scanner
            fileScan.close();

        //catching exception
        } catch (FileNotFoundException f) {
            System.out.println(f);
        }

        System.out.println();
        //printing data
        base.printData();
        System.out.println();
        //finding & printing total
        base.total();
    }
}