import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

@SuppressWarnings("ALL")
public class PlayGround extends JFrame {

    private GameStates gameStates;

    public PlayGround(GameStates playGameStates) {

        gameStates = playGameStates;
    }

    /*
        Die Funktion "playTheGame" wird aufgerufen, sobald man
        den playButton geklickt hat. Hier wird zuerst entschieden
        welchen Spiel-Modi man spielt. Mensch vs Mensch | Computer vs Computer
        oder Computer vs Mensch
     */

    public void playTheGame() throws IOException {

        //Play the game Human vs Human
        if (gameStates.getPlayerOne() == 1 && gameStates.getPlayerTwo() == 1) {

            showWelcomeMessage();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.println();
                System.out.println();
                System.out.println("=========================");
                System.out.println("Player " + gameStates.getThePlayer() + " moves");
                System.out.println("=========================");
                System.out.println();

                HashMap<Integer, Integer> nimberAtStack = new HashMap<Integer, Integer>();
                int positionNimber = 0;

                nimberAtStack = lookAtNimber();

                positionNimber = calculateXORSum(nimberAtStack);

                updateGUI();

                if (calculateNotWinMove() != 0) {

                    System.out.println("=========================");
                    System.out.println("Player " + gameStates.getThePlayer() + " loses");
                    System.out.println("=========================");
                    System.out.println();
                    System.exit(0);
                }

                System.out.println("Please enter a number between 0 and " + (gameStates.getMoves().size() - 1) + " : ");
                System.out.print("> ");

                String input = br.readLine();

                if (input.equals("exit")) {

                    System.exit(0);

                } else if (input.equals("hint")) {

                    calculateNimberPostition(positionNimber, nimberAtStack);

                } else if (input != "exit" && input != "hint") {

                    if (Integer.parseInt(input) > gameStates.getMoves().size() - 1) {

                        System.out.println("Please enter a correct number!");
                    }
                    else {

                        int check = makeNotWinMove(gameStates.getMoves(), input);

                        if (check != 0) {

                            System.exit(0);
                        }

                        gameStates.setPlayerToMove("human");

                        if (gameStates.getThePlayer() == 1) {

                            gameStates.setThePlayer(2);
                        } else {

                            gameStates.setThePlayer(1);
                        }
                    }
                }
            }
        }

