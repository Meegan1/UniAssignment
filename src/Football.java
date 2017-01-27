public class Football {
    public static Table table = new Table(); // creates the table object

    public static void main(String[] args) {

        // Adds the examples from the assessment brief
        table.addResult(new Result("Manchester City", "Manchester United", 2, 1));
        table.addResult(new Result("Chelsea", "Arsenal", 0, 1));
        table.addResult(new Result("Everton", "Liverpool", 0, 1));

        Display.mainMenu(); // displays the main menu
    }

    // Exits the program
    public static void exit()
    {
        Keyboard.close(); // closes the scanner
        System.exit(0); // exits
    }
}