package games.fourte;

import java.util.ArrayList;
import java.util.Arrays;

public class FourteSolver {

    int[] numbers;
    char[] operators;
    ArrayList<ArrayList<Character>> three_operators, two_operators, single;
    int target, length;
    private final Calculator calculator;

    public FourteSolver(int[] numbers, char[] symbols, int target) {
        this.numbers = numbers;
        this.operators = symbols;
        this.target = target;
        this.length = numbers.length;
        this.three_operators = new ArrayList<>(64);
        this.two_operators = new ArrayList<>(12);
        this.single = new ArrayList<>(4);
        calculator = new Calculator();
        operatorSeries();
    }

    private void operatorSeries() {
        opCombinations(3, three_operators, new ArrayList<>());
        opCombinations(2, two_operators, new ArrayList<>());
        opCombinations(1, single, new ArrayList<>());
    }

    private void opCombinations(int selections, ArrayList<ArrayList<Character>> operators, ArrayList<Character> temp) {
        if (temp.size() == selections) {
            operators.add(new ArrayList<>(temp));
            return;
        }
        for (char op : this.operators) {
            temp.add(op);
            opCombinations(selections, operators, temp);
            temp.remove(temp.size() - 1);
        }
    }

    public void init() {
        String master_copy = Arrays.toString(numbers);
        do {
            searchMachine();
            nextNumber();
        } while (!master_copy.equals(Arrays.toString(numbers)));
        System.out.println("*----* END *----*");
    }

    private void nextNumber() {
        int idx = length - 1;
        while (idx > 0 && numbers[idx - 1] >= numbers[idx]) {
            idx--;
        }
        if (idx == 0) {
            reverseNumber();
            return;
        }
        for (int i = length - 1; i >= idx; i--) {
            if (numbers[i] > numbers[idx - 1]) {
                numbers[i] = numbers[i] + numbers[idx - 1] - (numbers[idx - 1] = numbers[i]);
                sort(idx);
                return;
            }
        }
    }

    private void searchMachine() {
        ArrayList<ArrayList<Character>> op;
        for (int i = 4; i > 0; i--) {
            ArrayList<int[]> joint_numbers = addOn(i);
            if (i == 4) op = three_operators;
            else if (i == 3) op = two_operators;
            else if (i == 2) op = single;
            else {
                if (joint_numbers.get(0)[0] == target)
                    System.out.println(target);
                return;
            }
            for (int[] sets : joint_numbers) {
                sum(sets, op);
            }
        }
    }

    private ArrayList<int[]> addOn(int reducer) {
        ArrayList<int[]> jointNumbers = new ArrayList<>(reducer);
        if (reducer == length) {
            jointNumbers.add(numbers);
            return jointNumbers;
        }
        for (int i = 0; i < reducer % length; i++) {
            int[] currentNumber = new int[reducer];
            int idx = 0;
            for (int inner = 0; inner < reducer; inner++) {
                if (length - reducer >= 1 && idx == i) {
                    currentNumber[inner] = joiner(inner, length - reducer);
                    idx += length - reducer + 1;
                } else {
                    currentNumber[inner] = this.numbers[idx++];
                }
            }
            jointNumbers.add(currentNumber);
        }
        return jointNumbers;
    }
    private void sum(int[] numbers, ArrayList<ArrayList<Character>> operatorsSet) {
        for (ArrayList<Character> operators : operatorsSet) {
            String res = calculator.makeExpress(numbers, operators, target);
            if (res.length() > 0) {
                System.out.println(res);
                return;
            }
        }
    }
    private void reverseNumber() {
        int start = 0, end = length - 1;
        while (start < end) {
            numbers[start] = numbers[start] + numbers[end] - (numbers[end] = numbers[start]);
            start++;
            end--;
        }
    }
    private void sort(int start) {
        for (int i = start; i < length - 1; i++) {
            for (int j = start; j < length - (i - start + 1); j++) {
                if (numbers[j] > numbers[j + 1]) {
                    numbers[j] = numbers[j] + numbers[j + 1] - (numbers[j + 1] = numbers[j]);
                }
            }
        }
    }
    private int joiner(int begin, int places) {
        int num = numbers[begin];
        while (places-- > 0) {
            num = num * 10 + numbers[++begin];
        }
        return num;
    }
}
