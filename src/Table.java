import java.util.Arrays;

public class Table {

    // Declares properties
    private Result[] result; // declares result object
    private Team[] team; // declares team object
    private int results; // result counter
    private int totalTeams; // team counter

    public final int MAX_RESULTS = 100; // maximum amount of results
    private int totalHome = 0, totalAway = 0, highestHome = 0, highestAway = 0, invalidEntries = 0; // declares variables for table

    // Creates the table
    public Table() {
        // Sets initial property values for table
        result = new Result[MAX_RESULTS];
        team = new Team[MAX_RESULTS*2];
        results = 0;
        totalTeams = 0;
    }


//========================================[Result Methods]==============================================

    // Adds a result to the table
    public void addResult(Result a) {
        result[results] = a;
        totalHome += result[results].getHomeScore(); // adds home score of result to totalHome
        totalAway += result[results].getAwayScore(); // adds away score of result to totalAway
        results++; // increments the result counter
        updateHighest();
    }

    // Splits, validates and formats the input for addResult()
    public boolean addResult(String inputs) {return addResult(inputs, -1);}
    public boolean addResult(String inputs, int line) {
        String[] input = inputs.split(" : ");
        if(input[0].equals("") && input.length == 1)
            Display.error(1, "You haven't entered any inputs", line);
        else if(input.length < 4)
            Display.error(1, "You have only entered "+input.length+" input(s), please use the correct format", line);
        else if(input.length > 4)
            Display.error(1, "You have entered "+input.length+" inputs, please use the correct format", line);
        else if(!input[2].matches("^-?\\d+$") || !input[3].matches("^-?\\d+$"))
            Display.error(1, "The score is required to be an integer", line);
        else {
            addResult(new Result(input[0], input[1], Integer.valueOf(input[2]), Integer.valueOf(input[3]))); // adds the result
            return true;
        }

        invalidEntry(); // calls invalidEntry() method
        return false;
    }


    // Deletes all results
    public void wipe() {
        while (results!=0)
            deleteLastResult();
    }

    // Deletes the last result
    public void deleteLastResult() {
        totalHome -= result[results-1].getHomeScore();
        totalAway -= result[results-1].getAwayScore();
        result[currentIndex()] = null;
        results--;
        updateHighest();
    }


    public int nextIndex() { return results; } // returns next result index
    public int currentIndex() { return results-1; } // returns current result index

    public String getString(int i) { return result[i].getString(); } // returns the result.getString method for the specified index
    public String getDataString(int i) { return result[i].getDataString(); } // returns the result.getDataString method for the specified index


    // Methods for returning table properties
    public int totalMatches() { return results; }
    public int totalHomeScores() { return totalHome; }
    public int totalAwayScores() { return totalAway; }
    public int highestHomeScore() { return highestHome; }
    public int highestAwayScore() { return highestAway; }
    public int invalidEntries() { return invalidEntries; }

    // Updates the highestHomeScore and highestAwayScore
    public void updateHighest() {
        for (int i = 0; i <= currentIndex(); i++) { // searches through all rows
            if(result[i].getHomeScore() > highestHomeScore()) // if home score is higher than previous highest, set it as current highest
                highestHome = result[i].getHomeScore();
            if(result[i].getAwayScore() > highestAwayScore()) // if away score is higher than the  previous highest, set it as current highest
                highestAway = result[i].getAwayScore();
        }
    }

    public void invalidEntry() {
        invalidEntries++;
    } // increments the invalidEntries property


    // Prints all results
    public void printResults() {
        for(int i=0; i<=currentIndex(); i++) { // loops through all results
            System.out.print(i+"\t\t"); // prints the index of the result
            result[i].printResult(); // calls the printResult method
            System.out.println(); // ends the line
        }
    }

//========================================[Team Methods]==============================================

    // Creates a team
    public void createTeam(String name, int score, int conceded, boolean away) {
        team[totalTeams] = new Team(name, score, conceded, away);
        totalTeams++;
    }

    // Updates all teams and sorts into the league table
    public void updateTeams() {
        if(totalTeams != 0) // if teams exist, delete all
            while (totalTeams > 0) {
                team[totalTeams-1] = null;
                totalTeams--;
            }

        int teamIndex; // declares variable which stores the teams index
        for(int i = 0; i <= currentIndex(); i++) { // loops through each result and adds the teams
            if(totalTeams == 0) { // if no teams, adds first results' home and away teams
                createTeam(result[i].getHomeName(), result[i].getHomeScore(), result[i].getAwayScore(), false);
                createTeam(result[i].getAwayName(), result[i].getAwayScore(), result[i].getHomeScore(), true);
                continue;
            }

            if ((teamIndex = teamExists(result[i].getHomeName())) != -1) // if home team exists, call team.update()
                team[teamIndex].update(result[i].getHomeScore(), result[i].getAwayScore(), false);
            else // otherwise create new team
                createTeam(result[i].getHomeName(), result[i].getHomeScore(), result[i].getAwayScore(), false);

            if ((teamIndex = teamExists(result[i].getAwayName())) != -1) // if away team exists, call team.update()
                team[teamIndex].update(result[i].getAwayScore(), result[i].getHomeScore(), true);
            else // otherwise create new team
                createTeam(result[i].getAwayName(), result[i].getAwayScore(), result[i].getHomeScore(), true);
        }
        Arrays.sort(team, 0, totalTeams); // Sorts the teams into the league table Highest Points -> Lowest Points
    }

    // If the team exists return index, otherwise return -1
    public int teamExists(String name) {
        if(totalTeams == 0) // if no teams stored return -1
            return -1;
        int index = -1;
        for(int i=0; i<=totalTeams-1; i++) // searches through teams
            if(name.toLowerCase().equals(team[i].getName().toLowerCase())) { // checks if team name equals parameter
                index = i; // sets teams index
                break;
            }
        return index; // returns index or -1
    }

    // Prints a team report
    public void printTeamReport(int i) {
        team[i].printTeamReport(); // calls team.printTeamReport() for team from parameter
    }

    // Prints a league table
    public void printLeagueTable() {
        for(int i=0; i<=totalTeams-1; i++) { // loops through all teams
            team[i].printTeam(); // calls method to print team stats
        }
    }
}
