package linq;

import linq.domain.Customer;
import linq.domain.Objects;
import linq.domain.Order;
import linq.domain.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OrderingOperators {

    public static void main(String[] args) {
        /*linq28();
        linq29();
        linq30();
        linq31();
        linq32();
        linq33();
        linq34();
        linq35();
        linq36();
        linq37();
        linq38();
         */
        linq39();
    }

    /**
     * "This sample uses ordering to sort a list of words alphabetically."
     */
    public static void linq28() {
        String[] words = new String[]{"cherry", "apple", "blueberry"};
        Stream<String> stringStream = Arrays.stream(words).sorted();
        System.out.println("The sorted list of words:");
        stringStream.forEach(System.out::println);
    }

    /**
     * "This sample uses ordering to sort a list of words by length."
     */
    public static void linq29() {
        String[] words = new String[]{"cherry", "apple", "blueberry"};
        Stream<String> stringStream = Arrays.stream(words).sorted(Comparator.comparing(s -> s.length()));
        System.out.println("The sorted list of words (by length):");
        stringStream.forEach(System.out::println);
    }

    /**
     * "This sample uses ordering to sort a list of products by name."
     */
    public static void linq30() {
        List<Product> products = Objects.getProductList();
        Stream<Product> productStream = products.stream().sorted(Comparator.comparing(a -> a.productName));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses case-insensitive ordering to sort the words in an array."
     */
    public static void linq31() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> stringStream = Arrays.stream(words).sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER));
        stringStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses reverse ordering to sort a list of doubles from highest to lowest."
     */
    public static void linq32() {
        Double[] doubles = new Double[]{1.7, 2.3, 1.9, 4.1, 2.9};
        Stream<Double> doubleStream = Arrays.stream(doubles).sorted(Comparator.reverseOrder());
        System.out.println("The doubles from highest to lowest:");
        doubleStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses reverse ordering to sort a list of products by units in stock from highest to lowest."
     */
    public static void linq33() {
        List<Product> products = Objects.getProductList();
        Stream<Product> productStream = products.stream().sorted(Comparator.comparing(m -> m.unitPrice, Comparator.reverseOrder()));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses reverse case-insensitive ordering to sort the words in an array."
     */
    public static void linq34() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER).reversed());
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses nested ordering, first by length of their name, and then alphabetically by the name itself."
     */
    public static void linq35() {
        String[] words = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses case-insensitive nested ordering, with a custom comparer to sort first by word length and
     * then by a case-insensitive sort of the words in an array."
     */
    public static void linq36() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString,String.CASE_INSENSITIVE_ORDER));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses nested ordering to sort a list of products, first by category, and
     * then by unit price, from highest to lowest."
     */
    public static void linq37() {
        List<Product> products = Objects.getProductList();
        Stream<Product> productStream = products.stream().sorted(Comparator.comparing(Product::getCategory).thenComparing(Product::getUnitPrice,Comparator.reverseOrder()));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses uses case-insensitive reverse nested ordering to sort first by word length
     * and then by a case-insensitive descending sort of."
     */
    public static void linq38() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString,String.CASE_INSENSITIVE_ORDER.reversed()));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample uses reverse ordering to create a list of all digits in the array
     * whose second letter is 'i' that is reversed from the order in the original array."
     */
    public static void linq39() {
        String[] words = new String[]{ "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        Stream<String> wordStream = Arrays.stream(words).filter(m->m.charAt(1)=='i');
        System.out.println("A backwards list of the digits with a second character of 'i':");
        wordStream.collect(Collectors.toCollection(LinkedList::new)).descendingIterator().forEachRemaining(System.out::println);
    }
}
