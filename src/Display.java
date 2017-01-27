public class Display {
    private static Table table = Football.table; // stores Football.table object instance in table


    // Clears the console then displays the table and menu
    public static void mainMenu() {mainMenu("");}
    public static void mainMenu(String result) {
        clearConsole();
        table();
        if(!result.isEmpty()) // if parameter result exists
            System.out.println("UPDATE: "+result); // post update info above menu
        menu(true);
    }


    // Displays the menu, used to select option
    public static void menu() {menu(false);} // Shows the main menu by default
    public static void menu(boolean i) {    // Whether to display the main menu (true/false)
        if(i==true) // if true, display main menu
            System.out.println("Main Menu Commands:" +
                    "\n\tadd \t-\t Create new entries." +
                    "\n\tview \t-\t Displays the current entries followed by a report." +
                    "\n\tleague \t-\t Displays the league table." +
                    "\n\tsearch \t-\t Search for a team name to view their stats." +
                    "\n\tload \t-\t Load data from a file." +
                    "\n\tsave \t-\t Save the table as data or a HTML file." +
                    "\n\twipe \t-\t Wipes all data from the current session" +
                    "\n\texit \t-\t Exit the program.");

        System.out.println("Please type a command: ");

        String option = Keyboard.nextLine(); // stores user input
        switch(option.toLowerCase())	// allows user to input in upper or lower case
        {
            case "add": // Create new entries
                add();
                break;
            case "view": // Displays the current entries followed by a report.
                clearConsole();
                view();
                break;
            case "league": // Displays the league table.
                clearConsole();
                leagueTable();
                break;
            case "search": // Search for a team name to view their stats.
                clearConsole();
                teamSearch();
                break;
            case "load": // Load data from a file.
                load();
                break;
            case "save":
                clearConsole(); // Save the table as data or a HTML file.
                save();
                break;
            case "wipe": // Wipes all data from the current session
                table.wipe();
                mainMenu();
                break;
            case "exit": // Exit the program.
                Football.exit();
                break;
            default: // if command doesn't exist
                error(1, "UNKNOWN COMMAND"); // display error
                menu();
                break;
        }
    }


    // Displays report and pauses
    public static void view() {
        table();
        report();
        pause();
        mainMenu();
    }

    // Displays the tables in a table
    public static void table() {
        //begins displaying the table
        System.out.println("|----------------------------------------------------------------|");
        System.out.println("|                                                                |");
        System.out.println("|                        Football Results                        |");
        System.out.println("|                                                                |");
        System.out.println("|----------------------------------------------------------------|");
        System.out.println();
        System.out.println("#\t\tHome Team  [Home Team Score] | Away Team [Away Team Score]");
        System.out.println("-------------------------------------------------------------------");

        table.printResults(); // prints results

        System.out.println();
    }


    //Displays the report
    public static void report() {
        // Output of the report
        System.out.println("Totals\n----------------------");
        System.out.println("Total number of matches played: "+table.totalMatches());
        System.out.println("Total home score: "+table.totalHomeScores());
        System.out.println("Total away score: "+table.totalAwayScores());
        System.out.println("Highest home score: "+table.highestHomeScore());
        System.out.println("Highest away score: "+table.highestAwayScore());
        System.out.println("Total invalid entries: "+table.invalidEntries());
        System.out.println();
    }

    // Displays the league table
    public static void leagueTable() {
        clearConsole();
        table.updateTeams();
        System.out.println("/////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("==             League Table             ==");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\/////////////////////");
        System.out.println();
        System.out.format("%-40s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s\n", "Team Name", "P", "W", "D", "L", "F", "A", "GD","Pts");
        System.out.println("---------------------------------------------------------------------------------");
        table.printLeagueTable(); // prints league table
        System.out.println();
        pause();
        mainMenu();
    }


    // Searches for team and prints report
    public static void teamSearch() {
        System.out.print("Please insert a team name or type \"back\": ");
        String input = Keyboard.nextLine(); // stores user input

        switch(input.toLowerCase()) {
            case "back": // if back, return to main menu
                mainMenu();
                break;
            default:
                if(teamReport(input)) // if team report printed successfully, return to main menu
                    mainMenu();
                else { // if unsuccessful print error and allow user to try again
                    error(0, "\"" + input + "\" does not exist");
                    teamSearch();
                }
                break;
        }
    }

    // Prints the individual team report and returns true=successful/false=failed
    public static boolean teamReport(String name) {
        table.updateTeams();
        int index; // declares variable used for storing found teams index
        if((index = table.teamExists(name)) != -1) { // checks if team exists, stores index in variable
            clearConsole();
            table.printTeamReport(index); // print team report
            System.out.println();
            pause();
            return true;
        }
        return false;
    }

    // Displays add menu, allows user to add a result
    public static void add() {
        //gather info from keyboard input
        System.out.print("Please insert the results for row #" + table.nextIndex() + " [home_team_name : away_team_name : home_team_score : away_team_score] or type \"stop\" to go back: ");
        String inputs = Keyboard.nextLine(); // gets user input
        if(inputs.equals("stop")) // Check if the user has typed "stop"
        {
            mainMenu("Your inputs have been added to the table.");
        }
        else {
            table.addResult(inputs); // adds results to table
            add(); // allows multiple entries to be added
        }
    }

    // Allows user to load a file into the table
    public static void load() {
        System.out.print("Please enter the file name (eg. file.txt) or type \"back\": ");
        String input = Keyboard.nextLine(); // gets user input

        switch(input)
        {
            case "back": // if back, return to main menu
                mainMenu();
                break;
            default: // otherwise attempt to load file
                File.loadFile(input);
                break;
        }
    }

    // Allows user to save as text or HTML file
    public static void save() {
        System.out.println("1. Save as a text file.");
        System.out.println("2. Save as a HTML file.");
        System.out.print("Please choose an option or type \"back\": ");
        String input = Keyboard.nextLine(); // gets user input
        switch(input)
        {
            case "1": // if 1, save as text file
                clearConsole();
                System.out.print("Please enter the file name (eg. textfile.txt): ");
                input = Keyboard.nextLine(); // store user input
                File.createTextFile(input); // call File.createTextFile()
                break;
            case "2": // if 2, save as a HTML file
                clearConsole();
                System.out.print("Please enter the file name (eg. webfile): ");
                input = Keyboard.nextLine(); // store user input
                File.createHTML(input+".html"); // call File.createHTML()
                break;
            case "back": // if back, return to main menu
                mainMenu();
                break;
            default: // otherwise, report error: unknown command and allow user to try again
                clearConsole();
                error(1, "UNKNOWN COMMAND");
                save();
                break;
        }

    }

    // Displays an error(optional: option, string, optional: line)
    public static void error(String error) {error(0, error);}
    public static void error(int i, String error) {error(i, error, -1);}
    public static void error(int i, String error, int line) {
        switch (i) {
            case 0: // default error
                System.out.println("ERROR: " + error);
                break;
            case(1): // input error
                if(line == -1)
                    System.out.println("INPUT ERROR: " + error);
                else // if line parameter is given, print error line
                    System.out.println("(LINE #" + line + ") INPUT ERROR: " + error);
                break;
        }

        System.out.println();
    }

    // Waits for enter to be pressed before continuing
    public static void pause() {
        System.out.print("Press \"ENTER\" to continue...");
        Keyboard.nextLine();
    }


    // Prints multiple lines to clear the console
    public static void clearConsole() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

}
