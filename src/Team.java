public class Team implements Comparable<Team> {

    // Declares the object properties for the team
    private String teamName;
    private int totalMatches, totalScore, homeScore, awayScore, highestHome, highestAway, goalsConceded, wins, drawn, losses, points;

    // Creates the team
    public Team(String a, int b, int c, boolean away) { // Team Name | Goals Scored | Goals Conceded | Away - True/Home - False

        // Assigns the parameters to the object properties
        teamName = a;
        totalMatches++;
        totalScore = b;
        goalsConceded = c;


        /* If the team:
                - wins
                    Add 1 to wins
                    Add 3 to points
                - loses
                    Add 1 to losses
                - draws
                    Add 1 to drawn
                    Add 1 to points
         */
        if(b>c) {
            wins++;
            points += 3;
        }
        else if(b<c)
            losses++;
        else {
            drawn++;
            points += 1;
        }

        // If the team played away, adds the score to awayScore. If home, adds to homeScore
        if(away)
            awayScore = b;
        else
            homeScore = b;
    }

    // Updates the teams properties
    public void update(int score, int conceded, boolean away) {

        totalScore += score; // adds the score to the total
        totalMatches++; // increments total matches


        /* If the team:
                - wins
                    Add 1 to wins
                    Add 3 to points
                - loses
                    Add 1 to losses
                - draws
                    Add 1 to drawn
                    Add 1 to points
         */
        if(score>conceded) {
            wins++;
            points += 3;
        }
        else if(score<conceded)
            losses++;
        else {
            drawn++;
            points += 1;
        }

        // If the team played away, adds the score to awayScore and checks if it is the highest home score
        // If home, adds to homeScore and checks if it is the highest away score
        if(away) {
            awayScore += score;
            if (score > highestHome)
                highestHome = score;
        }
        else {
            homeScore += score;
            if (score > highestAway)
                highestAway = score;
        }
    }

    // Methods used to return the properties of the team
    public String getName() { return teamName; }
    public int getPoints() { return points; }
    public int getTotalScore() { return totalScore; }
    public int getGoalsConceded() { return goalsConceded; }

    // Prints the team properties
    public void printTeam() {
        System.out.format("%-40s%-5d%-5d%-5d%-5d%-5d%-5d%-5d%-5d\n", teamName, totalMatches, wins, drawn, losses, totalScore, goalsConceded, totalScore-goalsConceded, points);
    }

    // Prints the teams report
    public void printTeamReport() {
        System.out.println(teamName.toUpperCase() + " REPORT");
        System.out.println("----------------------------------------");
        System.out.println("Total matches: " + totalMatches);
        System.out.println("Total score: " + totalScore);
        System.out.println("Total home score: " + homeScore);
        System.out.println("Total away score: " + awayScore);
        System.out.println("Highest home score: " + highestHome);
        System.out.println("Highest away score: " + highestAway);
    }

    // Used in the Arrays.sort to compare each team
    // Sorts them numerically (high-low) by points -> goal difference -> total score
    public int compareTo(Team team) {
        int compareTeam = team.getPoints(); // gets the points of the parameter team and stores in compareTeam
        if(this.points == compareTeam) { // checks if this team has the same points
            compareTeam = team.getTotalScore()-team.getGoalsConceded(); // stores the goal difference

            if(compareTeam == this.getTotalScore()-this.getGoalsConceded()) { // checks if the goal difference is the same
                compareTeam = team.getTotalScore(); // stores the total score
                return compareTeam - this.getTotalScore(); // sorts by total score
            }
            return compareTeam - (this.getTotalScore() - this.getGoalsConceded()); // sorts by goal difference
        }
        return compareTeam - this.points; // sorts by points
    }

}
