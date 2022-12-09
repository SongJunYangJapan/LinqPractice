package linq;

import linq.domain.StudentScore;

import java.util.*;
import java.util.stream.Collectors;

public class ElementOperators {

    public static void main(String[] args) {
		/*linq58();
		linq59();
		linq60();
		linq61();
		linq62();*/
        linq58();
    }

    /**
     * "This sample converts a list ti an array."
     */
    public static void linq58() {
        List<Double> list = Arrays.asList(1.7, 2.3, 1.9, 4.1, 2.9);
        System.out.println("Every other double from highest to lowest:");
        double[] doubleArray = list.stream().sorted(Comparator.reverseOrder()).mapToDouble(x -> x).toArray();
        for (double o : doubleArray
        ) {
            System.out.println(o);
        }
    }


}
