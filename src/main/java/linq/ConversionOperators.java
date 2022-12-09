package linq;

import linq.domain.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConversionOperators {

    public static void main(String[] args) {
		/*linq54();
		linq55();
		linq56();
		linq57();
		*/
        linq57();
    }

    /**
     * "This sample converts a list ti an array."
     */
    public static void linq54() {
        List<Double> list = Arrays.asList(1.7, 2.3, 1.9, 4.1, 2.9);
        System.out.println("Every other double from highest to lowest:");
        double[] doubleArray = list.stream().sorted(Comparator.reverseOrder()).mapToDouble(x -> x).toArray();
        for (double o : doubleArray
        ) {
            System.out.println(o);
        }
    }

    /**
     * "This sample converts an array to a list."
     */
    public static void linq55() {
        String[] wordsArray = {"cherry", "apple", "blueberry"};
        System.out.println("The sorted word list:");
        Arrays.stream(wordsArray).sorted().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample converts an array of records to a dictionary."
     */
    public static void linq56() {
        ArrayList<StudentScore> scoreRecords = new ArrayList<>();
        scoreRecords.add(new StudentScore("Alice", 50));
        scoreRecords.add(new StudentScore("Bob", 40));
        scoreRecords.add(new StudentScore("Cathy", 45));
        Map<String, Integer> scoresMap = scoreRecords.stream().collect(Collectors.toMap(StudentScore::getName, StudentScore::getScore));
        System.out.printf("Bob's score: %s", scoresMap.get("Bob"));
    }

    /**
     * "This sample filters all elements that matches the type double/float."
     */
    public static void linq57() {
        Object[] objectsArray = {null, 1.0, "two", 3, "four", 5, "six", 7.0};
        Arrays.stream(objectsArray).filter(Double.class::isInstance).forEach(System.out::println);
    }
}
