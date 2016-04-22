package hearthladdersim;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    /**
     * index X = stars needed to reach rank X 
     */
    private static final int[] STAR_REQS = {
            5, //0
            5, //1
            5, //2
            5, //3
            5, //4
            5, //5
            5, //6
            5, //7
            5, //8
            5, //9
            4, //10
            4, //11
            4, //12
            4, //13
            4, //14
            3, //15
            3, //16
            3, //17
            3, //18
            3, //19
    };

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        final int testCount = 1000000;
        ArrayList<String> output = new ArrayList<String>();
        output.add("");
        for (Strategy s : Strategy.values()) {
            output.addAll(simulate(testCount, s));
            output.add("");
        }
        for (String s : output)
            System.out.println(s);
    }

    public static ArrayList<String> simulate(int testCount, Strategy strategy) {
        System.out.println("Beginning simulation of " + strategy.toString() + " - " + strategy.getDescription());
        double[] statsArr = new double[6]; //count, time, longestWinStreak, longestLoseStreak};
        int statusUpdate = (int) (testCount * 0.05);
        for (int k = 0; k < testCount; k++) {
            int[] temp = simulateClimb(strategy, false);
            for (int j = 0; j < temp.length; j++)
                statsArr[j] += temp[j];
            if ((k + 1) % statusUpdate == 0)
                System.out.println("Testing run " + (k + 1));
        }
        for (int k = 0; k < statsArr.length; k++) {
            statsArr[k] = statsArr[k] / testCount;
        }
        int hours = (int) (statsArr[1]) / 60;
        int minutes = (int) (statsArr[1]) % 60;
        ArrayList<String> output = new ArrayList<String>();
        output.add("Simulated " + testCount + " Rank 20 -> Legend runs.");
        output.add("Strategy: " + strategy.toString() + " - " + strategy.getDescription());
        output.add("Averages: " + statsArr[0] + " games. " + hours + " hours & " + minutes + " minutes.");
        output.add("Average longest  win streak: " + statsArr[2]);
        output.add("Average longest lose streak: " + statsArr[3]);
        output.add("Average # games at rank 5 or better: " + statsArr[4]);
        output.add("Average # games at ranks worse than 5: " + statsArr[5]);
        output.add("Average % of games at rank 5 or better: " + (statsArr[4] / statsArr[0]));
        return output;
    }

    public static int[] simulateClimb(Strategy s, boolean verbose) {
        int streak = 0;
        int stars = 0;
        int count = 0;
        int rank = 20;
        int time = 0;
        int longestWinStreak = 0;
        int longestLoseStreak = 0;
        int superProCount = 0;
        int noobCount = 0;
        while (rank > 0) {
            boolean win = false;
            time += s.getTime(streak, rank);
            if (rank > 5) {
                noobCount++;
            } else {
                superProCount++;
            }
            if (Math.random() < s.getRate(streak, rank)) {
                win = true;
                if (streak < 0)
                    streak = 0;
                streak++;
                stars++;
                if (streak >= 3 && rank > 5)
                    stars++;
            } else {
                if (streak > 0)
                    streak = 0;
                streak--;

                stars--;
            }
            if (stars < 0) {
                if (rank == 20) {
                    stars = 0;
                } else {
                    stars = STAR_REQS[rank] - 1;
                    rank++;
                }
            } else if (stars > STAR_REQS[rank - 1]) {
                stars -= STAR_REQS[rank - 1];
                rank--;
            }
            if (verbose)
                System.out.println((win ? " Won" : "Lost") + " game, now at rank " + rank + " with " + stars + " stars, streak " + streak);
            if (streak > longestWinStreak)
                longestWinStreak = streak;
            if (streak < longestLoseStreak)
                longestLoseStreak = streak;
            count++;
        }
        int hours = time / 60;
        int minutes = time % 60;
        if (verbose) {
            System.out.println("Games: " + count + ", Time: " + time + " minutes = " + hours + " hours & " + minutes + " minutes");
            System.out.println("Longest  win streak: " + longestWinStreak);
            System.out.println("Longest lose streak: " + longestLoseStreak);
            System.out.println("Games at rank 5 or better: " + superProCount);
            System.out.println("Games at ranks worse than 5: " + noobCount);
        }
        return new int[] { count, time, longestWinStreak, longestLoseStreak, superProCount, noobCount };
    }

}