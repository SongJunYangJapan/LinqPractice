package linq;

import cn.hutool.json.JSONUtil;
import linq.compare.CompareAnagram;
import linq.domain.Customer;
import linq.domain.Objects;
import linq.domain.Order;
import linq.domain.Product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingOperators {

    public static void main(String[] args) {
        /*linq40();
          linq41();
          linq42();
          linq43();
          linq44();
          linq45();
         */
        linq45();
    }

    /**
     * "This sample uses grouping to partition a list of numbers by their remainder when divided by 5."
     */
    public static void linq40() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        Map<Integer, List<Integer>> numberGroups = Arrays.stream(numbers).boxed().collect(Collectors.groupingBy(n -> n % 5, Collectors.toList()));
        for (Integer key : numberGroups.keySet()
        ) {
            System.out.println("Numbers with a remainder of %d when divided by 5:" + key);
            numberGroups.get(key).forEach(System.out::println);
        }
    }

    /**
     * "This sample uses grouping to partition a list of words by their first letter."
     */
    public static void linq41() {
        String[] words = new String[]{"blueberry", "chimpanzee", "abacus", "banana", "apple", "cheese"};
        Map<Character, List<String>> wordGroups = Arrays.stream(words).collect(Collectors.groupingBy(n -> n.charAt(0), Collectors.toList()));
        for (Character key : wordGroups.keySet()
        ) {
            System.out.printf("Words that start with the letter:'%s'%n", key);
            wordGroups.get(key).forEach(System.out::println);
        }
    }

    /**
     * "This sample uses grouping to partition a list of products by category."
     */
    public static void linq42() {
        List<Product> products = Objects.getProductList();
        Map<String, List<Product>> productGroups = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.toList()));
        for (String key : productGroups.keySet()
        ) {
            System.out.printf("Products in the category:'%s'%n", key);
            productGroups.get(key).forEach(System.out::println);
        }
    }

    /**
     * "This sample uses nested grouping to partition a list of each customer's orders, first by year, and then by month."
     */
    public static void linq43() {
        List<Customer> customers = Objects.getCustomerList();
        for (Customer customer : customers
        ) {
            System.out.printf("{CompanyName:%s", customer.getCompanyName());
            Map<Integer, Map<Integer, List<Order>>> yearMonthGroups = customer.getOrders().stream().collect(Collectors.groupingBy(a -> a.getOrderDate().getYear(), Collectors.groupingBy(b -> b.getOrderDate().getMonthValue())));
            System.out.print(JSONUtil.toJsonStr(yearMonthGroups));
            System.out.println("}");
        }
    }

    /**
     * "This sample uses GroupBy to partition trimmed elements of an array using a custom comparer that matches words that are anagrams of each other."
     */
    public static void linq44() {
        String[] words = new String[]{"from    ", " salt", " earn ", "  last   ", " near ", " form  "};
        Map<String, List<String>> wordGroups = Arrays.stream(words).collect(Collectors.groupingBy(String::trim));
        for (String key : wordGroups.keySet()
        ) {
            List<String> trimedList = wordGroups.get(key).stream().map(m -> m.trim()).collect(Collectors.toList());
            wordGroups.put(key, trimedList);
            for (String k : wordGroups.keySet()
            ) {
                if (k == key) {
                    continue;
                } else if (CompareAnagram.isAnagram(key, k)) {
                    wordGroups.get(key).add(k);
                }
            }
        }
        System.out.print(JSONUtil.toJsonStr(wordGroups));
    }

    /**
     * "This sample uses GroupBy to partition trimmed elements of an array using a custom comparer that matches words
     * that are anagrams of each other, and then converts the results to uppercase."
     */
    public static void linq45() {
        String[] words = new String[]{"from    ", " salt", " earn ", "  last   ", " near ", " form  "};
        Map<String, List<String>> wordGroups = Arrays.stream(words).collect(Collectors.groupingBy(a->a.trim()));
        for (String key : wordGroups.keySet()
        ) {
            List<String> trimedList = wordGroups.get(key).stream().map(m -> m.trim().toUpperCase()).collect(Collectors.toList());
            wordGroups.put(key, trimedList);
            for (String k : wordGroups.keySet()
            ) {
                if (k == key) {
                    continue;
                } else if (CompareAnagram.isAnagram(key, k)) {
                    wordGroups.get(key).add(k.toUpperCase());
                }
            }
        }
        System.out.print(JSONUtil.toJsonStr(wordGroups));
    }
}
