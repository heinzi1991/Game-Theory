import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")


/*
    Das hier ist die Extraklasse, wo alle wichtigen
    Informationen gespeichert sind. Wie zum Beispiel
    die ersten Nimber - Berechtigung, oder die aktuellen
    Höhen der Stapel während des Spiels
 */
public class GameStates {

    private HashMap<Integer, List<Integer>> rules;
    private HashMap<Integer, Integer> nimbers;
    private HashMap<Integer, String> coinsPerStack;
    private HashMap<Integer, List<Integer>> allMoves;
    private HashMap<Integer, List<Integer>> winMoves;

    private String name;
    private String playerToMove;
    private int thePlayer;
    private int playerOne;
    private int playerTwo;
    private int countOfStacks;
    private int XORSum;


    public void gameStates() {
    }

    public void setRules(HashMap<Integer, List<Integer>> gameRules) {

        rules = gameRules;
    }

    public void setNimbers(HashMap<Integer, Integer> gameNimbers) {

        nimbers = gameNimbers;
    }

    public void setName(String gameName) {

        name = gameName;
    }

    public void setPlayerOne(int playerOneCount) {

        playerOne = playerOneCount;
    }

    public void setPlayerTwo(int playerTwoCount) {

        playerTwo = playerTwoCount;
    }

    public void setPlayerToMove(String playerMove) {

        playerToMove = playerMove;
    }

    public void setCountOfStacks(int gameCountOfStacks) {

        countOfStacks = gameCountOfStacks;
    }

    public void setCoinsPerStack(HashMap<Integer, String> gameCoinsPerStack) {

        coinsPerStack = gameCoinsPerStack;
    }

    public void setXORSum(int sum) {

        XORSum = sum;
    }

    public void setMoves(HashMap<Integer, List<Integer>> actualMoves) {

        allMoves = actualMoves;
    }

    public void setWinMoves(HashMap<Integer, List<Integer>> actualWinMoves) {

        winMoves = actualWinMoves;
    }

    public void setThePlayer(int player) {

        thePlayer = player;
    }

    public HashMap<Integer, List<Integer>> getRules() {

        return rules;
    }

    public HashMap<Integer, Integer> getNimbers() {

        return nimbers;
    }

    public String getName() {

        return name;
    }

    public int getPlayerOne() {

        return playerOne;
    }

    public int getPlayerTwo() {

        return playerTwo;
    }

    public String getPlayerToMove() {

        return playerToMove;
    }

    public int getCountOfStacks() {

        return countOfStacks;
    }

    public HashMap<Integer, String> getCoinsPerStack() {

        return coinsPerStack;
    }

    public int getXORSum() {

        return XORSum;
    }

    public HashMap<Integer, List<Integer>> getMoves() {

        return allMoves;
    }

    public HashMap<Integer, List<Integer>> getWinMoves() {

        return winMoves;
    }

    public int getThePlayer() {

        return thePlayer;
    }
}
