package ses.candycrush.model;


public class Util {

    public static CandyCrushGame createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        int rows = lines.size();
        int cols = lines.getFirst().length();
        var model = new CandyCrushGame(rows, cols);
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.setCandyAt(row, col, characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }

    public static int characterToCandy(char c) {
        return switch (c) {
            case '.' -> 0; /* NoCandy */
            case 'o' -> 1; /* NormalCandy(0) */
            case '*' -> 2; /* NormalCandy(1) */
            case '#' -> 3; /* NormalCandy(2) */
            case '@' -> 4; /* NormalCandy(3) */
            case 'x' -> 5; /* RowSnapper */
            case '+' -> 6; /* MultiCandy */
            case '$' -> 7; /* RareCandy */
            case '&' -> 8; /* TurnMaster */
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }


    public static String candyToString(int c) {
        return switch (c) {
            case 0 -> "."; /* NoCandy */
            case 1 -> "o"; /* NormalCandy(0) */
            case 2 -> "*"; /* NormalCandy(1) */
            case 3 -> "#"; /* NormalCandy(2) */
            case 4 -> "@"; /* NormalCandy(3) */
            case 5 -> "x"; /* RowSnapper */
            case 6 -> "+"; /* MultiCandy */
            case 7 -> "$"; /* RareCandy */
            case 8 -> "&"; /* TurnMaster */
            default -> throw new IllegalArgumentException();
        };
    }

}