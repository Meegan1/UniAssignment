import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class File {
    private static Table table = Football.table; // stores Football.table object instance in table


    // Loads a file into the table
    public static void loadFile(String fileName)
    {
        int errors = 0; // count for errors in file
        int firstTable = table.nextIndex(); // stores results index of first input for later use
        java.io.File file = new java.io.File(fileName);
        try {
            Scanner sfile = new Scanner(file);

            int line = 0; // count for line number
            while(sfile.hasNextLine())
            {
                if(!table.addResult(sfile.nextLine(), line+1)) // attempt to input line from file, checks for errors
                {
                    errors++; // if error is found increase count by 1
                }
                line++; // increase line number by 1
            }
            sfile.close(); // close file

            if(errors==0) { // checks if no errors
                Display.mainMenu("The data has been loaded from "+fileName);
            } else {
                int v=0; // used for validating the users input
                while(v==0) {
                    v=1; // allows loop to break if input is valid
                    System.out.print("Do you still want to input the valid data? (Y/N): ");
                    String option = Keyboard.nextLine();
                    switch (option.toLowerCase()) {
                        case "y":
                            Display.mainMenu("The data has been loaded from "+fileName); // keeps the valid stored data
                            break;
                        case "n":
                            unloadFile(firstTable, line, errors); // removes the valid stored data
                            Display.mainMenu();
                            break;
                        default:
                            v=0; // keeps loop going
                            Display.error(1, "Unknown command");
                            break;
                    }
                }
            }

        }
        catch (FileNotFoundException e) // if file not found display error and allow user to try again
        {
            Display.error("File not found");
            Display.load();
        }

    }

    // Unloads a file from the table
    public static void unloadFile(int start, int lines, int errors)
    {
        // Loop which goes through all the rows which were inserted
        for(int i=start; i<(start+(lines-errors)); i++) {
            table.deleteLastResult();
        }
    }

    // Saves a string into a file
    public static void saveFile(String fileName, StringBuilder string)
    {
        FileWriter fstream; // declares the file stream
        try {
            fstream = new FileWriter(fileName); // initializes the file object
            BufferedWriter out = new BufferedWriter(fstream); // declares and initializes the buffer
            out.write(string.toString()); // writes string parameter into the file
            out.close(); // closes the file
        } catch (IOException e) { // prevents crash from issue with saving file
            e.printStackTrace();
        }
        finally {
            Display.clearConsole();
            Display.mainMenu("The results have been stored in "+fileName);
        }
    }

    // Creates a text file
    public static void createTextFile(String fileName)
    {
        StringBuilder file = new StringBuilder(); // declares and initializes the string builder object

        // For loop used for each result in the table
        for(int i = 0; i <= table.currentIndex(); i++)
        {
            file.append(table.getDataString(i)+"\n"); // writes the result of table.getDataString() in the file
        }

        saveFile(fileName, file); // runs the saveFile() method
    }

    // Creates a HTML file
    public static void createHTML(String fileName)
    {
        StringBuilder html = new StringBuilder(); // declares and initializes the string builder object

        // ========[Writes the HTML into the string]=======
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Football Results</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<center><br><br><br><h1>Football Results</h1>");


        // For loop used for each result in the table
        for(int i = 0; i <= table.currentIndex(); i++)
        {
            html.append(table.getString(i)+"<br>"); // appends the result of table.getString() into the string
        }

        // Output of the report
        html.append("<br><br>----------------------Totals----------------------<br>");
        html.append("Total number of matches played: "+table.totalMatches()+"<br>");
        html.append("Total home score: "+table.totalHomeScores()+"<br>");
        html.append("Total away score: "+table.totalAwayScores()+"<br>");
        html.append("Highest home score: "+table.highestHomeScore()+"<br>");
        html.append("Highest away score: "+table.highestAwayScore()+"<br>");
        html.append("Total invalid entries: "+table.invalidEntries()+"<br>");


        html.append("</center>");
        html.append("</body>");
        html.append("</html>");

        saveFile(fileName, html); // calls the saveFile() method
    }


}
