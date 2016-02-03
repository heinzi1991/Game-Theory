import java.io.*;
import java.util.*;

@SuppressWarnings("ALL")
public class Main {

    /*
        Textdatei wird eingelesen und gespaltet um zu schauen wie
        die Regeln ausschauen;
        Steht INF in den Regeln wird das Wort mit 400 ersetzt
     */

    public static void main(String[] args) throws InterruptedException {

        GameStates gameStates = new GameStates();

        BufferedReader br = null;
        String name = "";
        String[] parts = new String[1000];

        ArrayList nameList = new ArrayList();
        ArrayList<Integer> numbers = new ArrayList();

        HashMap<Integer, Integer> mainNimber = new HashMap<Integer, Integer>();

        try {

            br = new BufferedReader(new FileReader(new File(args[0])));

            String line = null;

            while ((line = br.readLine()) != null) {

                parts = line.split(" ");

                for (int i = 0; i < parts.length; i++) {

                    if (parts[i].equals("INF")) {

                        numbers.add(400);
                    }

                    else if (parts[i].matches("\\d+")) {

                        numbers.add(Integer.parseInt(parts[i]));
                    }
                    else {

                        nameList.add(parts[i]);
                    }
                }

                numbers.add(999);
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (br != null) {

                try {

                    br.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }

       /* for (int i = 0; i < nameList.size(); i++) {

            name += (nameList.get(i) + " ");
        } */


        /*
            Da werden die ersten und die wichtigsten Nimbers für
            einen Stapel mit der Höhe von 400 berechnet
         */
        mainNimber = calculateNimber(numbers, gameStates);

        /*
            Da speichern wir es alles in eine extra Klasse ab
            damit wir alles haben für das spätere Spiel
         */
        gameStates.setName(name);
        gameStates.setNimbers(mainNimber);

        NimberCalc nimberCalculation = new NimberCalc(gameStates);
        GameInfo gameSettings = new GameInfo(gameStates);

    }


    /*
        Hier werden nun die Nimbers berechnet
     */
    private static HashMap calculateNimber(ArrayList<Integer> rules, GameStates gameState) {

        HashMap<Integer, Integer> map = new HashMap();
        HashMap<Integer, List<Integer>> nimberRules = new HashMap<Integer, List<Integer>>();

        ArrayList<Integer> sliceRules = new ArrayList<Integer>();
        ArrayList<Integer> temp = new ArrayList<Integer>();

        int keyCount = 0;
        int check = 0;
        int counter = 0;

        for (int i = 0; i < rules.size(); i++) {

            if (rules.get(i) < 999) {

                counter++;
            }

            if (rules.get(i) == 999) {

                check = i;

                nimberRules.put(keyCount, rules.subList(check - counter, i));
                keyCount++;
                counter = 0;
            }
        }

        /*
            Hier werden die Regeln in die extra Klasse gespeichert
         */
        gameState.setRules(nimberRules);


        //nimbers all 0 before m1
        for (int i = 0; i < rules.get(0); i++) {

            map.put(i, 0);
        }

        //hight 0 - 400
        for (int i = rules.get(0); i <= 400; i++) {

            ArrayList<Integer> nimbers = new ArrayList();

            for (int j = 0; j < nimberRules.size(); j++) {

                //first stack, pop coins
                for (int k = nimberRules.get(j).get(0); k <= nimberRules.get(j).get(1); k++) {

                    if (i >= k) {

                        int newHigh = i - k;

                        List<Integer> sliceRule = nimberRules.get(j);
                        List<Integer> sliceCount = sliceRule.subList(2, sliceRule.size());

                        if (sliceRule.get(sliceRule.size() - 1) > 1) {

                            ArrayList<Integer> twoslicenimbers = calcNimerSlice(newHigh, sliceRule, map);

                            nimbers.addAll(twoslicenimbers);
                        }

                        if (sliceCount.contains(1)) {

                            nimbers.add(map.get(newHigh));
                        }

                        if (sliceCount.contains(0) && newHigh == 0) {

                            nimbers.add(map.get(newHigh));
                        }
                    }
                }
            }

            Collections.sort(nimbers);

            HashSet<Integer> nimberSet = new HashSet<Integer>(nimbers);
            ArrayList<Integer> nimberWithoutDub = new ArrayList<Integer>(nimberSet);

            System.out.println("NimberWithoutDubSize: " + nimberWithoutDub.size());

            boolean numberbetween = false;

            //looks for the lowest nimber
            for (int k = 0; k < nimberWithoutDub.size(); k++) {

                int test = nimberWithoutDub.get(k);

                if (test != k) {

                    map.put(i, k);
                    numberbetween = true;
                    break;
                }
            }

            if (numberbetween == false && nimberWithoutDub.size() > 0) {

                int test = nimberWithoutDub.get(nimberWithoutDub.size() - 1) + 1;
                map.put(i, test);
            }
            else {

                int test = 0;
                map.put(i, test);
            }
        }

        return map;
    }


    /*
        In dieser Funktion gehen wir alle Slice Regeln durch
     */

    private static ArrayList<Integer> calcNimerSlice(int height, List<Integer> sliceRules, HashMap<Integer, Integer> nimbers) {

        List<Integer> slicingCount = sliceRules.subList(2, sliceRules.size());
        ArrayList<Integer> returnnimbers = new ArrayList<Integer>();

        for (int i = 0; i < slicingCount.size(); i++) {

            switch (slicingCount.get(i)) {

                case 0:

                    break;

                case 1:

                    break;

                case 2:

                    ArrayList<Integer> two = TwoSlice(height, nimbers);
                    returnnimbers.addAll(two);
                    break;

                case 3:

                    ArrayList<Integer> three = ThreeSlice(height, nimbers);
                    returnnimbers.addAll(three);
                    break;

                case 4:

                    ArrayList<Integer> four = FourSlice(height, nimbers);
                    returnnimbers.addAll(four);
                    break;

                default:

                    System.out.println("Das darf nie gelesen werden!!");
            }
        }

        return returnnimbers;
    }

    /*
        Steht in der Regel eine "2", dann kann man den Stapel einmal
        splitten
    */

    private static ArrayList<Integer> TwoSlice(int height, HashMap<Integer, Integer> nimbers) {

        int i = 1;
        int j = height - 1;

        ArrayList<Integer> nimberOpportunities = new ArrayList();

        if (height >= 2) {

            while (i <= j) {

                int nimberstack1 = nimbers.get(i);
                int nimberstack2 = nimbers.get(j);
                int nimberSlice = nimberstack1^nimberstack2;

                //System.out.println("NimberSlice2 at " + height + ": " + nimberSlice);

                nimberOpportunities.add(nimberSlice);

                i++;
                j--;
            }

        }

        return nimberOpportunities;
    }

    /*
        Steht in der Regel eine "3", dann kann man den Stapel zweimal
        splitten
     */

    private static ArrayList<Integer> ThreeSlice(int height, HashMap<Integer, Integer> nimbers) {

        int abort = height / 3;

        ArrayList<Integer> nimberOpportunities = new ArrayList<Integer>();

        if (height >= 3) {

            for (int i = 1; i <= abort; i++) {

                int j = i;
                int k = height - (i + j);

                while (j <= k) {

                    int nimberStack2 = nimbers.get(j);
                    int nimberStack3 = nimbers.get(k);

                    int firstXOR = nimberStack2^nimberStack3;
                    int secondXOR = firstXOR^nimbers.get(i);

                    //System.out.println("NimberSlice3 at " + height + ": " + secondXOR);

                    nimberOpportunities.add(secondXOR);

                    j++;
                    k--;
                }
            }
        }

        return nimberOpportunities;
    }

    /*
        Steht in der Regel eine "4" dann kann man den Stapel dreimal
        splitten
     */

    private static ArrayList<Integer> FourSlice(int height, HashMap<Integer, Integer> nimbers) {

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

                        int firstXOR = nimberStack3^nimberStack4;
                        int secondXOR = firstXOR^nimbers.get(j);
                        int thirdXOR = secondXOR^nimbers.get(i);

                        //System.out.println("NimberSlice4 at " + height + ": " + thirdXOR);

                        nimberOpportunities.add(thirdXOR);

                        k++;
                        l--;
                    }
                }
            }
        }

        return nimberOpportunities;
    }
}
