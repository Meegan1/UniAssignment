public class Result {

    // Declare object properties the result
    private String homeName;
    private String awayName;
    private int homeScore;
    private int awayScore;

    // Creates the result
    public Result(String a, String b, int c, int d) {
        // Assigns the parameters into each object properties
        homeName = a;
        awayName = b;
        homeScore = c;
        awayScore = d;
    }

    // Methods used to return properties of the result
    public String getHomeName() {
        return homeName;
    }
    public String getAwayName() {
        return awayName;
    }
    public int getHomeScore() {
        return homeScore;
    }
    public int getAwayScore() {
        return awayScore;
    }

    public void printResult() { System.out.print(homeName + " [" + homeScore + "] | " + awayName + " [" + awayScore + "]"); } // prints the formatted result
    public String getString() { return homeName + " [" + homeScore + "] | " + awayName+" [" + awayScore + "]"; } // returns the formatted result
    public String getDataString() { return homeName + " : " + awayName + " : " + homeScore + " : " + awayScore; } // returns the result in the format that it was inputted

}