        //Play the game Computer vs Computer
        if (gameStates.getPlayerOne() == 0 && gameStates.getPlayerTwo() == 0) {

            while (true) {

                System.out.println("=========================");
                System.out.println("Player " + gameStates.getThePlayer() + " moves");
                System.out.println("=========================");
                System.out.println();

                HashMap<Integer, Integer> nimberAtStack = new HashMap<Integer, Integer>();
                int positionNimber = 0;

                nimberAtStack = lookAtNimber();

                positionNimber = calculateXORSum(nimberAtStack);

                updateGUI();

                if (calculateNotWinMove() != 0) {

                    System.out.println("=========================");
                    System.out.println("Player " + gameStates.getThePlayer() + " loses");
                    System.out.println("=========================");
                    System.out.println();
                    System.exit(0);
                }

                if (positionNimber > 0) {

                    calculateNimberPostition(positionNimber, nimberAtStack);

                } else {

                    if (makeNotWinMove(gameStates.getMoves(), "automatic") != 0) {

                        System.exit(0);
                    }
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameStates.setPlayerToMove("computer");

                if (gameStates.getThePlayer() == 1) {

                    gameStates.setThePlayer(2);
                } else {

                    gameStates.setThePlayer(1);
                }
            }
        }

        //Play the game Human vs Computer
        if ((gameStates.getPlayerOne() == 0 && gameStates.getPlayerTwo() == 1) || (gameStates.getPlayerOne() == 1 && gameStates.getPlayerTwo() == 0)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.println();
                System.out.println();
                System.out.println("=========================");
                System.out.println("Player " + gameStates.getThePlayer() + " moves");
                System.out.println("=========================");
                System.out.println();

                HashMap<Integer, Integer> nimberAtStack = new HashMap<Integer, Integer>();
                int positionNimber = 0;

                nimberAtStack = lookAtNimber();

                positionNimber = calculateXORSum(nimberAtStack);

                updateGUI();

                if (calculateNotWinMove() != 0) {

                    System.out.println("=========================");
                    System.out.println("Player " + gameStates.getThePlayer() + " loses");
                    System.out.println("=========================");
                    System.out.println();
                    System.exit(0);
                }

                if (gameStates.getPlayerToMove() == "human") {

                    System.out.println("Please enter a number between 0 and " + (gameStates.getMoves().size() - 1) + " :");
                    System.out.print("> ");

                    String input = br.readLine();

                    if (input.equals("exit")) {

                        System.exit(0);
                    } else if (input != "exit") {

                        if (Integer.parseInt(input) > gameStates.getMoves().size() - 1) {

                            System.out.println("Please enter a correct number!");
                        }
                        else {

                            int check = makeNotWinMove(gameStates.getMoves(), input);

                            if (check != 0) {

                                System.exit(0);
                            }

                            gameStates.setPlayerToMove("computer");

                            if (gameStates.getThePlayer() == 1) {

                                gameStates.setThePlayer(2);
                            } else {

                                gameStates.setThePlayer(1);
                            }
                        }
                    }

                } else {

                    if (positionNimber > 0) {

                        calculateNimberPostition(positionNimber, nimberAtStack);
                    } else {

                        if (makeNotWinMove(gameStates.getMoves(), "automatic") != 0) {

                            System.exit(0);
                        }
                    }

                    gameStates.setPlayerToMove("human");

                    if (gameStates.getThePlayer() == 1) {

                        gameStates.setThePlayer(2);
                    } else {

                        gameStates.setThePlayer(1);
                    }
                }
            }
        }
    }

    /*
        Jede bestimmte Stapelhöhe hat eine bestimmte Nimber
        daher schauen wir bei jeden Stapel nach welche Höhe
        dieser besitzt und schauen dann welche Nimber dieser
        Stapel besitzt
     */

    public HashMap<Integer, Integer> lookAtNimber() {

        HashMap<Integer, Integer> stackNimber = new HashMap<Integer, Integer>();

        for (int i = 1; i <= gameStates.getCoinsPerStack().size(); i++) {

            stackNimber.put(i, gameStates.getNimbers().get(Integer.parseInt(gameStates.getCoinsPerStack().get(i))));
        }

        return stackNimber;
    }

    /*
        Für die aktulle Nimber Stellung, also ist es eine Nimber 0
        Stellung oder eine nicht Nimber 0 Stellung müssen wir nur
        die Nimbers der aktuellen Stampel XOR'n
     */

    public int calculateXORSum(HashMap<Integer, Integer> stackNimber) {

        int XORSum = 0;

        for (int i = 1; i <= stackNimber.size(); i++) {

            XORSum = XORSum ^ stackNimber.get(i);
        }

        gameStates.setXORSum(XORSum);

        return XORSum;
    }

    /*
        Ganz einfach: mit der XORSum Nimber schauen wir bei welchen Stapel
        man einen Win-Move machen kann!
     */

    public void calculateNimberPostition(int XORSum, HashMap<Integer, Integer> stackNimber) {

        HashMap<Integer, Integer> playMoveNimber = new HashMap<Integer, Integer>();
        HashMap<Integer, List<Integer>> possibleMoves = new HashMap<Integer, List<Integer>>();

        int heighPossibleMove = 999;

        for (int i = 1; i <= stackNimber.size(); i++) {

            int XORMoveNim = 0;

            XORMoveNim = stackNimber.get(i) ^ XORSum;

            playMoveNimber.put(i, XORMoveNim);

            possibleMoves.put(i, calculateNimber(gameStates.getRules(), i, playMoveNimber.get(i)));
        }

        HashMap<Integer, List<Integer>> allWinningMoves = calculateWinMove(possibleMoves);

        if (gameStates.getPlayerToMove() == "computer") {

            makeWinMove(allWinningMoves);
        }

    }

    /*
        Gleich wie bei der ersten Nimber Berechnung
     */

    private List<Integer> calculateNimber(HashMap<Integer, List<Integer>> rules, int stack, int nimber) {

        int i = Integer.parseInt(gameStates.getCoinsPerStack().get(stack));
        List<Integer> move = new ArrayList<Integer>();
        int counter = 0;

        ArrayList<Integer> nimbers = new ArrayList();

        for (int j = 0; j < rules.size(); j++) {

            for (int k = rules.get(j).get(0); k <= rules.get(j).get(1); k++) {

                if (i >= k) {

                    int newHigh = i - k;

                    List<Integer> sliceRule = rules.get(j);
                    List<Integer> sliceCount = sliceRule.subList(2, sliceRule.size());

                    if (sliceRule.get(sliceRule.size() - 1) > 1) {

                        ArrayList<Integer> twoslicenimbers = calcNimerSlice(newHigh, sliceRule, gameStates.getNimbers(), nimber, j, k);
                        move.addAll(twoslicenimbers);
                    }

                    if (sliceCount.contains(1)) {

                        if (gameStates.getNimbers().get(newHigh) == nimber) {

                            move.add(j);
                            move.add(k);
                            move.add(0);
                            move.add(0);
                            move.add(0);
                            move.add(0);
                            move.add(999);

                            counter++;
                        }
                    }

                    if (sliceCount.contains(0) && newHigh == 0) {

                        if (gameStates.getNimbers().get(newHigh) == nimber) {

                            move.add(j);
                            move.add(k);
                            move.add(0);
                            move.add(0);
                            move.add(0);
                            move.add(0);
                            move.add(999);

                            counter++;
                        }
                    }
                }
            }
        }
        return move;
    }


    /*
        Gleich wie bei der ersten Nimber Berechnung
     */

    private static ArrayList<Integer> calcNimerSlice(int height, List<Integer> sliceRules, HashMap<Integer, Integer> nimbers, int nimber, int rule, int coins) {

        List<Integer> slicingCount = sliceRules.subList(2, sliceRules.size());
        ArrayList<Integer> returnnimbers = new ArrayList<Integer>();

        for (int i = 0; i < slicingCount.size(); i++) {

            switch (slicingCount.get(i)) {

                case 0:

                    break;

                case 1:

                    break;

                case 2:

                    ArrayList<Integer> two = TwoSlice(height, nimbers, nimber, rule, coins);
                    returnnimbers.addAll(two);

                    break;

                case 3:

                    ArrayList<Integer> three = ThreeSlice(height, nimbers, nimber, rule, coins);
                    returnnimbers.addAll(three);

                    break;

                case 4:

                    ArrayList<Integer> four = FourSlice(height, nimbers, nimber, rule, coins);
                    returnnimbers.addAll(four);

                    break;

                default:

                    System.out.println("Das darf nie gelesen werden!!");
            }
        }
        return returnnimbers;
    }

    /*
        Gleich wie bei der ersten Nimber Berechnung
     */

    private static ArrayList<Integer> TwoSlice(int height, HashMap<Integer, Integer> nimbers, int nimber, int rule, int coins) {

        int i = 1;
        int j = height - 1;

        ArrayList<Integer> nimberOpportunities = new ArrayList();

        if (height >= 2) {

            while (i <= j) {

                int nimberstack1 = nimbers.get(i);
                int nimberstack2 = nimbers.get(j);
                int nimberSlice = nimberstack1 ^ nimberstack2;

                if (nimberSlice == nimber) {

                    nimberOpportunities.add(rule);
                    nimberOpportunities.add(coins);
                    nimberOpportunities.add(i);
                    nimberOpportunities.add(j);
                    nimberOpportunities.add(0);
                    nimberOpportunities.add(0);
                    nimberOpportunities.add(999);
                }

                i++;
                j--;
            }

        }
        return nimberOpportunities;
    }

    /*
        Gleich wie bei der ersten Nimber Berechnung
     */

    private static ArrayList<Integer> ThreeSlice(int height, HashMap<Integer, Integer> nimbers, int nimber, int rule, int coins) {

        int abort = height / 3;

        ArrayList<Integer> nimberOpportunities = new ArrayList<Integer>();

        if (height >= 3) {

            for (int i = 1; i <= abort; i++) {

                int j = i;
                int k = height - (i + j);

                while (j <= k) {

                    int nimberStack2 = nimbers.get(j);
                    int nimberStack3 = nimbers.get(k);

                    int firstXOR = nimberStack2 ^ nimberStack3;
                    int secondXOR = firstXOR ^ nimbers.get(i);

                    if (secondXOR == nimber) {

                        nimberOpportunities.add(rule);
                        nimberOpportunities.add(coins);
                        nimberOpportunities.add(i);
                        nimberOpportunities.add(j);
                        nimberOpportunities.add(k);
                        nimberOpportunities.add(0);
                        nimberOpportunities.add(999);
                    }

                    j++;
                    k--;
                }
            }
        }
        return nimberOpportunities;
    }

    /*
        Gleich wie bei der ersten Nimber Berechnung
     */

    private static ArrayList<Integer> FourSlice(int height, HashMap<Integer, Integer> nimbers, int nimber, int rule, int coins) {

        ArrayList<Integer> nimberOpportunities = new ArrayList<Integer>();

        int abort = height / 4;

        if (height >= 4) {

            for (int i = 1; i <= abort; i++) {

                for (int j = i; j <= (abort + 1); j++) {

                    int k = j;
                    int l = height - (i + j + k);

                    while (k <= l) {

                        int nimberStack3 = nimbers.get(k);
                        int nimberStack4 = nimbers.get(l);

                        int firstXOR = nimberStack3 ^ nimberStack4;
                        int secondXOR = firstXOR ^ nimbers.get(j);
                        int thirdXOR = secondXOR ^ nimbers.get(i);

                        if (thirdXOR == nimber) {

                            nimberOpportunities.add(rule);
                            nimberOpportunities.add(coins);
                            nimberOpportunities.add(i);
                            nimberOpportunities.add(j);
                            nimberOpportunities.add(k);
                            nimberOpportunities.add(l);
                            nimberOpportunities.add(999);
                        }

                        k++;
                        l--;
                    }
                }
            }
        }
        return nimberOpportunities;
    }

    /*
        Wie die Funktion schon zeigt, hier berechnen wir alle möglichen Win-Moves
        und diese werden dann in einer "Konsolen-Tablle" angezeigt
     */

    public HashMap<Integer, List<Integer>> calculateWinMove(HashMap<Integer, List<Integer>> winningMoves) {

        Set<Integer> allMoveKeys = winningMoves.keySet();
        Integer[] keys = allMoveKeys.toArray(new Integer[allMoveKeys.size()]);

        HashMap<Integer, List<Integer>> allMoves = new HashMap<Integer, List<Integer>>();

        int counter = 0;
        int moreMoveCounter = 0;
        int moveListSize = 0;

        for (int i = 0; i < keys.length; i++) {

            List<Integer> tempMove = winningMoves.get(keys[i]);

            moveListSize = tempMove.size();

            if (tempMove.size() == 5) {

                tempMove.remove(tempMove.size() - 1);
                tempMove.add(keys[i]);

                allMoves.put(counter, tempMove);
                counter++;
            } else {

                for (int j = 0; j < moveListSize; j++) {

                    if (tempMove.get(j) < 999) {

                        moreMoveCounter++;
                    }

                    if (tempMove.get(j) == 999) {

                        List<Integer> tempMove1 = tempMove.subList((j - moreMoveCounter), j + 1);

                        tempMove1.set(tempMove1.size() - 1, keys[i]);

                        allMoves.put(counter, tempMove1);
                        counter++;
                        moreMoveCounter = 0;

                    }
                }
            }

            moveListSize = 0;
        }

        gameStates.setWinMoves(allMoves);

        System.out.println("==========================================================");
        System.out.println("==================== WINNING MOVES =======================");
        System.out.println("=|------------------------------------------------------|=");
        System.out.println("=|   Number   |                    Move                 |=");
        System.out.println("=|------------------------------------------------------|=");

        for (int i = 0; i < allMoves.size(); i++) {

            String stack = Integer.toString(allMoves.get(i).get(6));
            String coin = Integer.toString(allMoves.get(i).get(1));
            String rules = Integer.toString(allMoves.get(i).get(0));
            String split1 = Integer.toString(allMoves.get(i).get(2));
            String split2 = Integer.toString(allMoves.get(i).get(3));
            String split3 = Integer.toString(allMoves.get(i).get(4));
            String split4 = Integer.toString(allMoves.get(i).get(5));

            String move = "Stack: " + stack + " Coin(s): " + coin + " Split: " + split1 + " : " + split2 + " : " + split3 + " : " + split4;


            System.out.println("=|      " + i + "     | " + move + "|=");
            System.out.println("=|------------------------------------------------------|=");
        }

        System.out.println("==========================================================");
        System.out.println();

        return allMoves;

    }

    /*
        In dieser Funktion nehmen wir einen random ausgesuchten Win-Move und
        führen dieses dann aus
     */

    public void makeWinMove(HashMap<Integer, List<Integer>> winMoves) {

        Random ran = new Random();

        int randomNumber = ran.nextInt(winMoves.size());

        if (winMoves.get(randomNumber).get(2) == 0) {

            HashMap<Integer, String> stackMap = gameStates.getCoinsPerStack();

            int newHeight = Integer.parseInt(stackMap.get(winMoves.get(randomNumber).get(6))) - winMoves.get(randomNumber).get(1);

            stackMap.put(winMoves.get(randomNumber).get(6), Integer.toString(newHeight));

            gameStates.setCoinsPerStack(stackMap);
        }

        if (winMoves.get(randomNumber).get(2) != 0) {

            HashMap<Integer, String> stackMap = gameStates.getCoinsPerStack();

            int newHeight = winMoves.get(randomNumber).get(2);

            stackMap.put(winMoves.get(randomNumber).get(6), Integer.toString(newHeight));

            for (int i = 3; i < winMoves.get(randomNumber).size() - 1; i++) {

                int stackCount = stackMap.size();

                if (winMoves.get(randomNumber).get(i) != 0) {

                    stackMap.put(stackCount + 1, Integer.toString(winMoves.get(randomNumber).get(i)));
                }

            }
            gameStates.setCoinsPerStack(stackMap);
        }
    }

    /*
        Hier das gleiche, in dieser Funktion schauen wir und berechnen
        wir alle legalen Moves, natürlich kann es sein und soll sogar so
        sein, dass in diesen Moves auch Win-Moves drinstecken können.
        Dann zeigen wir auch diese Moves in einer "Konsolen-Tabelle" an.
     */

    public int calculateNotWinMove() {

        Set<Integer> allStackKeys = gameStates.getCoinsPerStack().keySet();
        Integer[] keys = allStackKeys.toArray(new Integer[allStackKeys.size()]);

        HashMap<Integer, List<Integer>> notWinMoves = new HashMap<Integer, List<Integer>>();
        int counter = 0;

        for (int i = 0; i < keys.length; i++) {

            for (int j = 0; j < gameStates.getRules().size(); j++) {

                List<Integer> rules = gameStates.getRules().get(j);
                List<Integer> sliceRules = rules.subList(2, rules.size());

                for (int k = rules.get(0); k <= rules.get(1); k++) {

                    List<Integer> moveStates1 = new ArrayList<Integer>();

                    if (Integer.parseInt(gameStates.getCoinsPerStack().get(keys[i])) >= k) {

                        int newHeight = Integer.parseInt(gameStates.getCoinsPerStack().get(keys[i])) - k;

                        if (sliceRules.get(sliceRules.size() - 1) > 1) {

                            for (int l = 0; l < sliceRules.size(); l++) {

                                if (sliceRules.get(l) > 1) {

                                    if (newHeight / sliceRules.get(l) >= 1) {

                                        ArrayList<Integer> test = calculateNotWinSplit(newHeight, sliceRules.get(l));

                                        for (int m = 0; m < test.size(); m++) {

                                            if (test.get(m) == 999) {

                                                List<Integer> moveStates = new ArrayList<Integer>();

                                                moveStates.add(j);
                                                moveStates.add(k);
                                                moveStates.add(test.get(m - 4));
                                                moveStates.add(test.get(m - 3));
                                                moveStates.add(test.get(m - 2));
                                                moveStates.add(test.get(m - 1));
                                                moveStates.add(keys[i]);

                                                notWinMoves.put(counter, moveStates);
                                                counter++;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (sliceRules.contains(1)) {

                            moveStates1.add(j);
                            moveStates1.add(k);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(keys[i]);

                            notWinMoves.put(counter, moveStates1);
                            counter++;
                        }

                        if (sliceRules.contains(0) && newHeight == 0) {

                            moveStates1.add(j);
                            moveStates1.add(k);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(0);
                            moveStates1.add(keys[i]);

                            notWinMoves.put(counter, moveStates1);
                            counter++;
                        }
                    }
                }
            }
        }

        if (notWinMoves.size() == 0) {

            return 1;
        }

        if (gameStates.getPlayerToMove() == "human") {

            int loopCounter = 0;

            System.out.println("===================================================================================================================");
            System.out.println("=================================================== YOUR MOVES ====================================================");
            System.out.println("=|-------------------------------------------------------|------------|------------------------------------------|=");
            System.out.println("=|   Number   |                    Move                  |   Number   |                    Move                  |=");
            System.out.println("=|-------------------------------------------------------|------------|------------------------------------------|=");

            for (int i = 0; i < notWinMoves.size() - 1; i += 2) {

                String stack1 = Integer.toString(notWinMoves.get(i).get(6));
                String coin1 = Integer.toString(notWinMoves.get(i).get(1));
                String rules1 = Integer.toString(notWinMoves.get(i).get(0));
                String split11 = Integer.toString(notWinMoves.get(i).get(2));
                String split21 = Integer.toString(notWinMoves.get(i).get(3));
                String split31 = Integer.toString(notWinMoves.get(i).get(4));
                String split41 = Integer.toString(notWinMoves.get(i).get(5));

                String move1 = "Stack: " + stack1 + " Coin(s): " + coin1 + " Split: " + split11 + " : " + split21 + " : " + split31 + " : " + split41;

                String stack2 = Integer.toString(notWinMoves.get(i + 1).get(6));
                String coin2 = Integer.toString(notWinMoves.get(i + 1).get(1));
                String rules2 = Integer.toString(notWinMoves.get(i + 1).get(0));
                String split12 = Integer.toString(notWinMoves.get(i + 1).get(2));
                String split22 = Integer.toString(notWinMoves.get(i + 1).get(3));
                String split32 = Integer.toString(notWinMoves.get(i + 1).get(4));
                String split42 = Integer.toString(notWinMoves.get(i + 1).get(5));

                String move2 = "Stack: " + stack2 + " Coin(s): " + coin2 + " Split: " + split12 + " : " + split22 + " : " + split32 + " : " + split42;

                System.out.println("=|      " + i + "     | " + move1 + " |      " + (i + 1) + "     | " + move2 + " |=");
                System.out.println("=|---------------------------------------------------------------------------------------------------------------|=");
                loopCounter += 2;

                if (loopCounter == (notWinMoves.size() - 1)) {

                    String stack3 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(6));
                    String coin3 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(1));
                    String rules3 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(0));
                    String split13 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(2));
                    String split23 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(3));
                    String split33 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(4));
                    String split43 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(5));

                    String move3 = "Stack: " + stack3 + " Coin(s): " + coin3 + " Split: " + split13 + " : " + split23 + " : " + split33 + " : " + split43;

                    System.out.println("=|      " + (notWinMoves.size() - 1) + "     | " + move3 + " |            |                                          |=");
                }

            }

            if (notWinMoves.size() == 1) {

                String stack4 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(6));
                String coin4 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(1));
                String rules4 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(0));
                String split14 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(2));
                String split24 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(3));
                String split34 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(4));
                String split44 = Integer.toString(notWinMoves.get(notWinMoves.size() - 1).get(5));

                String move4 = "Stack: " + stack4 + " Coin(s): " + coin4 + " Split: " + split14 + " : " + split24 + " : " + split34 + " : " + split44;

                System.out.println("=|      " + (notWinMoves.size() - 1) + "     | " + move4 + " |            |                                          |=");
            }

            System.out.println("===================================================================================================================");
            System.out.println();
        }

        gameStates.setMoves(notWinMoves);
        return 0;
    }

    /*
        Dann suchen wir einen random NotWinMove aus und führen diesen aus.
     */

    public int makeNotWinMove(HashMap<Integer, List<Integer>> notWinMoves, String moveCount) {

        int moveInt = 0;

        if (notWinMoves.size() == 0) {

            return 1;
        }

        if (moveCount == "automatic") {

            Random ran = new Random();

            moveInt = ran.nextInt(notWinMoves.size());
        } else {

            moveInt = Integer.parseInt(moveCount);
        }

        List<Integer> notWinMoveStates = notWinMoves.get(moveInt);

        if (notWinMoveStates.get(2) == 0) {

            HashMap<Integer, String> stackMap = gameStates.getCoinsPerStack();

            int newHeight = Integer.parseInt(stackMap.get(notWinMoves.get(moveInt).get(6))) - notWinMoves.get(moveInt).get(1);

            stackMap.put(notWinMoves.get(moveInt).get(6), Integer.toString(newHeight));

            gameStates.setCoinsPerStack(stackMap);
        }

        if (notWinMoveStates.get(2) != 0) {

            HashMap<Integer, String> stackMap = gameStates.getCoinsPerStack();

            int newHeight = notWinMoves.get(moveInt).get(2);

            stackMap.put(notWinMoves.get(moveInt).get(6), Integer.toString(newHeight));

            for (int i = 3; i < notWinMoves.get(moveInt).size() - 1; i++) {

                int stackCount = stackMap.size();

                if (notWinMoves.get(moveInt).get(i) != 0) {

                    stackMap.put(stackCount + 1, Integer.toString(notWinMoves.get(moveInt).get(i)));
                }
            }

            gameStates.setCoinsPerStack(stackMap);
        }
        return 0;
    }

    /*
        Hier werden nur die NotWinMove Slice Höhen zurückgegeben. Also
        alle möglichen neuen Höhen.
     */

    public ArrayList<Integer> calculateNotWinSplit(int height, int slice) {

        ArrayList<Integer> test = new ArrayList<Integer>();

        switch (slice) {

            case 2:

                int stack1 = 1;
                int stack2 = height - 1;

                while (stack1 <= stack2) {

                    test.add(stack1);
                    test.add(stack2);
                    test.add(0);
                    test.add(0);
                    test.add(999);

                    stack1++;
                    stack2--;
                }
                break;

            case 3:

                int abort3 = height / 3;

                for (int i = 1; i <= abort3; i++) {

                    int stack02 = i;
                    int stack03 = height - (stack02 + i);

                    while (stack02 <= stack03) {

                        test.add(i);
                        test.add(stack02);
                        test.add(stack03);
                        test.add(0);
                        test.add(999);

                        stack02++;
                        stack03--;
                    }
                }
                break;

            case 4:

                int abort4 = height / 4;

                for (int j = 1; j <= abort4; j++) {

                    for (int k = j; k <= (abort4 + 1); k++) {

                        int stack003 = k;
                        int stack004 = height - (stack003 + k + j);

                        while (stack003 <= stack004) {

                            test.add(j);
                            test.add(k);
                            test.add(stack003);
                            test.add(stack004);
                            test.add(999);

                            stack003++;
                            stack004--;
                        }
                    }
                }
                break;

            default:

                System.out.println("Das darf niemand lesen");
                break;
        }
        return test;
    }

    /*
        Update GUI macht nur, das immmer die aktuellste Spielstellung
        "grafisch" dargestellt wird
     */

    public void updateGUI() {

        System.out.println("Nimber Position: " + gameStates.getXORSum());
        System.out.println();

        Set<Integer> allStackKeys = gameStates.getCoinsPerStack().keySet();
        Integer[] keys = allStackKeys.toArray(new Integer[allStackKeys.size()]);

        int tempHeight = Integer.parseInt(gameStates.getCoinsPerStack().get(keys[0]));

        for (int i = 0; i < keys.length; i++) {

            if (tempHeight < Integer.parseInt(gameStates.getCoinsPerStack().get(keys[i]))) {

                tempHeight = Integer.parseInt(gameStates.getCoinsPerStack().get(keys[i]));
            }
        }

        int tempHeight1 = tempHeight;

        for (int i = 0; i < tempHeight; i++) {

            for (int j = 0; j < keys.length; j++) {

                if (tempHeight1 > Integer.parseInt(gameStates.getCoinsPerStack().get(keys[j]))) {

                    System.out.print("    ");
                } else {

                    System.out.print("[  ]");
                }
            }
            tempHeight1--;
            System.out.println();
        }

        for (int i = 0; i < keys.length; i++) {

            if (keys[i] >= 10) {

                System.out.print(" " + keys[i] + " ");
            } else {

                System.out.print(" " + "0" + keys[i] + " ");
            }
        }
        System.out.println();

        for (int i = 0; i < keys.length; i++) {

            System.out.print("====");
        }
        System.out.println();
        System.out.println();
    }

    public void showWelcomeMessage() {

        System.out.println();
        System.out.println("          *   *  *****  *   *  ****    ****  ****          ");
        System.out.println("          **  *    *    ** **  *   *  *      *   *         ");
        System.out.println("          * * *    *    * * *  ****   *****  ****          ");
        System.out.println("          *  **    *    *   *  *   *  *      *  *          ");
        System.out.println("          *   *  *****  *   *  ****    ****  *   *         ");
        System.out.println();
        System.out.println();
    }
}