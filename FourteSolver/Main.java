package games.fourte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        char[] symbols = new char[]{'+', '-', '*', '/'};
        System.out.print("ENTER NUMBERS : ");
        int[] numbers = getInput();
        System.out.print("ENTER TARGET : ");
        int target = getInput()[0];
        FourteSolver instinct = new FourteSolver(numbers, symbols, target);
        instinct.init();
    }

    private static int[] getInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String in = bufferedReader.readLine();
            String[] nums = in.split("\\s");
            int[] n = new int[nums.length];
            int i = 0;
            for (String x : nums) {
                n[i++] = Integer.parseInt(x);
            }
            return n;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
