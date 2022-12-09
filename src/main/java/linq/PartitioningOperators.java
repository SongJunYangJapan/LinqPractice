package linq;

import linq.domain.Customer;
import linq.domain.Objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PartitioningOperators {

    public static void main(String[] args) {
		/*
		linq20();
		linq21();
		linq22();
		linq23();
		linq24();
		linq25();
		linq26();*/
        linq27();
    }


    /**
     * "This sample take First 3 numbers."
     */
    static void linq20() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("First 3 numbers:");
        Arrays.stream(numbers).limit(3).forEach(System.out::println);
    }

    /**
     * "This sample take First 3 orders in WA."
     */
    static void linq21() {
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("First 3 orders in WA:");
        customerList.stream().filter(c -> "WA".equals(c.region))
                .flatMap(c -> c.orders.stream()
                        .map(o -> new HashMap<String, Object>() {{
                            put("customerID", c.customerID);
                            put("orderID", o.orderID);
                            put("orderDate", o.orderDate);
                        }})).limit(3)
                .forEach(ObjectDumper::dump);
    }

    /**
     * "This sample skip First 4 numbers."
     */
    static void linq22() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("All but first 4 numbers:");
        Arrays.stream(numbers).skip(4).forEach(System.out::println);
    }

    /**
     * "This sample skip First 2 orders in WA."
     */
    static void linq23() {
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("All but first 2 orders in WA:");
        customerList.stream().filter(c -> "WA".equals(c.region))
                .flatMap(c -> c.orders.stream()
                        .map(o -> new HashMap<String, Object>() {{
                            put("customerID", c.customerID);
                            put("orderID", o.orderID);
                            put("orderDate", o.orderDate);
                        }})).skip(2)
                .forEach(ObjectDumper::dump);
    }

    /**
     * "This sample uses a partition to return elements starting from the
     * beginning of the array until a number is read whose value is not less than 6."
     */
    static void linq24() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("First numbers less than 6:");
        Arrays.stream(numbers).takeWhile(num -> num < 6).forEach(System.out::println);
    }

    /**
     * This sample uses a partition to return elements starting from the
     * beginning of the array until a number is hit that is less than its
     * position in the array.
     */

    static void linq25() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("First numbers not less than their position:");
        IntStream.range(0, numbers.length)
                .mapToObj(index -> new Object() {
                    int Num = numbers[index];
                    int Index = index;
                }).takeWhile(x -> x.Num >= x.Index).forEach(x -> System.out.println(x.Num));
    }

    /**
     * This sample uses a partition to get the elements of the array starting from
     * the first element divisible by 3.
     */

    static void linq26() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("All elements starting from first element divisible by 3:");
        Arrays.stream(numbers)
                .dropWhile(n -> n % 3 != 0).forEach(System.out::println);
    }

    /**
     * This sample uses a partition to get the elements of the array starting from
     * the first element less than its position.
     */

    static void linq27() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        System.out.println("All elements starting from first element less than its position:");
        IntStream.range(0, numbers.length)
                .mapToObj(index -> new Object() {
                    int Num = numbers[index];
                    int Index = index;
                }).dropWhile(x -> x.Num >= x.Index).forEach(x -> System.out.println(x.Num));
    }
}
