package linq;

import cn.hutool.json.JSONUtil;
import linq.domain.Customer;
import linq.domain.Objects;
import linq.domain.Order;
import linq.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetOperators {

    public static void main(String[] args) {
        /*linq46();
        linq47();
        linq48();
        linq49();
        linq50();
        linq51();
        linq52();
        linq53();
        */
        linq53();
    }

    /**
     * "This sample removes all duplicate elements in a sequence of factors of 300."
     */
    public static void linq46() {
        int[] numbers = {2, 2, 3, 5, 5};
        Arrays.stream(numbers).boxed().distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample gets distint Category names from all the products."
     */
    public static void linq47() {
        List<Product> products = Objects.getProductList();
        products.stream().map(m -> m.getCategory()).distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample creates a Union of sequences that contains unique values from both arrays."
     */
    public static void linq48() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Unique numbers from both arrays:");
        Stream.concat(Arrays.stream(numbersA).boxed(), Arrays.stream(numbersB).boxed()).distinct().sorted().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample creates a Union of sequences that contains the distinct first letter from both product and customer names."
     */
    public static void linq49() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("Unique first letters from Product names and Customer names:");
        Stream.concat(productList.stream().map(a -> a.productName.charAt(0)), customerList.stream().map(b -> b.companyName.charAt(0))).distinct().sorted().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * "This sample creates Intersection that contains the common values shared by both arrays."
     */
    public static void linq50() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Common numbers shared by both arrays:");
        Arrays.stream(numbersA).boxed().filter(o ->
                (Arrays.stream(numbersB).boxed().anyMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
    }

    /**
     * "This sample creates Intersection that contains  the common first letter from both product and customer names."
     */
    public static void linq51() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("Common first letters from Product names and Customer names:");
        productList.stream().map(m -> m.productName.charAt(0)).filter(o ->
                (customerList.stream().map(n -> n.companyName.charAt(0)).anyMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
    }

    /**
     * "This sample creates a sequence that excludes the values from the second sequence."
     */
    public static void linq52() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Numbers in first array but not second array:");
        Arrays.stream(numbersA).boxed().filter(o ->
                (Arrays.stream(numbersB).boxed().noneMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
    }

    /**
     * "This sample creates a sequence that the first letters of product names that but excludes letters of customer names first letter."
     */
    public static void linq53() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("First letters from Product names, but not from Customer names:");
        productList.stream().map(m -> m.productName.charAt(0)).filter(o ->
                (customerList.stream().map(m -> m.companyName.charAt(0)).noneMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
    }
}
