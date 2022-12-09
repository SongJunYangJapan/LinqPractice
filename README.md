101 LINQ Samples in Java 8 Streams and Lambdas
===========================

Port of [C#'s' 101 LINQ Samples](http://code.msdn.microsoft.com/101-LINQ-Samples-3fb9811b) translated into Java 8.
(Structure, text and etc copied from [mythz Clojure repository](https://github.com/mythz/clojure-linq-examples))

Execute and display the results of all the examples with:

    ./gradlew run

#### Contributions into more idiomatic and readable examples are welcome!

#### [LINQ - Restriction Operators](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/RestrictionOperators.java) / [MSDN C#](http://code.msdn.microsoft.com/LINQ-to-DataSets-09787825)

#### [LINQ - Projection Operators](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/ProjectionOperators.java) / [MSDN C#](http://code.msdn.microsoft.com/LINQ-Partitioning-Operators-c68aaccc)

## Side-by-side - C# LINQ vs Java

For a side-by-side comparison, the original **C#** source code is displayed above the equivalent **Java8** translation.

- The **Output** shows the console output of running the **Java** sample.
- Outputs ending with `...` illustrates only a partial response is displayed.
- The source-code for C# and Clojure utils used are included once under the first section they're used in.
- Unfortunately, Java does not support anonymous record types, so HashMap is used. ObjectDumper substitute for HashMaps
  in java is
  located [here.](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/ObjectDumper.java)

LINQ - Restriction Operators
----------------------------

### linq1: Where - Simple 1

```csharp
//c#
public void Linq1()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var lowNums =
        from n in numbers
        where n < 5
        select n;

    Console.WriteLine("Numbers < 5:");
    foreach (var x in lowNums)
    {
        Console.WriteLine(x);
    }
}
```

```java
//java
int[]numbers={5,4,1,3,9,8,6,7,2,0};
        IntStream stream=Arrays.stream(numbers).filter(x->x< 5);
        System.out.println("Numbers < 5:");
        stream.forEach(System.out::println);
```

#### Output

    Numbers < 5:
    4
    1
    3
    2
    0

### linq2: Where - Simple 2

```csharp
//c#
public void Linq2()
{
    List<Product> products = GetProductList();

    var soldOutProducts =
        from p in products
        where p.UnitsInStock == 0
        select p;

    Console.WriteLine("Sold out products:");
    foreach (var product in soldOutProducts)
    {
        Console.WriteLine("{0} is sold out!", product.ProductName);
    }
}
```

```java
//java
List<Product> products=Objects.getProductList();
        Stream<Product> stream=products.stream().filter(x->x.unitsInStock==0);
        System.out.println("Sold out products:");
        stream.map(x->x.productName).forEach(System.out::println);
```

#### Output

    Sold out products:
    Chef Anton's Gumbo Mix  is sold out
    Alice Mutton  is sold out
    Thüringer Rostbratwurst  is sold out
    Gorgonzola Telino  is sold out
    Perth Pasties  is sold out

### linq3: Where - Simple 3

```csharp
//c#
public void Linq3()
{
    List<Product> products = GetProductList();

    var expensiveInStockProducts =
        from p in products
        where p.UnitsInStock > 0 && p.UnitPrice > 3.00M
        select p;

    Console.WriteLine("In-stock products that cost more than 3.00:");
    foreach (var product in expensiveInStockProducts)
    {
        Console.WriteLine("{0} is in stock and costs more than 3.00.", product.ProductName);
    }
}
```

```java
//java
List<Product> products=Objects.getProductList();
        Stream<Product> stream=products.stream().filter(x->x.unitsInStock>0&&x.unitPrice>3.00);
        System.out.println("In-stock products that cost more than 3.00:");
        stream.map(x->String.format("%s is in stock and costs more than 3.00.",x.productName))
        .forEach(System.out::println);
```

#### Output

    In-stock products that cost more than 3.00:
    Chai is in stock and costs more than 3.00
    Chang is in stock and costs more than 3.00
    Aniseed Syrup is in stock and costs more than 3.00
    Chef Anton's Cajun Seasoning is in stock and costs more than 3.00
    Grandma's Boysenberry Spread is in stock and costs more than 3.00

### linq4: Where - Drilldown

```csharp
//c#
public void Linq4()
{
    List<Customer> customers = GetCustomerList();

    var waCustomers =
        from c in customers
        where c.Region == "WA"
        select c;

    Console.WriteLine("Customers from Washington and their orders:");
    foreach (var customer in waCustomers)
    {
        Console.WriteLine("Customer {0}: {1}", customer.CustomerID, customer.CompanyName);
        foreach (var order in customer.Orders)
        {
            Console.WriteLine("  Order {0}: {1}", order.OrderID, order.OrderDate);
        }
    }
}
```

```java
//java
System.out.println("Customers from Washington and their orders:");
        Stream<Customer> stream=Objects.getCustomerList()
        .stream()
        .filter(x->x.region!=null&&x.region.equals("WA"));

        stream.forEach(x->{
        System.out.println(String.format("Customer %s: %s",x.customerID,x.companyName));
        for(Order y:x.orders){
        System.out.println(String.format("   Order %s: %s",y.orderID,y.orderDate));
        }
        });
```

#### Output

    Customer LAZYK: Lazy K Kountry Store
       Order 10482: 1997-03-21T00:00
       Order 10545: 1997-05-22T00:00
    Customer TRAIH: Trail's Head Gourmet Provisioners
       Order 10574: 1997-06-19T00:00
       Order 10577: 1997-06-23T00:00
       Order 10822: 1998-01-08T00:00
    ...

### linq5: Where - Indexed

```csharp
//c#
public void Linq5()
{
    string[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var shortDigits = digits.Where((digit, index) => digit.Length < index);

    Console.WriteLine("Short digits:");
    foreach (var d in shortDigits)
    {
        Console.WriteLine("The word {0} is shorter than its value.", d);
    }
}
```

```java
//java
String[]digits={"zero","one","two","three","four","five","six","seven","eight","nine"};
        System.out.println("Short digits:");
        IntStream.range(0,digits.length)
        .filter(i->digits[i].length()<i)
        .mapToObj(i->String.format("The word %s is shorter than its value.",digits[i]))
        .forEach(System.out::println);
```

#### Output

    Short digits:
    The word five is shorter than its value
    The word six is shorter than its value
    The word seven is shorter than its value
    The word eight is shorter than its value
    The word nine is shorter than its value

LINQ - Projection Operators
---------------------------

### linq6: Select - Simple 1

```csharp
//c#
public void Linq6()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var numsPlusOne =
        from n in numbers
        select n + 1;

    Console.WriteLine("Numbers + 1:");
    foreach (var i in numsPlusOne)
    {
        Console.WriteLine(i);
    }
}
```

```java
//java
int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("Numbers + 1:");
        Arrays.stream(numbers).map(x->x+1).forEach(System.out::println);
```

#### Output

    Numbers + 1:
    6
    5
    2
    4
    10
    9
    7
    8
    3
    1

### linq7: Select - Simple 2

```csharp
//c#
public void Linq7()
{
    List<Product> products = GetProductList();

    var productNames =
        from p in products
        select p.ProductName;

    Console.WriteLine("Product Names:");
    foreach (var productName in productNames)
    {
        Console.WriteLine(productName);
    }
}
```

```java
//java
System.out.println("Product Names:");
        Objects.getProductList().stream().map(x->x.productName).forEach(System.out::println);
```

#### Output

    Product Names:
    Chai
    Chang
    Aniseed Syrup
    Chef Anton's Cajun Seasoning
    Chef Anton's Gumbo Mix
    ...

### linq8: Select - Transformation

```csharp
//c#
public void Linq8()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] strings = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var textNums =
        from n in numbers
        select strings[n];

    Console.WriteLine("Number strings:");
    foreach (var s in textNums)
    {
        Console.WriteLine(s);
    }
}
```

```java
//java
int[]numbers={5,4,1,3,9,8,6,7,2,0};
        String[]strings={"zero","one","two","three","four","five","six","seven","eight","nine"};

        System.out.println("Number strings:");
        Arrays.stream(numbers).mapToObj(x->strings[x]).forEach(System.out::println);
```

#### Output

    Number strings:
    five
    four
    one
    three
    nine
    eight
    six
    seven
    two
    zero

### linq9: Select - Anonymous Types 1

```csharp
//c#
public void Linq9()
{
    string[] words = { "aPPLE", "BlUeBeRrY", "cHeRry" };

    var upperLowerWords =
        from w in words
        select new { Upper = w.ToUpper(), Lower = w.ToLower() };

    foreach (var ul in upperLowerWords)
    {
        Console.WriteLine("Uppercase: {0}, Lowercase: {1}", ul.Upper, ul.Lower);
    }
}
```

```java
//java
String[]words={"aPPLE","BlUeBeRrY","cHeRry"};

        Arrays.stream(words).map(s->new HashMap<String, String>(){{
        put("Upper",s.toUpperCase());
        put("Lower",s.toLowerCase());
        }}).forEach(map->System.out.println(
        String.format("Uppercase : %s, Lowercase : %s",map.get("Upper"),map.get("Lower"))));
```

#### Output

    Uppercase: APPLE , Lowercase: apple
    Uppercase: BLUEBERRY , Lowercase: blueberry
    Uppercase: CHERRY , Lowercase: cherry

### linq10: Select - Anonymous Types 2

```csharp
//c#
public void Linq10()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] strings = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var digitOddEvens =
        from n in numbers
        select new { Digit = strings[n], Even = (n % 2 == 0) };

    foreach (var d in digitOddEvens)
    {
        Console.WriteLine("The digit {0} is {1}.", d.Digit, d.Even ? "even" : "odd");
    }
}
```

```java
//java
int[]numbers={5,4,1,3,9,8,6,7,2,0};
        String[]strings={"zero","one","two","three","four","five","six","seven","eight","nine"};

        Arrays.stream(numbers).mapToObj(number->new HashMap<String, Object>(){{
        put("Digit",strings[number]);
        put("Even",number%2==0);
        }}).map(x->String.format("The digit %s is %s.",x.get("Digit"),x.get("Even").equals(Boolean.FALSE)?"odd":"even"))
        .forEach(System.out::println);
```

#### Output

    The digit five is odd
    The digit four is even
    The digit one is odd
    The digit three is odd
    The digit nine is odd
    The digit eight is even
    The digit six is even
    The digit seven is odd
    The digit two is even
    The digit zero is even

### linq11: Select - Anonymous Types 3

```csharp
//c#
public void Linq11()
{
    List<Product> products = GetProductList();

    var productInfos =
        from p in products
        select new { p.ProductName, p.Category, Price = p.UnitPrice };

    Console.WriteLine("Product Info:");
    foreach (var productInfo in productInfos)
    {
        Console.WriteLine("{0} is in the category {1} and costs {2} per unit.", productInfo.ProductName, productInfo.Category, productInfo.Price);
    }
}
```

```java
//java
System.out.println("Product Info:");

        Objects.getProductList().stream().map(x->new HashMap<String, Object>(){{
        put("ProductName",x.productName);
        put("Category",x.category);
        put("Price",x.unitPrice);
        }}).map(x->String.format("%s is in the category %s and costs %f per unit.",
        x.get("ProductName"),x.get("Category"),x.get("Price"))).forEach(System.out::println);
```

#### Output

    Product Info:
    Chai is in the category Beverages and costs 18.0
    Chang is in the category Beverages and costs 19.0
    Aniseed Syrup is in the category Condiments and costs 10.0
    ...

### linq12: Select - Indexed

```csharp
//c#
public void Linq12()
{
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};

System.out.println("Number: In-place?");
IntStream.range(0, numbers.length).mapToObj(i -> new HashMap<String, Object>() {{
    put("Num", numbers[i]);
    put("InPlace", numbers[i] == i);
}}).map(map -> map.get("Num") + ": " + map.get("InPlace")).forEach(System.out::println);
}
```

```java
//java
(defn linq12[]
        (let[numbers[5 4 1 3 9 8 6 7 2 0]
        nums-in-place
        (map-indexed(fn[i num]{:num num,:in-place(=num i)})numbers)]
        (println"Number: In-place?")
        (doseq[n nums-in-place]
        (println(:num n)":"(:in-place n)))))
```

#### Output

    Number: In-place?
    5 : false
    4 : false
    1 : false
    3 : true
    9 : false
    8 : false
    6 : true
    7 : true
    2 : false
    0 : false

### linq13: Select - Filtered

```csharp
//c#
public void Linq13()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var lowNums =
        from n in numbers
        where n < 5
        select digits[n];

    Console.WriteLine("Numbers < 5:");
    foreach (var num in lowNums)
    {
        Console.WriteLine(num);
    }
}
```

```java
//java
int[]numbers={5,4,1,3,9,8,6,7,2,0};
        String[]digits={"zero","one","two","three","four","five","six","seven","eight","nine"};

        System.out.println("Numbers < 5:");
        Arrays.stream(numbers).filter(x->x< 5).mapToObj(x->digits[x])
        .forEach(System.out::println);
```

#### Output

    Numbers < 5:
    four
    one
    three
    two
    zero

### linq14: SelectMany - Compound from 1

```csharp
//c#
public void Linq14()
{
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var pairs =
        from a in numbersA
        from b in numbersB
        where a < b
        select new { a, b };

    Console.WriteLine("Pairs where a < b:");
    foreach (var pair in pairs)
    {
        Console.WriteLine("{0} is less than {1}", pair.a, pair.b);
    }
}
```

```java
//java
int[]numbersA={0,2,4,5,6,8,9};
        int[]numbersB={1,3,5,7,8};

        System.out.println("Pairs where a < b:");
        Arrays.stream(numbersA)
        .mapToObj(a->Arrays.stream(numbersB)
        .filter(b->a<b)
        .mapToObj(b->new HashMap<String, Object>(){{
        put("A",a);
        put("B",b);
        }}))
        .flatMap(x->x)
        .map(x->String.format("%d is less than %d",x.get("A"),x.get("B")))
        .forEach(System.out::println);
```

#### Output

    Pairs where a < b:
    0 is less than 1
    0 is less than 3
    0 is less than 5
    0 is less than 7
    0 is less than 8
    2 is less than 3
    2 is less than 5
    2 is less than 7
    2 is less than 8
    4 is less than 5
    4 is less than 7
    4 is less than 8
    5 is less than 7
    5 is less than 8
    6 is less than 7
    6 is less than 8

### linq15: SelectMany - Compound from 2

```csharp
//c#
public void Linq15()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.Total < 500.00M
        select new { c.CustomerID, o.OrderID, o.Total };

    ObjectDumper.Write(orders);
}
```

```java
//java
List<Customer> customerList=Objects.getCustomerList();

        customerList.stream().flatMap(c->c.orders.stream()
        .filter(o->o.total< 500.0)
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        put("total",o.total);
        }})).forEach(ObjectDumper::dump);
```

#### Output

    {:customer-id ALFKI, :order-id 10702, :total 330.00M}
    {:customer-id ALFKI, :order-id 10952, :total 471.20M}
    {:customer-id ANATR, :order-id 10308, :total 88.80M}
    {:customer-id ANATR, :order-id 10625, :total 479.75M}
    ...

### linq16: SelectMany - Compound from 3

```csharp
//c#
public void Linq16()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.OrderDate >= new DateTime(1998, 1, 1)
        select new { c.CustomerID, o.OrderID, o.OrderDate };

    ObjectDumper.Write(orders);
}
```

```java
//java
List<Customer> customerList=Objects.getCustomerList();

        customerList.stream().flatMap(c->c.orders.stream()
        .filter(o->o.orderDate.isAfter(LocalDateTime.of(1998,1,1,0,0,0)))
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        put("orderDate",o.orderDate);
        }})).forEach(ObjectDumper::dump);
```

#### Output

    {:customer-id ALFKI, :order-id 10835, :order-date #<DateTime 1998-01-15T00:00:00.000-05:00>}
    {:customer-id ALFKI, :order-id 10952, :order-date #<DateTime 1998-03-16T00:00:00.000-05:00>}
    {:customer-id ALFKI, :order-id 11011, :order-date #<DateTime 1998-04-09T00:00:00.000-04:00>}
    {:customer-id ANATR, :order-id 10926, :order-date #<DateTime 1998-03-04T00:00:00.000-05:00>}
    {:customer-id ANTON, :order-id 10856, :order-date #<DateTime 1998-01-28T00:00:00.000-05:00>}
    ...

### linq17: SelectMany - from Assignment

```csharp
//c#
public void Linq17()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.Total >= 2000.0M
        select new { c.CustomerID, o.OrderID, o.Total };

    ObjectDumper.Write(orders);
}
```

```java
//java
List<Customer> customerList=Objects.getCustomerList();

        customerList.stream().flatMap(c->c.orders.stream()
        .filter(o->o.total>=2000.0)
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        put("total",o.total);
        }})).forEach(ObjectDumper::dump);
```

#### Output

    {:customer-id ANTON, :order-id 10573, :total 2082.00M}
    {:customer-id AROUT, :order-id 10558, :total 2142.90M}
    {:customer-id AROUT, :order-id 10953, :total 4441.25M}
    {:customer-id BERGS, :order-id 10384, :total 2222.40M}
    {:customer-id BERGS, :order-id 10524, :total 3192.65M}
    ...

### linq18: SelectMany - Multiple from

```csharp
//c#
public void Linq18()
{
    List<Customer> customers = GetCustomerList();

    DateTime cutoffDate = new DateTime(1997, 1, 1);

    var orders =
        from c in customers
        where c.Region == "WA"
        from o in c.Orders
        where o.OrderDate >= cutoffDate
        select new { c.CustomerID, o.OrderID };

    ObjectDumper.Write(orders);
}
```

```java
//java
List<Customer> customerList=Objects.getCustomerList();
        LocalDateTime cutoffDate=LocalDateTime.of(1997,1,1,0,0,0);

        customerList.stream()
        .filter(c->"WA".equals(c.region))
        .flatMap(c->c.orders.stream()
        .filter(o->o.orderDate.isAfter(cutoffDate))
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        }})).forEach(ObjectDumper::dump);
```

#### Output

    {:customer-id LAZYK, :order-id 10482}
    {:customer-id LAZYK, :order-id 10545}
    {:customer-id TRAIH, :order-id 10574}
    {:customer-id TRAIH, :order-id 10577}
    {:customer-id TRAIH, :order-id 10822}
    {:customer-id WHITC, :order-id 10469}
    {:customer-id WHITC, :order-id 10483}
    {:customer-id WHITC, :order-id 10504}
    {:customer-id WHITC, :order-id 10596}
    {:customer-id WHITC, :order-id 10693}
    {:customer-id WHITC, :order-id 10696}
    {:customer-id WHITC, :order-id 10723}
    {:customer-id WHITC, :order-id 10740}
    {:customer-id WHITC, :order-id 10861}
    {:customer-id WHITC, :order-id 10904}
    {:customer-id WHITC, :order-id 11032}
    {:customer-id WHITC, :order-id 11066}

### linq19: SelectMany - Indexed

```csharp
//c#
public void Linq19()
{
    List<Customer> customers = GetCustomerList();

    var customerOrders =
        customers.SelectMany(
            (cust, custIndex) =>
            cust.Orders.Select(o => "Customer #" + (custIndex + 1) +
                                    " has an order with OrderID " + o.OrderID));

    ObjectDumper.Write(customerOrders);
}
```

```java
//java
List<Customer> customerList=Objects.getCustomerList();
        IntStream.range(0,customerList.size())
        .mapToObj(i->customerList.get(i).orders.stream().map(o->
        "Customer #"+(i+1)+" has an order with OrderID "+o.orderID))
        .flatMap(x->x).forEach(System.out::println);
```

#### Output

    Customer #1 has an order with OrderID 10643
    Customer #1 has an order with OrderID 10692
    Customer #1 has an order with OrderID 10702
    Customer #1 has an order with OrderID 10835
    Customer #1 has an order with OrderID 10952
    Customer #1 has an order with OrderID 11011
    Customer #2 has an order with OrderID 10308
    Customer #2 has an order with OrderID 10625
    Customer #2 has an order with OrderID 10759
    Customer #2 has an order with OrderID 10926
    ...

### linq20: This sample take First 3 numbers.

> This sample uses a partition/slice to get only the first 3 elements of the array.

```csharp
//c#
static void Linq20()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var first3Numbers = numbers.Take(3);

    Console.WriteLine("First 3 numbers:");
    first3Numbers.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq20(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("First 3 numbers:");
        Arrays.stream(numbers).limit(3).forEach(System.out::println);
        }
```

#### Output

    First 3 numbers:
    5
    4
    1

### linq21: This sample take First 3 orders in WA.

> This sample uses a partition/slice to get the first 3 orders from customers in Washington.

```csharp
//c#
static void Linq21()
{
    var customers = GetCustomerList();

    var first3WAOrders = customers
        .Where(c => c.Region == "WA")
        .SelectMany(customer => customer.Orders, (customer, order) => new { customer, order })
        .Select(x => 
            new
            {
                x.customer.CustomerID, 
                x.order.OrderID, 
                x.order.OrderDate
            })
        .Take(3);

    Console.WriteLine("First 3 orders in WA:");
    first3WAOrders.ForEach(ObjectDumper.Write);
}
```

```java
//java
static void linq21(){
        List<Customer> customerList=Objects.getCustomerList();
        System.out.println("First 3 orders in WA:");
        customerList.stream().filter(c->"WA".equals(c.region))
        .flatMap(c->c.orders.stream()
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        put("orderDate",o.orderDate);
        }})).limit(3)
        .forEach(ObjectDumper::dump);
        }
```

#### Output

    First 3 orders in WA:
    (orderID=10482,customerID=LAZYK,orderDate=1997-03-21T00:00)
    (orderID=10545,customerID=LAZYK,orderDate=1997-05-22T00:00)
    (orderID=10574,customerID=TRAIH,orderDate=1997-06-19T00:00)

### linq22: Skip - Simple

> This sample uses a partition to get all but the first four elements of the array.

```csharp
//c#
static void Linq22()
{
    var numbers = new []{ 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var allButFirst4Numbers = numbers.Skip(4);

    Console.WriteLine("All but first 4 numbers:");
    allButFirst4Numbers.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq22(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("All but first 4 numbers:");
        Arrays.stream(numbers).skip(4).forEach(System.out::println);
        }
```

#### Output

    All but first 4 numbers:
    9
    8
    6
    7
    2
    0

### linq23: Skip - Nested

> This sample uses Take to get all but the first 2 orders from customers in Washington.

```csharp
//c#
static void Linq23()
{
    var customers = GetCustomerList();

    var waOrders = customers
        .Where(c => c.Region == "WA")
        .SelectMany(customer => customer.Orders, (customer, order) => new { customer, order })
        .Select(x => 
            new
            {
                x.customer.CustomerID, 
                x.order.OrderID, 
                x.order.OrderDate
            });

    var allButFirst2Orders = waOrders.Skip(2);

    Console.WriteLine("All but first 2 orders in WA:");
    ObjectDumper.Write(allButFirst2Orders);
}
```

```java
//java
static void linq23(){
        List<Customer> customerList=Objects.getCustomerList();
        System.out.println("All but first 2 orders in WA:");
        customerList.stream().filter(c->"WA".equals(c.region))
        .flatMap(c->c.orders.stream()
        .map(o->new HashMap<String, Object>(){{
        put("customerID",c.customerID);
        put("orderID",o.orderID);
        put("orderDate",o.orderDate);
        }})).skip(2)
        .forEach(ObjectDumper::dump);
        }
```

#### Output

    All but first 2 orders in WA:
    (orderID=10574,customerID=TRAIH,orderDate=1997-06-19T00:00)
    (orderID=10577,customerID=TRAIH,orderDate=1997-06-23T00:00)
    (orderID=10822,customerID=TRAIH,orderDate=1998-01-08T00:00)
    (orderID=10269,customerID=WHITC,orderDate=1996-07-31T00:00)
    (orderID=10344,customerID=WHITC,orderDate=1996-11-01T00:00)
    (orderID=10469,customerID=WHITC,orderDate=1997-03-10T00:00)
    (orderID=10483,customerID=WHITC,orderDate=1997-03-24T00:00)
    (orderID=10504,customerID=WHITC,orderDate=1997-04-11T00:00)
    (orderID=10596,customerID=WHITC,orderDate=1997-07-11T00:00)
    (orderID=10693,customerID=WHITC,orderDate=1997-10-06T00:00)
    (orderID=10696,customerID=WHITC,orderDate=1997-10-08T00:00)
    (orderID=10723,customerID=WHITC,orderDate=1997-10-30T00:00)
    (orderID=10740,customerID=WHITC,orderDate=1997-11-13T00:00)
    (orderID=10861,customerID=WHITC,orderDate=1998-01-30T00:00)
    (orderID=10904,customerID=WHITC,orderDate=1998-02-24T00:00)
    (orderID=11032,customerID=WHITC,orderDate=1998-04-17T00:00)
    (orderID=11066,customerID=WHITC,orderDate=1998-05-01T00:00)

### linq24: TakeWhile - Simple

> This sample uses a partition to return elements starting from the beginning of the array until a number is read whose value is not less than 6.

```csharp
//c#
static void Linq24()
{
    var numbers = new[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var firstNumbersLessThan6 = numbers.TakeWhile(n => n < 6);

    Console.WriteLine("First numbers less than 6:");
    firstNumbersLessThan6.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq24(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("First numbers less than 6:");
        Arrays.stream(numbers).takeWhile(num->num<6).forEach(System.out::println);
        }
```

#### Output

    First numbers less than 6:
    5
    4
    1
    3

### linq25: TakeWhile - Indexed

> This sample uses a partition to return elements starting from the beginning of the array until a number is hit that is less than its position in the array.

```csharp
//c#
static void Linq25()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var firstSmallNumbers = numbers.TakeWhile((n, index) => n >= index);

    Console.WriteLine("First numbers not less than their position:");
    firstSmallNumbers.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq25(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("First numbers not less than their position:");
        IntStream.range(0,numbers.length)
        .mapToObj(index->new Object(){
        int Num=numbers[index];
        int Index=index;
        }).takeWhile(x->x.Num>=x.Index).forEach(x->System.out.println(x.Num));
        }
```

#### Output

    First numbers not less than their position:
    5
    4

### linq26: SkipWhile - Simple

> This sample uses a partition to get the elements of the array starting from the first element divisible by 3.

```csharp
//c#
static void Linq26()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var allButFirst3Numbers = numbers.SkipWhile(n => n % 3 != 0);

    Console.WriteLine("All elements starting from first element divisible by 3:");
    allButFirst3Numbers.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq26(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("All elements starting from first element divisible by 3:");
        Arrays.stream(numbers)
        .dropWhile(n->n%3!=0).forEach(System.out::println);
        }
```

#### Output

    All elements starting from first element divisible by 3:
    3
    9
    8
    6
    7
    2
    0

### linq27: SkipWhile - Indexed

> This sample uses a partition to get the elements of the array starting from the first element less than its position.

```csharp
//c#
static void Linq27()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var laterNumbers = numbers.SkipWhile((n, index) => n >= index);

    Console.WriteLine("All elements starting from first element less than its position:");
    laterNumbers.ForEach(Console.WriteLine);
}
```

```java
//java
static void linq27(){
        int[]numbers={5,4,1,3,9,8,6,7,2,0};
        System.out.println("All elements starting from first element less than its position:");
        IntStream.range(0,numbers.length)
        .mapToObj(index->new Object(){
        int Num=numbers[index];
        int Index=index;
        }).dropWhile(x->x.Num>=x.Index).forEach(x->System.out.println(x.Num));
        }
```

#### Output

    All elements starting from first element less than its position:
    1
    3
    9
    8
    6
    7
    2
    0

LINQ - Ordering Operators
-------------------------

### linq28: OrderBy - Simple 1

> This sample uses ordering to sort a list of words alphabetically.

```csharp
//c#
static void Linq28()
{
    var words = new [] { "cherry", "apple", "blueberry" };

    var sortedWords = words.OrderBy(w => w);

    Console.WriteLine("The sorted list of words:");
    sortedWords.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq28(){
        String[]words=new String[]{"cherry","apple","blueberry"};
        Stream<String> stringStream=Arrays.stream(words).sorted();
        System.out.println("The sorted list of words:");
        stringStream.forEach(System.out::println);
        }
```

#### Output

    The sorted list of words:
    apple
    blueberry
    cherry

### linq29: OrderBy - Simple 2

> This sample uses ordering to sort a list of words by length.

```csharp
//c#
static void Linq29()
{
    var words = new [] { "cherry", "apple", "blueberry" };

    var sortedWords = words.OrderBy(w => w.Length);

    Console.WriteLine("The sorted list of words (by length):"); 
    sortedWords.ForEach(Console.WriteLine);
}
```

```java
//java
public static void Linq29(){
        var words=new String[]{"cherry","apple","blueberry"};

        var sortedWords=Arrays.stream(words)
        .sorted(Comparator.comparing(s->s.length()));

        print("The sorted list of words:");
        sortedWords.forEach(System.out::println);
        }
```

#### Output

    The sorted list of words (by length):
    apple
    cherry
    blueberry

### linq30: OrderBy - Simple 3

> This sample uses ordering to sort a list of products by name.

```csharp
//c#
static void Linq30()
{
    var products = GetProductList();

    var sortedProducts = products.OrderBy(p => p.ProductName);

    ObjectDumper.Write(sortedProducts);
}
```

```java
//java
public static void linq30(){
        List<Product> products=Objects.getProductList();
        Stream<Product> productStream=products.stream().sorted(Comparator.comparing(a->a.productName));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    {productId: 17, productName: Alice Mutton, category: Meat/Poultry, unitPrice: 39.0, unitsInStock: 0}
    {productId: 3, productName: Aniseed Syrup, category: Condiments, unitPrice: 10.0, unitsInStock: 13}
    {productId: 40, productName: Boston Crab Meat, category: Seafood, unitPrice: 18.4, unitsInStock: 123}
    {productId: 60, productName: Camembert Pierrot, category: Dairy Products, unitPrice: 34.0, unitsInStock: 19}
    {productId: 18, productName: Carnarvon Tigers, category: Seafood, unitPrice: 62.5, unitsInStock: 42}
    ...

### linq31: OrderBy - Comparer

> This sample uses case-insensitive ordering to sort the words in an array.

```csharp
//c#
static void Linq31()
{
    var words = new [] { "aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry" }; 

    var sortedWords = words.OrderBy(a => a, StringComparer.CurrentCultureIgnoreCase); 

    ObjectDumper.Write(sortedWords); 
}
```

```java
//java
public static void linq31(){
        String[]words=new String[]{"aPPLE","AbAcUs","bRaNcH","BlUeBeRrY","ClOvEr","cHeRry"};
        Stream<String> stringStream=Arrays.stream(words).sorted(Comparator.comparing(String::toString,String.CASE_INSENSITIVE_ORDER));
        stringStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    AbAcUs
    aPPLE
    BlUeBeRrY
    bRaNcH
    cHeRry
    ClOvEr

### linq32: OrderByDescending - Simple 1

> This sample uses reverse ordering to sort a list of doubles from highest to lowest.

```csharp
//c#
static void Linq32()
{
    var doubles = new[]{ 1.7, 2.3, 1.9, 4.1, 2.9 };

    var sortedDoubles = doubles.OrderByDescending(d => d);

    Console.WriteLine("The doubles from highest to lowest:");
    sortedDoubles.ForEach(Console.WriteLine);
}
```

```java
//java
public static void Linq32(){
        var doubles=new Double[]{1.7,2.3,1.9,4.1,2.9};

        var sortedDoubles=Arrays.stream(doubles)
        .sorted(Comparator.reverseOrder());

        print("The doubles from highest to lowest:");
        sortedDoubles.forEach((System.out::println));
        }
```

#### Output

    The doubles from highest to lowest:
    4.1
    2.9
    2.3
    1.9
    1.7

### linq33: OrderByDescending - Simple 2

> This sample uses reverse ordering to sort a list of products by units in stock from highest to lowest.

```csharp
//c#
static void Linq33()
{
    var products = GetProductList();

    var sortedProducts = products.OrderByDescending(p => p.UnitsInStock);

    ObjectDumper.Write(sortedProducts);
}
```

```java
//java
public static void linq33(){
        List<Product> products=Objects.getProductList();
        Stream<Product> productStream=products.stream().sorted(Comparator.comparing(m->m.unitPrice,Comparator.reverseOrder()));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    {productId: 75, productName: Rh�nbr�u Klosterbier, category: Beverages, unitPrice: 7.75, unitsInStock: 125}
    {productId: 40, productName: Boston Crab Meat, category: Seafood, unitPrice: 18.4, unitsInStock: 123}
    {productId: 6, productName: Grandma's Boysenberry Spread, category: Condiments, unitPrice: 25.0, unitsInStock: 120}
    {productId: 55, productName: P�t� chinois, category: Meat/Poultry, unitPrice: 24.0, unitsInStock: 115}
    {productId: 61, productName: Sirop d'�rable, category: Condiments, unitPrice: 28.5, unitsInStock: 113}
    ...

### linq34: OrderByDescending - Comparer

> This sample uses reverse case-insensitive ordering to sort the words in an array.

```csharp
//c#
static void Linq34()
{
    var words = new [] { "aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry" };

    var sortedWords = words.OrderByDescending(a => a, StringComparer.CurrentCultureIgnoreCase); 

    ObjectDumper.Write(sortedWords);
}
```

```java
//java
public static void linq34() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER).reversed());
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    ClOvEr
    cHeRry
    bRaNcH
    BlUeBeRrY
    aPPLE
    AbAcUs

### linq35: ThenBy - Simple

> This sample uses nested ordering, first by length of their name, and then alphabetically by the name itself.

```csharp
//c#
static void Linq35()
{
    var digits = new [] { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var sortedDigits = digits
        .OrderBy(d => d.Length)
        .ThenBy(d => d);

    Console.WriteLine("Sorted digits:");
    sortedDigits.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq35() {
        String[] words = new String[]{ "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    Sorted digits:
    one
    six
    two
    five
    four
    nine
    zero
    eight
    seven
    three

### linq36: ThenBy - Comparer

> This sample uses case-insensitive nested ordering, with a custom comparer to sort first by word length and then by a case-insensitive sort of the words in an array.

```csharp
//c#
static void Linq36()
{
    var words = new [] { "aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry" };

    var sortedWords = words
        .OrderBy(a => a.Length)
        .ThenBy(a => a, StringComparer.CurrentCultureIgnoreCase);

    ObjectDumper.Write(sortedWords);
}
```

```java
//java
public static void linq36() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString,String.CASE_INSENSITIVE_ORDER));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    aPPLE
    AbAcUs
    bRaNcH
    cHeRry
    ClOvEr
    BlUeBeRrY

### linq37: ThenByDescending - Simple

> This sample uses nested ordering to sort a list of products, first by category, and then by unit price, from highest to lowest.

```csharp
//c#
public void Linq37() 
{ 
    List<Product> products = GetProductList(); 

    var sortedProducts = products
        .OrderBy(p => p.Category)
        .ThenByDescending(p => p.UnitPrice);

    ObjectDumper.Write(sortedProducts); 
}
```

```java
//java
public static void linq37() {
        List<Product> products = Objects.getProductList();
        Stream<Product> productStream = products.stream().sorted(Comparator.comparing(Product::getCategory).thenComparing(Product::getUnitPrice,Comparator.reverseOrder()));
        productStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    {productId: 38, productName: C�te de Blaye, category: Beverages, unitPrice: 263.5, unitsInStock: 17}
    {productId: 43, productName: Ipoh Coffee, category: Beverages, unitPrice: 46.0, unitsInStock: 17}
    {productId: 2, productName: Chang, category: Beverages, unitPrice: 19.0, unitsInStock: 17}
    {productId: 76, productName: Lakkalik��ri, category: Beverages, unitPrice: 18.0, unitsInStock: 57}
    {productId: 39, productName: Chartreuse verte, category: Beverages, unitPrice: 18.0, unitsInStock: 69}
    {productId: 1, productName: Chai, category: Beverages, unitPrice: 18.0, unitsInStock: 39}
    {productId: 35, productName: Steeleye Stout, category: Beverages, unitPrice: 18.0, unitsInStock: 20}
    {productId: 70, productName: Outback Lager, category: Beverages, unitPrice: 15.0, unitsInStock: 15}
    {productId: 34, productName: Sasquatch Ale, category: Beverages, unitPrice: 14.0, unitsInStock: 111}
    ...

### linq38: ThenByDescending - Comparer

> This sample uses uses case-insensitive reverse nested ordering to sort first by word length and then by a case-insensitive descending sort of the words in an array.

```csharp
//c#
static void Linq38()
{
    var words = new [] { "aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry" };

    var sortedWords = words
        .OrderBy(a => a.Length)
        .ThenByDescending(a => a, StringComparer.CurrentCultureIgnoreCase);

    ObjectDumper.Write(sortedWords);
}
```

```java
//java
public static void linq38() {
        String[] words = new String[]{"aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry"};
        Stream<String> wordStream = Arrays.stream(words).sorted(Comparator.comparing(String::length).thenComparing(String::toString,String.CASE_INSENSITIVE_ORDER.reversed()));
        wordStream.collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    aPPLE
    ClOvEr
    cHeRry
    bRaNcH
    AbAcUs
    BlUeBeRrY

### linq39: Reverse

> This sample uses reverse ordering to create a list of all digits in the array whose second letter is 'i' that is reversed from the order in the original array.

```csharp
//c#
static void Linq39()
{
    var digits = new [] { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var reversedIDigits = digits
        .Where(d => d[1] == 'i')
        .Reverse();

    Console.WriteLine("A backwards list of the digits with a second character of 'i':");
    reversedIDigits.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq39() {
        String[] words = new String[]{ "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        Stream<String> wordStream = Arrays.stream(words).filter(m->m.charAt(1)=='i');
        System.out.println("A backwards list of the digits with a second character of 'i':");
        wordStream.collect(Collectors.toCollection(LinkedList::new)).descendingIterator().forEachRemaining(System.out::println);
        }
```

#### Output

    A backwards list of the digits with a second character of 'i':
    nine
    eight
    six
    five

LINQ - Grouping Operators
-------------------------

### linq40: GroupBy - Simple 1

> This sample uses grouping to partition a list of numbers by their remainder when divided by 5.

```csharp
//c#
static void Linq40()
{
    var numbers = new[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 }; 

    var numberGroups = numbers
        .GroupBy(n => n % 5)
        .Select(x => 
            new
            {
                Remainder = x.Key, 
                Numbers = x
            });

    numberGroups.ForEach((g) => 
    {
            Console.WriteLine("Numbers with a remainder of {0} when divided by 5:", g.Remainder);
            g.Numbers.ForEach(Console.WriteLine);
    });
}
```

```java
//java
public static void linq40() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        Map<Integer, List<Integer>> numberGroups = Arrays.stream(numbers).boxed().collect(Collectors.groupingBy(n -> n % 5, Collectors.toList()));
        for (Integer key : numberGroups.keySet()
        ) {
        System.out.println("Numbers with a remainder of %d when divided by 5:" + key);
        numberGroups.get(key).forEach(System.out::println);
        }
        }
```

#### Output

    Numbers with a remainder of 0 when divided by 5:
    5
    0
    Numbers with a remainder of 1 when divided by 5:
    1
    6
    Numbers with a remainder of 2 when divided by 5:
    7
    2
    Numbers with a remainder of 3 when divided by 5:
    3
    8
    Numbers with a remainder of 4 when divided by 5:
    4
    9

### linq41: GroupBy - Simple 2

> This sample uses grouping to partition a list of words by their first letter.

```csharp
//c#
static void Linq41()
{
    var words = new [] { "blueberry", "chimpanzee", "abacus", "banana", "apple", "cheese" }; 

    var wordGroups = words
        .GroupBy(w => w[0])
        .Select(g => 
            new
            {
                FirstLetter = g.Key, 
                Words = g
            });

    wordGroups.ForEach((g) => 
    {
        Console.WriteLine($"Words that start with the letter '{g.FirstLetter}':");
        g.Words.ForEach(Console.WriteLine);
    });
}
```

```java
//java
public static void linq41() {
        String[] words = new String[]{"blueberry", "chimpanzee", "abacus", "banana", "apple", "cheese"};
        Map<Character, List<String>> wordGroups = Arrays.stream(words).collect(Collectors.groupingBy(n -> n.charAt(0), Collectors.toList()));
        for (Character key : wordGroups.keySet()
        ) {
        System.out.printf("Words that start with the letter:'%s'%n", key);
        wordGroups.get(key).forEach(System.out::println);
        }
        }
```

#### Output

    Words that start with the letter 'a':
    abacus
    apple
    Words that start with the letter 'b':
    banana
    blueberry
    Words that start with the letter 'c':
    cheese
    chimpanzee

### linq42: GroupBy - Simple 3

> This sample uses grouping to partition a list of products by category.

```csharp
//c#
static void Linq42()
{
    var products = GetProductList(); 
    
    var orderGroups = products
        .GroupBy(p => p.Category)
        .Select(g => 
            new
            {
                Category = g.Key, 
                Products = g
            }); 

    ObjectDumper.Write(orderGroups, 1); 
}
```

```java
//java
public static void linq42() {
        List<Product> products = Objects.getProductList();
        Map<String, List<Product>> productGroups = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.toList()));
        for (String key : productGroups.keySet()
        ) {
        System.out.printf("Products in the category:'%s'%n", key);
        productGroups.get(key).forEach(System.out::println);
        }
        }
```

#### Output

    Products in the category 'Grains/Cereals':
    (Product id=22, name=Gustaf's Knäckebröd, cat=Grains/Cereals, price=21.0, inStock=104)
    (Product id=23, name=Tunnbröd, cat=Grains/Cereals, price=9.0, inStock=61)
    (Product id=42, name=Singaporean Hokkien Fried Mee, cat=Grains/Cereals, price=14.0, inStock=26)
    (Product id=52, name=Filo Mix, cat=Grains/Cereals, price=7.0, inStock=38)
    (Product id=56, name=Gnocchi di nonna Alice, cat=Grains/Cereals, price=38.0, inStock=21)
    (Product id=57, name=Ravioli Angelo, cat=Grains/Cereals, price=19.5, inStock=36)
    (Product id=64, name=Wimmers gute Semmelknödel, cat=Grains/Cereals, price=33.25, inStock=22)
    Products in the category 'Confections':
    (Product id=16, name=Pavlova, cat=Confections, price=17.45, inStock=29)
    (Product id=19, name=Teatime Chocolate Biscuits, cat=Confections, price=9.2, inStock=25)
    ...

### linq43: GroupBy - Nested

> This sample uses nested grouping to partition a list of each customer's orders, first by year, and then by month.

```csharp
//c#
public void Linq43() 
{ 
    var customers = GetCustomerList(); 

    var customerOrderGroups = customers
        .Select(c => new 
        {
            c.CompanyName,
            YearGroups = 
            (
                c.Orders
                    .GroupBy(y => y.OrderDate.Year)
                    .Select(YearGroup => new 
                    {
                        Year = YearGroup.Key,
                        MonthGroups = 
                        (
                            YearGroup
                            .GroupBy(m =>  m.OrderDate.Month)
                            .Select(MonthGroup => new
                            {
                                Month = MonthGroup.Key, Orders = MonthGroup
                            })

                        )
                    })
            )
        });
        
    ObjectDumper.Write(customerOrderGroups, 3); 
} 
```

```java
//java
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
```

#### Output

    {CompanyName:Alfreds Futterkiste{"1997":{"8":[{"orderID":10643,"orderDate":872434800000,"total":814.5}],"10":[{"orderID":10692,"orderDate":875804400000,"total":878},{"orderID":10702,"orderDate":876668400000,"total":330}]},"1998":{"1":[{"orderID":10835,"orderDate":884790000000,"total":845.8}],"3":[{"orderID":10952,"orderDate":889974000000,"total":471.2}],"4":[{"orderID":11011,"orderDate":892047600000,"total":933.5}]}}}
    {CompanyName:Ana Trujillo Emparedados y helados{"1996":{"9":[{"orderID":10308,"orderDate":842972400000,"total":88.8}]},"1997":{"8":[{"orderID":10625,"orderDate":870966000000,"total":479.75}],"11":[{"orderID":10759,"orderDate":880642800000,"total":320}]},"1998":{"3":[{"orderID":10926,"orderDate":888937200000,"total":514.4}]}}}

### linq44: GroupBy - Nested

> This sample uses GroupBy to partition trimmed elements of an array using a custom comparer that matches words that are anagrams of each other.

```csharp
//c#
static void Linq44()
        {
            var anagrams = new [] { "from    ", " salt", " earn ", "  last   ", " near ", " form  " }; 
            var orderGroups = anagrams
                .GroupBy(w => w.Trim(), new AnagramEqualityComparer());

            ObjectDumper.Write(orderGroups, 1); 
        }
```java
//java
public static void linq44() {
        String[] words = new String[]{"from    ", " salt", " earn ", "  last   ", " near ", " form  "};
        Map<String, List<String>> wordGroups = Arrays.stream(words).collect(Collectors.groupingBy(String::trim));
        for (String key : wordGroups.keySet()
        ) {
            List<String> trimdList = wordGroups.get(key).stream().map(m -> m.trim()).collect(Collectors.toList());
            wordGroups.put(key, trimdList);
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
#### Output

### linq45: GroupBy - Nested

> This sample uses GroupBy to partition trimmed elements of an array using a custom comparer that matches words that are anagrams of each other, and then converts the results to uppercase.
```csharp
//c#
static void Linq45()
        {
            var anagrams = new [] { "from   ", " salt", " earn ", "  last   ", " near ", " form  " }; 
        
            var orderGroups = anagrams.GroupBy( 
                        w => w.Trim(),
                        a => a.ToUpper(), 
                        new AnagramEqualityComparer() 
                        ); 
        
            ObjectDumper.Write(orderGroups, 1); 
        }     
```java
//java
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

LINQ - Set Operators
--------------------

### linq46: Distinct - 1

> This sample removes all duplicate elements in a sequence of factors of 300.

```csharp
//c#
static void Linq46()
{
    int[] factorsOf300 = { 2, 2, 3, 5, 5 };

    var uniqueFactors = factorsOf300.Distinct();

    Console.WriteLine("Prime factors of 300:");
    uniqueFactors.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq46() {
        int[] numbers = { 2, 2, 3, 5, 5 };
        Arrays.stream(numbers).boxed().distinct().collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    Prime factors of 300:
    2
    3
    5

### linq47: Distinct - 2

> This sample gets distint Category names from all the products.

```csharp
//c#
static void Linq47()
{
    var products = GetProductList();

    var categoryNames = products
        .Select(p => p.Category)
        .Distinct();

    Console.WriteLine("Category names:");
    categoryNames.ForEach(Console.WriteLine);
}
```

```java
//java
public static void Linq47(){
        var products=Data.getProductList();

        var productCategories=products.stream()
        .map(p->p.category)
        .distinct();

        print("Category names:");
        productCategories.forEach(System.out::println);
        }
```

#### Output

    Category names:
    Beverages
    Dairy Products
    Condiments
    Meat/Poultry
    Produce
    Seafood
    Grains/Cereals
    Confections

### linq48: Union - 1

> This sample creates a Union of sequences that contains unique values from both arrays.

```csharp
//c#
static void Linq48()
{
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var uniqueNumbers = numbersA.Union(numbersB);

    Console.WriteLine("Unique numbers from both arrays:");
    uniqueNumbers.ForEach(Console.WriteLine);
}  
```

```java
//java
public static void linq48() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Unique numbers from both arrays:");
        Stream.concat(Arrays.stream(numbersA).boxed(), Arrays.stream(numbersB).boxed()).distinct().sorted().collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    Unique numbers from both arrays:
    0
    1
    2
    3
    4
    5
    6
    7
    8
    9

### linq49: Union - 2

> This sample creates a Union of sequences that contains the distinct first letter from both product and customer names

```csharp
//c#
static void Linq49()
{
    var products = GetProductList();
    var customers = GetCustomerList();

    var productFirstChars = products.Select(p => p.ProductName[0]);
    var customerFirstChars = customers.Select(c => c.CompanyName[0]);

    var uniqueFirstChars = productFirstChars.Union(customerFirstChars);

    Console.WriteLine("Unique first letters from Product names and Customer names:");
    uniqueFirstChars.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq49() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("Unique first letters from Product names and Customer names:");
        Stream.concat(productList.stream().map(a -> a.productName.charAt(0)), customerList.stream().map(b -> b.companyName.charAt(0))).distinct().sorted().collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    Unique first letters from Product names and Customer names:
    G
    W
    R
    I
    P
    Z
    L
    U
    C
    O
    N
    F
    A
    D
    T
    E
    J
    K
    S
    Q
    H
    M
    B
    V

### linq50: Intersect - 1

> This sample creates Intersection that contains the common values shared by both arrays.

```csharp
//c#
static void Linq50()
{
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var commonNumbers = numbersA.Intersect(numbersB);

    Console.WriteLine("Common numbers shared by both arrays:");
    commonNumbers.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq50() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Common numbers shared by both arrays:");
        Arrays.stream(numbersA).boxed().filter(o ->
        (Arrays.stream(numbersB).boxed().anyMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
        }
```

#### Output

    Common numbers shared by both arrays:
    8
    5

### linq51: Intersect - 2

> This sample creates Intersection that contains contains the common first letter from both product and customer names.

```csharp
//c#
static void Linq51()
{
    var products = GetProductList();
    var customers = GetCustomerList();

    var productFirstChars = products.Select(p => p.ProductName[0]);
    var customerFirstChars = customers.Select(c => c.CompanyName[0]);

    var commonFirstChars = productFirstChars.Intersect(customerFirstChars);

    Console.WriteLine("Common first letters from Product names and Customer names:");
    commonFirstChars.ForEach(Console.WriteLine);
} 
```

```java
//java
public static void linq51() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("Common first letters from Product names and Customer names:");
        productList.stream().map(m -> m.productName.charAt(0)).filter(o ->
        (customerList.stream().map(n -> n.companyName.charAt(0)).anyMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
        }
```

#### Output

    Common first letters from Product names and Customer names:
    C
    A
    G
    N
    M
    I
    Q
    K
    T
    P
    S
    R
    B
    V
    F
    E
    W
    L
    O

### linq52: Except - 1

> This sample creates a sequence that excludes the values from the second sequence.

```csharp
//c#
public static void Linq52() {
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var uniqueNumbers = Arrays.stream(numbersA)
            .filter(a -> Arrays.stream(numbersB).noneMatch(b -> b == a));

    print("Common numbers shared by both arrays:");
    uniqueNumbers.forEach(System.out::println);
}
```

```java
//java
public static void linq52() {
        int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
        int[] numbersB = {1, 3, 5, 7, 8};
        System.out.println("Numbers in first array but not second array:");
        Arrays.stream(numbersA).boxed().filter(o ->
        (Arrays.stream(numbersB).boxed().noneMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
        }
```

#### Output

    Numbers in first array but not second array:
    0
    2
    4
    6
    9

### linq53: Except - 2

> This sample creates a sequence that the first letters of product names that but excludes letters of customer names first letter.

```csharp
//c#
static void Linq53()
{
    var products = GetProductList();
    var customers = GetCustomerList();

    var productFirstChars = products.Select(p => p.ProductName[0]);
    var customerFirstChars = customers.Select(c => c.CompanyName[0]);

    var productOnlyFirstChars = productFirstChars.Except(customerFirstChars);

    Console.WriteLine("First letters from Product names, but not from Customer names:");
    productOnlyFirstChars.ForEach(Console.WriteLine);
}   
```

```java
//java
public static void linq53() {
        List<Product> productList = Objects.getProductList();
        List<Customer> customerList = Objects.getCustomerList();
        System.out.println("First letters from Product names, but not from Customer names:");
        productList.stream().map(m -> m.productName.charAt(0)).filter(o ->
        (customerList.stream().map(m -> m.companyName.charAt(0)).noneMatch(b -> b.equals(o)))
        ).distinct().forEach(System.out::println);
        }
```

#### Output

    First letters from Product names, but not from Customer names:
    U
    J
    Z

LINQ - Conversion Operators
---------------------------

### linq54: ToArray

> This sample converts a list ti an array.

```csharp
//c#
static void Linq54()
{
    var list = new List<double> { 1.7, 2.3, 1.9, 4.1, 2.9 };

    var doublesArray = list
        .OrderByDescending(d => d)
        .ToArray();
        
    Console.WriteLine("Every other double from highest to lowest:");
    for (var d = 0; d < doublesArray.Length; d += 2)
    {
        Console.WriteLine(doublesArray[d]);
    }
}
```

```java
//java
public static void linq54() {
        List<Double> list = Arrays.asList(1.7, 2.3, 1.9, 4.1, 2.9);
        System.out.println("Every other double from highest to lowest:");
        double[] doubleArray = list.stream().sorted(Comparator.reverseOrder()).mapToDouble(x -> x).toArray();
        for (double o : doubleArray
        ) {
        System.out.println(o);
        }
        }
```

#### Output

    Every other double from highest to lowest:
    4.1
    2.3
    1.7

### linq55: ToList

> This sample converts an array to a list

```csharp
//c#
static void Linq55()
{
    var words = new[] { "cherry", "apple", "blueberry" };

    var wordList = words
        .OrderBy(x => x)
        .ToList();

    Console.WriteLine("The sorted word list:");
    wordList.ForEach(Console.WriteLine);
}
```

```java
//java
public static void linq55() {
        String[] wordsArray = {"cherry", "apple", "blueberry"};
        System.out.println("The sorted word list:");
        Arrays.stream(wordsArray).sorted().collect(Collectors.toList()).forEach(System.out::println);
        }
```

#### Output

    The sorted word list:
    apple
    blueberry
    cherry

### linq56: ToDictionary

> This sample converts an array of records to a dictionary

```csharp
//c#
static void Linq56()
{
    var scoreRecords = 
        new[] 
        { 
            new {Name = "Alice", Score = 50},
            new {Name = "Bob"  , Score = 40},
            new {Name = "Cathy", Score = 45}
        };

    var scoreRecordsDict = scoreRecords.ToDictionary(sr => sr.Name);

    Console.WriteLine("Bob's score: {0}", scoreRecordsDict["Bob"]);
}
```

```java
//java
public static void linq56() {
        ArrayList<StudentScore> scoreRecords = new ArrayList<>();
        scoreRecords.add(new StudentScore("Alice", 50));
        scoreRecords.add(new StudentScore("Bob", 40));
        scoreRecords.add(new StudentScore("Cathy", 45));
        Map<String, Integer> scoresMap = scoreRecords.stream().collect(Collectors.toMap(StudentScore::getName, StudentScore::getScore));
        System.out.printf("Bob's score: %s", scoresMap.get("Bob"));
        }
```

#### Output

    Bob's score: {Name: Bob, Score: 40}

### linq57: OfType

> This sample filters all elements that matches the type double/float.

```csharp
//c#
static void Linq57()
{
    var numbers = new object[]{ null, 1.0, "two", 3, "four", 5, "six", 7.0 };

    var doubles = numbers.OfType<double>();

    Console.WriteLine("Numbers stored as doubles:");
    doubles.ForEach(Console.WriteLine);
} 
```

```java
//java
public static void linq57() {
        Object[] objectsArray = {null, 1.0, "two", 3, "four", 5, "six", 7.0};
        Arrays.stream(objectsArray).filter(Double.class::isInstance).forEach(System.out::println);
        }
```

#### Output

    Numbers stored as doubles:
    1.0
    7.0

LINQ - Element Operators
------------------------

### linq58: First - Simple

> This sample returns the first matching element as a Product, instead of as a sequence containing a Product.

```csharp
//c#
static void Linq58()
{
    var products = GetProductList();

    var product12 = products.First(p => p.ProductID == 12);

    ObjectDumper.Write(product12);
}
```

```java
//java
public static void Linq58(){
        var products=Data.getProductList();

        var produdct12=products.stream()
        .filter(p->p.productId==12)
        .findFirst();

        System.out.println(produdct12);
        }
```

#### Output

    Optional[(Product id=12, name=Queso Manchego La Pastora, cat=Dairy Products, price=38.0, inStock=86)]

### linq59: First - Condition

> This sample finds the first element in the array that starts with 'o'.

```csharp
//c#
static void Linq59()
{
    var strings = new []{ "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var startsWithO = strings.First(s => s.StartsWith('o'));

    Console.WriteLine("A string starting with 'o': {0}", startsWithO);
}
```

```java
//java
public static void Linq59(){
        var strings=new String[]{"zero","one","two","three","four","five","six","seven","eight","nine"};

        var startsWithO=Arrays.stream(strings)
        .filter(s->s.startsWith("o"))
        .findFirst();

        print("A string starting with 'o': %s",startsWithO);
        }
```

#### Output

    A string starting with 'o': one

### linq61: FirstOrDefault - Simple

> This sample returns the first or default if nothing is found, to try to return the first element of the sequence unless there are no elements, in which case the default value for that type is returned.

```csharp
//c#
static void Linq61()
{
    var numbers = new int[0];

    var firstNumOrDefault = numbers.FirstOrDefault();

    Console.WriteLine(firstNumOrDefault);
}
```

```java
//java
public static void Linq61(){
        var numbers=new int[0];

        var firstNumOrdDefault=Arrays.stream(numbers).boxed()
        .findFirst()
        .orElse(0);

        System.out.println(firstNumOrdDefault);
        }
```

#### Output

    0

### linq62: FirstOrDefault - Condition

> This sample returns the first or default if nothing is found, to return the first product whose ProductID is 789 as a single Product object, unless there is no match, in which case null is returned.

```csharp
//c#
static void Linq62()
{
    var products = GetProductList();

    var product789 = products.FirstOrDefault(p => p.ProductID == 789);

    Console.WriteLine("Product 789 exists: {0}", product789 != null);
}
```

```java
//java
public static void Linq62(){
        var products=Data.getProductList();

        var product789=products.stream()
        .filter(p->p.productId==789)
        .findFirst()
        .orElse(null);

        print("Product 789 exists: %s",product789!=null);
        }
```

#### Output

    Product 789 exists: false

### linq64: ElementAt

> This sample retrieve the second number greater than 5 from an array.

```csharp
//c#
static void Linq64()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var fourthLowNum = numbers
        .Where(num => num > 5)
        .ElementAt(1);

    Console.WriteLine("Second number > 5: {0}", fourthLowNum);
}           
```

```java
//java
public static void Linq64(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var secondNumberGreaterThan5=Arrays.stream(numbers).boxed()
        .filter(n->n>5)
        .toArray()
        [1];

        print("Second number > 5: %d",secondNumberGreaterThan5);
        }
```

#### Output

    Second number > 5: 8

LINQ - Generation Operators
---------------------------

### linq65: Range

> This sample uses generates a sequence of numbers from 100 to 149 that is used to find which numbers in that range are odd and even.

```csharp
//c#
static void Linq65()
{
    var numbers = Enumerable.Range(100, 50)
        .Select(n => 
            new
            {
                Number = n, 
                OddEven = n % 2 == 1 ? "odd" : "even"
            });

    numbers.ForEach((n) => Console.WriteLine("The number {0} is {1}.", n.Number, n.OddEven));
}
```

```java
//java
public static void Linq65(){
        var numbersEvenOdd=IntStream.range(100,150)
        .mapToObj(n->new Object(){
        int Number=n;
        String OddEven=(n%2==0?"even":"odd");
        })
        .collect(Collectors.toList());

        for(var num:numbersEvenOdd){
        print("The number %d is %s",num.Number,num.OddEven);
        }
        }
```

#### Output

    The number 100 is even.
    The number 101 is odd.
    The number 102 is even.
    The number 103 is odd.
    The number 104 is even.
    The number 105 is odd.
    The number 106 is even.
    The number 107 is odd.
    The number 108 is even.
    The number 109 is odd.
    The number 110 is even.
    ...

### linq66: Repeat

> This sample uses generates a sequence of repeated numbers that contains the number 7 ten times.

```csharp
//c#
static void Linq66()
{
    var numbers = Enumerable.Repeat(7, 10);

    numbers.ForEach(Console.WriteLine);
}  
```

```java
//java
public static void Linq66(){
        var numbers=new int[10];

        Arrays.fill(numbers,7);

        for(int number:numbers){
        System.out.println(number);
        }
        }
```

#### Output

    7
    7
    7
    7
    7
    7
    7
    7
    7
    7

LINQ - Quantifiers
------------------

### linq67: Any - Simple

> This sample uses determines if Any of the words in the array contain the substring 'ei'.

```csharp
//c#
static void Linq67()
{
    var words = new []{ "believe", "relief", "receipt", "field" };

    var iAfterE = words.Any(w => w.Contains("ei"));

    Console.WriteLine($"There is a word in the list that contains 'ei': {iAfterE}");
}
```

```java
//java
public static void Linq67(){
        var words=new String[]{"believe","relief","receipt","field"};

        var iAfterE=Arrays.stream(words)
        .anyMatch(w->w.contains("ei"));

        print("There is a word that contains in the list that contains 'ei': %s",iAfterE);
        }
```

#### Output

    There is a word that contains in the list that contains 'ei': true

### linq69: Any - Grouped

> This sample determines if Any of the grouped a list of products only for categories that have at least one product that is out of stock.

```csharp
//c#
static void Linq69()
{
    var products = GetProductList();

    var productGroups = products
        .GroupBy(prod => prod.Category)
        .Where(prodGroup => prodGroup.Any(p => p.UnitsInStock == 0))
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                Products = prodGroup
            });

    ObjectDumper.Write(productGroups, 1);
}
```

```java
//java
public static void Linq69(){
        print("TODO");
        }
```

#### Output

    {Category: Condiments, Products: {{productId: 3, productName: Aniseed Syrup, category: Condiments, unitPrice: 10.0, unitsInStock: 13}, {productId: 4, productName: Chef Anton's Cajun Seasoning, category: Condiments, unitPrice: 22.0, unitsInStock: 53}, {productId: 5, productName: Chef Anton's Gumbo Mix, category: Condiments, unitPrice: 21.35, unitsInStock: 0}, {productId: 6, productName: Grandma's Boysenberry Spread, category: Condiments, unitPrice: 25.0, unitsInStock: 120}, {productId: 8, productName: Northwoods Cranberry Sauce, category: Condiments, unitPrice: 40.0, unitsInStock: 6}, {productId: 15, productName: Genen Shouyu, category: Condiments, unitPrice: 15.5, unitsInStock: 39}, {productId: 44, productName: Gula Malacca, category: Condiments, unitPrice: 19.45, unitsInStock: 27}, {productId: 61, productName: Sirop d'�rable, category: Condiments, unitPrice: 28.5, unitsInStock: 113}, {productId: 63, productName: Vegie-spread, category: Condiments, unitPrice: 43.9, unitsInStock: 24}, {productId: 65, productName: Louisiana Fiery Hot Pepper Sauce, category: Condiments, unitPrice: 21.05, unitsInStock: 76}, {productId: 66, productName: Louisiana Hot Spiced Okra, category: Condiments, unitPrice: 17.0, unitsInStock: 4}, {productId: 77, productName: Original Frankfurter gr�ne So�e, category: Condiments, unitPrice: 13.0, unitsInStock: 32}}}
    ...

### linq70: All - Simple

> This sample determines if All the elements in the array contain only odd numbers.

```csharp
//c#
static void Linq70()
{
    var numbers = new [] { 1, 11, 3, 19, 41, 65, 19 };

    var onlyOdd = numbers.All(n => n % 2 == 1);

    Console.WriteLine($"The list contains only odd numbers: {onlyOdd}");
}
```

```java
//java
public static void Linq70(){
        var numbers=new int[]{1,11,3,19,41,65,19};

        var onlyOdd=Arrays.stream(numbers)
        .allMatch(n->n%2==1);

        print("The list contains only odd numbers: %s",onlyOdd);
        }
```

#### Output

    The list contains only odd numbers: true

### linq72: All - Grouped

> This sample determines if All elements in the grouped a list of products by categories, have all of their products in stock.

```csharp
//c#
static void Linq72()
{
    var products = GetProductList();

    var productGroups = products
        .GroupBy(prod => prod.Category)
        .Where(prodGroup => prodGroup.All(p => p.UnitsInStock > 0))
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                Products = prodGroup
            });

    ObjectDumper.Write(productGroups, 1);
}
```

```java
//java
public static void Linq71(){
        print("TODO");
        }
```

#### Output

    {Category: Grains/Cereals, Products: {{productId: 22, productName: Gustaf's Kn�ckebr�d, category: Grains/Cereals, unitPrice: 21.0, unitsInStock: 104}, {productId: 23, productName: Tunnbr�d, category: Grains/Cereals, unitPrice: 9.0, unitsInStock: 61}, {productId: 42, productName: Singaporean Hokkien Fried Mee, category: Grains/Cereals, unitPrice: 14.0, unitsInStock: 26}, {productId: 52, productName: Filo Mix, category: Grains/Cereals, unitPrice: 7.0, unitsInStock: 38}, {productId: 56, productName: Gnocchi di nonna Alice, category: Grains/Cereals, unitPrice: 38.0, unitsInStock: 21}, {productId: 57, productName: Ravioli Angelo, category: Grains/Cereals, unitPrice: 19.5, unitsInStock: 36}, {productId: 64, productName: Wimmers gute Semmelkn�del, category: Grains/Cereals, unitPrice: 33.25, unitsInStock: 22}}}
    ...

LINQ - Aggregate Operators
--------------------------

### linq73: Count - Simple

> This sample gets the number of unique prime factors of 300.

```csharp
//c#
static void Linq73()
{
    var primeFactorsOf300 = new [] { 2, 2, 3, 5, 5 };

    var uniqueFactors = primeFactorsOf300.Distinct().Count();

    Console.WriteLine($"There are {uniqueFactors} unique prime factors of 300.");
}
```

```java
//java
public static void Linq73(){
        var primeFactorsOf300=new int[]{2,2,3,5,5};

        var uniqueFactors=Arrays.stream(primeFactorsOf300).distinct().count();

        print("There are %d unique factors of 300.",uniqueFactors);
        }
```

#### Output

    There are 3 unique factors of 300.

### linq74: Count - Conditional

> This sample gets the number of odd ints in the array.

```csharp
//c#
static void Linq74()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var oddNumbers = numbers.Count(n => n % 2 == 1);

    Console.WriteLine($"There are {oddNumbers} odd numbers in the list.");
}
```

```java
//java
public static void Linq74(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var oddNumbers=Arrays.stream(numbers)
        .filter(n->n%2==1)
        .count();

        print("There are %d odd numbers in the list.",oddNumbers);
        }
```

#### Output

    There are 5 odd numbers in the list.

### linq76: Count - Nested

> This sample uses returns a list of customers and how many orders each has.

```csharp
//c#
static void Linq76()
{
    var customers = GetCustomerList();

    var orderCounts = customers
        .Select(cust => 
            new
            {
                cust.CustomerID, 
                OrderCount = cust.Orders.Count()
            });

    ObjectDumper.Write(orderCounts);
}
```

```java
//java
public static void Linq75(){
        var customers=Data.getCustomerList();


        var orderCounts=customers.stream()
        .map(c->new Object(){
        String customerId=c.customerId;
        int orderCount=c.orders!=null?c.orders.size():0;
        })
        .collect(Collectors.toList());

        for(var o:orderCounts){
        print("{CustomerId: %s, OrderCount: %d}",o.customerId,o.orderCount);
        }
        }
```

#### Output

    {CustomerId: ALFKI, OrderCount: 6}
    {CustomerId: ANATR, OrderCount: 4}
    {CustomerId: ANTON, OrderCount: 7}
    {CustomerId: AROUT, OrderCount: 13}
    {CustomerId: BERGS, OrderCount: 18}
    {CustomerId: BLAUS, OrderCount: 7}
    {CustomerId: BLONP, OrderCount: 11}
    ...

### linq77: Count - Grouped

> This sample uses returns a list of categories and how many products each has.

```csharp
//c#
static void Linq77()
{
    var products = GetProductList();

    var categoryCounts = products
        .GroupBy(prod => prod.Category)
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                ProductCount = prodGroup.Count()
            });

    ObjectDumper.Write(categoryCounts);
}
```

```java
//java
public static void Linq77(){
        var products=Data.getProductList();

        var categoryCounts=products.stream()
        .collect(Collectors.groupingBy(p->p.category,Collectors.counting()));

        System.out.println((categoryCounts));
        }
```

#### Output

    {Grains/Cereals=7, Confections=13, Produce=5, Meat/Poultry=6, Seafood=12, Beverages=12, Dairy Products=10, Condiments=12}

### linq78: Sum - Simple

> This sample uses adds all the numbers in an array.

```csharp
//c#
static void Linq78()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var numSum = numbers.Sum();

    Console.WriteLine($"The sum of the numbers is {numSum}.");
}
```

```java
//java
public static void Linq78(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var numSum=Arrays.stream(numbers).sum();

        print("The sum of the numbers is %d.",numSum);
        }
```

#### Output

    The sum of the numbers is 45.

### linq79: Sum - Projection

> This sample  gets the total number of characters of all words in the array.

```csharp
//c#
static void Linq79()
{
    var  words = new [] { "cherry", "apple", "blueberry" };

    var totalChars = words.Sum(w => w.Length);

    Console.WriteLine($"There are a total of {totalChars} characters in these words.");
}
```

```java
//java
public static void Linq79(){
        var words=new String[]{"cherry","apple","blueberry"};

        var totalChars=Arrays.stream(words)
        .mapToInt(w->w.length())
        .sum();

        print("There are a total of %d characters in these words.",totalChars);
        }
```

#### Output

    There are a total of 20 characters in these words.

### linq80: Sum - Grouped

> This sample gets the total units in stock for each product category.

```csharp
//c#
static void Linq80()
{
    var products = GetProductList();

    var categories = products
        .GroupBy(prod => prod.Category)
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                TotalUnitsInStock = prodGroup.Sum(p => p.UnitsInStock)
            });

    ObjectDumper.Write(categories);
}
```

```java
//java
public static void Linq80(){
        var products=Data.getProductList();

        var categoryStockCounts=products.stream()
        .collect(Collectors.groupingBy(p->p.category,
        Collectors.summingInt(p->p.unitsInStock)));

        System.out.println((categoryStockCounts));
        }
```

#### Output

    {Grains/Cereals=308, Confections=386, Produce=100, Meat/Poultry=165, Seafood=701, Beverages=559, Dairy Products=393, Condiments=507}

### linq81: Min - Simple

> This sample uses gets the lowest number in an array.

```csharp
//c#
static void Linq81()
{
    var numbers = new []{ 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var minNum = numbers.Min();

    Console.WriteLine($"The minimum number is {minNum}.");
} 
```

```java
//java
public static void Linq81(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var minNum=Arrays.stream(numbers).min().getAsInt();

        print("The minimum number is %d",minNum);
        }
```

#### Output

    The minimum number is 0.

### linq82: Min - Projection

> This sample uses gets the length of the shortest word in an array.>

```csharp
//c#
static void Linq82()
{
    var words = new [] { "cherry", "apple", "blueberry" };

    var shortestWord = words.Min(w => w.Length);

    Console.WriteLine($"The shortest word is {shortestWord} characters long.");
}
```

```java
//java
public static void Linq82(){
        var words=new String[]{"cherry","apple","blueberry"};

        var shortestWord=Arrays.stream(words)
        .mapToInt(w->w.length())
        .min()
        .getAsInt();

        print("The shortest word is %d characters long.",shortestWord);
        }
```

#### Output

    The shortest word is 5 characters long.

### linq83: Min - Grouped

> This sample uses gets the cheapest price among each category's products.

```csharp
//c#
static void Linq83()
{
    var products = GetProductList();

    var categories = products
        .GroupBy(prod => prod.Category)
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                CheapestPrice = prodGroup.Min(p => p.UnitPrice)
            });

    ObjectDumper.Write(categories);
}
```

```java
//java
public static void Linq83(){
        var products=Data.getProductList();

        var groupedByCategoryPrice=products.stream()
        .collect(Collectors.groupingBy(p->p.category,
        Collectors.minBy(Comparator.comparing(p->p.unitPrice))));

        for(var key:groupedByCategoryPrice.keySet()){
        var unitPrice=groupedByCategoryPrice.get(key).get().unitPrice;
        print("{Category: %s, CheapestPrice %.2f}",key,unitPrice);
        }
        }
```

#### Output

    {Category: Grains/Cereals, CheapestPrice 7,00}
    {Category: Confections, CheapestPrice 9,20}
    {Category: Produce, CheapestPrice 10,00}
    {Category: Meat/Poultry, CheapestPrice 7,45}
    {Category: Seafood, CheapestPrice 6,00}
    {Category: Beverages, CheapestPrice 4,50}
    {Category: Dairy Products, CheapestPrice 2,50}
    {Category: Condiments, CheapestPrice 10,00}

### linq84: Min - Elements

> This sample gets the products with the lowest price in each category.

```csharp
//c#
static void Linq84()
{
    var products = GetProductList();

    var categories = products.GroupBy(prod => prod.Category)
        .Select(prodGroup => new {prodGroup, minPrice = prodGroup.Min(p => p.UnitPrice)})
        .Select(x => 
            new
            {
                Category = x.prodGroup.Key,
                CheapestProducts = x.prodGroup.Where(p => p.UnitPrice == x.minPrice)
            });

    ObjectDumper.Write(categories, 1);
}
```

```java
//java
public static void Linq84(){
        print("TODO");
        }
```

#### Output

    {Category: Dairy Products, CheapestProducts: {{productId: 33, productName: Geitost, category: Dairy Products, unitPrice: 2.5, unitsInStock: 112}}}
    {Category: Grains/Cereals, CheapestProducts: {{productId: 52, productName: Filo Mix, category: Grains/Cereals, unitPrice: 7.0, unitsInStock: 38}}}
    {Category: Confections, CheapestProducts: {{productId: 19, productName: Teatime Chocolate Biscuits, category: Confections, unitPrice: 9.2, unitsInStock: 25}}}
    {Category: Seafood, CheapestProducts: {{productId: 13, productName: Konbu, category: Seafood, unitPrice: 6.0, unitsInStock: 24}}}
    {Category: Condiments, CheapestProducts: {{productId: 3, productName: Aniseed Syrup, category: Condiments, unitPrice: 10.0, unitsInStock: 13}}}
    {Category: Meat/Poultry, CheapestProducts: {{productId: 54, productName: Tourti�re, category: Meat/Poultry, unitPrice: 7.45, unitsInStock: 21}}}
    {Category: Produce, CheapestProducts: {{productId: 74, productName: Longlife Tofu, category: Produce, unitPrice: 10.0, unitsInStock: 4}}}
    {Category: Beverages, CheapestProducts: {{productId: 24, productName: Guaran� Fant�stica, category: Beverages, unitPrice: 4.5, unitsInStock: 20}}}

### linq85: Max - Simple

> This sample gets the highest number in an array. Note that the method returns a single value.

```csharp
//c#
static void Linq85()
{
    var numbers = new []{ 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var maxNum = numbers.Max();

    Console.WriteLine($"The maximum number is {maxNum}.");
}
```

```java
//java
public static void Linq85(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var maxNum=Arrays.stream(numbers).max().getAsInt();

        print("The maximum number is %d.",maxNum);
        }
```

#### Output

    The maximum number is 9.

### linq86: Max - Projection

> This sample gets the length of the longest word in an array.

```csharp
//c#
static void Linq86()
{
    var words = new [] { "cherry", "apple", "blueberry" };

    var longestLength = words.Max(w => w.Length);

    Console.WriteLine($"The longest word is {longestLength} characters long.");
}
```

```java
//java
public static void Linq86(){
        var words=new String[]{"cherry","apple","blueberry"};

        var shortestWord=Arrays.stream(words)
        .mapToInt(w->w.length())
        .max()
        .getAsInt();

        print("The longest word is %d characters long.",shortestWord);
        }
```

#### Output

    The longest word is 9 characters long.

### linq87: Max - Grouped

> This sample gets the most expensive price among each category's products.

```csharp
//c#
static void Linq87()
{
    var products = GetProductList();

    var categories = products
        .GroupBy(prod => prod.Category)
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                MostExpensivePrice = prodGroup.Max(p => p.UnitPrice)
            });

    ObjectDumper.Write(categories);
}
```

```java
//java
public static void Linq87(){
        var products=Data.getProductList();

        var groupedByCategoryPrice=products.stream()
        .collect(Collectors.groupingBy(p->p.category,
        Collectors.maxBy(Comparator.comparing(p->p.unitPrice))));

        for(var key:groupedByCategoryPrice.keySet()){
        var unitPrice=groupedByCategoryPrice.get(key).get().unitPrice;
        print("{Category: %s, MostExpensive %.2f}",key,unitPrice);
        }
```

#### Output

    {Category: Dairy Products, MostExpensivePrice: 55.0}
    {Category: Grains/Cereals, MostExpensivePrice: 38.0}
    {Category: Confections, MostExpensivePrice: 81.0}
    {Category: Seafood, MostExpensivePrice: 62.5}
    {Category: Condiments, MostExpensivePrice: 43.9}
    {Category: Meat/Poultry, MostExpensivePrice: 123.79}
    {Category: Produce, MostExpensivePrice: 53.0}
    {Category: Beverages, MostExpensivePrice: 263.5}

### linq88: Max - Elements

> This sample gets the products with the most expensive price in each category.

```csharp
//c#
static void Linq88()
{
    var products = GetProductList();

    var categories = products.GroupBy(prod => prod.Category)
        .Select(prodGroup => new {prodGroup, maxPrice = prodGroup.Max(p => p.UnitPrice)})
        .Select(x => 
            new
            {
                Category = x.prodGroup.Key,
                MostExpensiveProducts = x.prodGroup.Where(p => p.UnitPrice == x.maxPrice)
            });

    ObjectDumper.Write(categories, 1);
}
```

```java
//java
public static void Linq88(){
        print("TODO");
        }
```

#### Output

    {Category: Dairy Products, MostExpensiveProducts: {{productId: 59, productName: Raclette Courdavault, category: Dairy Products, unitPrice: 55.0, unitsInStock: 79}}}
    {Category: Grains/Cereals, MostExpensiveProducts: {{productId: 56, productName: Gnocchi di nonna Alice, category: Grains/Cereals, unitPrice: 38.0, unitsInStock: 21}}}
    {Category: Confections, MostExpensiveProducts: {{productId: 20, productName: Sir Rodney's Marmalade, category: Confections, unitPrice: 81.0, unitsInStock: 40}}}
    {Category: Seafood, MostExpensiveProducts: {{productId: 18, productName: Carnarvon Tigers, category: Seafood, unitPrice: 62.5, unitsInStock: 42}}}
    {Category: Condiments, MostExpensiveProducts: {{productId: 63, productName: Vegie-spread, category: Condiments, unitPrice: 43.9, unitsInStock: 24}}}
    {Category: Meat/Poultry, MostExpensiveProducts: {{productId: 29, productName: Th�ringer Rostbratwurst, category: Meat/Poultry, unitPrice: 123.79, unitsInStock: 0}}}
    {Category: Produce, MostExpensiveProducts: {{productId: 51, productName: Manjimup Dried Apples, category: Produce, unitPrice: 53.0, unitsInStock: 20}}}
    {Category: Beverages, MostExpensiveProducts: {{productId: 38, productName: C�te de Blaye, category: Beverages, unitPrice: 263.5, unitsInStock: 17}}}

### linq89: Average - Simple

> This sample gets the average of all numbers in an array.

```csharp
//c#
static void Linq89()
{
    var numbers = new [] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var averageNum = numbers.Average();

    Console.WriteLine($"The average number is {averageNum}.");
}
```

```java
//java
public static void Linq89(){
        var numbers=new int[]{5,4,1,3,9,8,6,7,2,0};

        var averageNum=Arrays.stream(numbers).average().getAsDouble();

        print("The averages number is %.2f.",averageNum);
        }
```

#### Output

    The averages number is 4,50.

### linq90: Average - Projection

> This sample gets the average length of the words in the array.

```csharp
//c#
static void Linq90()
{
    var words = new [] { "cherry", "apple", "blueberry" };

    var averageLength = words.Average(w => w.Length);

    Console.WriteLine($"The average word length is {averageLength} characters.");
}
```

```java
//java
public static void Linq90(){
        var words=new String[]{"cherry","apple","blueberry"};

        var averageWordLength=Arrays.stream(words)
        .mapToInt(w->w.length())
        .average()
        .getAsDouble();

        print("The shortest word is %f characters long.",averageWordLength);
        }
```

#### Output

    The average word length is 6.666666666666667 characters.

### linq91: Average - Grouped

> This sample gets the average price of each category's products.

```csharp
//c#
static void Linq91()
{
    var  products = GetProductList();

    var categories = products
        .GroupBy(prod => prod.Category)
        .Select(prodGroup => 
            new
            {
                Category = prodGroup.Key, 
                AveragePrice = prodGroup.Average(p => p.UnitPrice)
            });

    ObjectDumper.Write(categories);
}
```

```java
//java
public static void Linq91(){
        print("TODO");
        }
```

#### Output

    {Category: Dairy Products, AveragePrice: 28.73}
    {Category: Grains/Cereals, AveragePrice: 20.25}
    {Category: Confections, AveragePrice: 25.16}
    {Category: Seafood, AveragePrice: 20.6825}
    {Category: Condiments, AveragePrice: 23.0625}
    {Category: Meat/Poultry, AveragePrice: 54.00666666666667}
    {Category: Produce, AveragePrice: 32.37}
    {Category: Beverages, AveragePrice: 37.979166666666664}

### linq92: Aggregate - Simple

> This sample uses creates a running product on the array that calculates the total product of all elements.

```csharp
//c#
static void Linq92()
{
    var doubles = new [] { 1.7, 2.3, 1.9, 4.1, 2.9 };

    var product = doubles.Aggregate((runningProduct, nextFactor) => runningProduct * nextFactor);

    Console.WriteLine($"Total product of all numbers: {product}");
}
```

```java
//java
public static void Linq92(){
        var doubles=new double[]{1.7,2.3,1.9,4.1,2.9};

        var product=Arrays.stream(doubles)
        .reduce((runningTotal,nextFactor)->runningTotal*nextFactor)
        .getAsDouble();

        print("Total product of all numbers: %f",product);
        }
```

#### Output

    Total product of all numbers: 88.33080999999999

### linq93: Aggregate - Seed

> This sample uses to creates a running account balance that subtracts each withdrawal from the initial balance of 100, as long as the balance never drops below 0.

```csharp
//c#
static void Linq93()
{
    var startBalance = 100.0;

    var attemptedWithdrawals = new []{ 20, 10, 40, 50, 10, 70, 30 };

    var endBalance = attemptedWithdrawals
        .Aggregate(startBalance, 
                    (balance, nextWithdrawal) =>
                    ((nextWithdrawal <= balance) ? (balance - nextWithdrawal) : balance));

    Console.WriteLine($"Ending balance: {endBalance}");
}   
```

```java
//java
public static void Linq93(){
        int startBalance=100;

        var attemptedWithdrawals=new int[]{20,10,40,50,10,70,30};

        var endBalance=Arrays.stream(attemptedWithdrawals)
        .reduce(startBalance,
        (balance,nextWithdrawal)->
        (nextWithdrawal<=balance)?(balance-nextWithdrawal):balance);

        print("Ending balance: %d",endBalance);
        }
```

#### Output

    Ending balance: 20.0

LINQ - Miscellaneous Operators
------------------------------

### linq94: Concat - 1

> This sample creates a contatenation of each array's values, one after the other.

```csharp
//c#
static void Linq94()
{
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var allNumbers = numbersA.Concat(numbersB);

    Console.WriteLine("All numbers from both arrays:");
    allNumbers.ForEach(Console.WriteLine);
}
```

```java
//java
public static void Linq94(){
        int[]numbersA={0,2,4,5,6,8,9};
        int[]numbersB={1,3,5,7,8};

        var allNumbers=Stream
        .concat(
        Arrays.stream(numbersA).boxed(),
        Arrays.stream(numbersB).boxed());

        print("All numbers from both arrays:");
        allNumbers.forEach(System.out::println);
        }
```

#### Output

    All numbers from both arrays:
    0
    2
    4
    5
    6
    8
    9
    1
    3
    5
    7
    8

### linq95: Concat - 2

> This sample creates a contatenation that contains the names of all customers and products, including any duplicates.

```csharp
//c#
static void Linq95()
{
    var customers = GetCustomerList();
    var products = GetProductList();

    var customerNames = customers.Select(cust => cust.CompanyName);
    var productNames = products.Select(prod => prod.ProductName);

    var allNames = customerNames.Concat(productNames);

    Console.WriteLine("Customer and product names:");
    allNames.ForEach(Console.WriteLine);
}
```

```java
//java
public static void Linq95(){
        var products=Data.getProductList();
        var customers=Data.getCustomerList();

        var customerNames=customers.stream().map(c->c.companyName);
        var productNames=products.stream().map(p->p.productName);


        var allNames=Stream.concat(customerNames,productNames);

        print("Customer and product names:");
        allNames.forEach(System.out::println);
        }
```

#### Output

    Customer and product names:
    Alfreds Futterkiste
    Ana Trujillo Emparedados y helados
    Antonio Moreno Taquer�a
    Around the Horn
    Berglunds snabbk�p
    Blauer See Delikatessen
    ...

### linq96: EqualAll - 1

> This sample checks if two sequences match on all elements in the same order

```csharp
//c#
static void Linq96()
{
    var wordsA = new[] { "cherry", "apple", "blueberry" };
    var wordsB = new[] { "cherry", "apple", "blueberry" };

    var match = wordsA.SequenceEqual(wordsB);

    Console.WriteLine($"The sequences match: {match}");
}
```

```java
//java
public static void Linq96(){
        var wordsA=new String[]{"cherry","apple","blueberry"};
        var wordsB=new String[]{"cherry","apple","blueberry"};

        var equal=Arrays.equals(wordsA,wordsB);

        print("The sequences match: %s",equal);
        }
```

#### Output

    The sequences match: True

### linq97: EqualAll - 2

> This sample checks if two sequences match on all elements in the same order.

```csharp
//c#
static void Linq97()
{
    var wordsA = new[] { "cherry", "apple", "blueberry" };
    var wordsB = new[] { "apple", "blueberry", "cherry" };

    var match = wordsA.SequenceEqual(wordsB);

    Console.WriteLine($"The sequences match: {match}");
}   
```

```java
//java
public static void Linq97(){
        var wordsA=new String[]{"cherry","apple","blueberry"};
        var wordsB=new String[]{"apple","blueberry","cherry"};

        var equal=Arrays.equals(wordsA,wordsB);

        print("The sequences match: %s",equal);
        }
```

#### Output

    The sequences match: False

LINQ - Query Execution
----------------------

### linq99: Deferred Execution

> The following sample shows how query execution is deferred until the query is enumerated at a foreach statement.

```csharp
//c#
static void Linq99()
{
    // Queries are not executed until you enumerate over them.
    var numbers = new[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    int i = 0;
    var simpleQuery = numbers
        .Select(x => i++);

    // The local variable 'i' is not incremented until the query is executed in the foreach loop.
    Console.WriteLine($"The current value of i is {i}"); //i is still zero

    simpleQuery.ForEach(item => Console.WriteLine($"v = {item}, i = {i}")); // now i is incremented          
}
```

```java
//java
public static void Linq99(){
        print("TODO");
        }
```

#### Output

    v = 1, i = 1
    v = 2, i = 2
    v = 3, i = 3
    v = 4, i = 4
    v = 5, i = 5
    v = 6, i = 6
    v = 7, i = 7
    v = 8, i = 8
    v = 9, i = 9
    v = 10, i = 10

### linq100: Immediate Execution

> The following sample shows how queries can be executed immediately, and their results stored in memory, with methods such as ToList/list.

```csharp
//c#
static void Linq100()
{
    // Methods like ToList(), Max(), and Count() cause the query to be executed immediately.            
    var numbers = new[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var i = 0;
    var immediateQuery = numbers
        .Select(x =>  ++i)
        .ToList();

    Console.WriteLine("The current value of i is {0}", i); //i has been incremented

    immediateQuery.ForEach(item => Console.WriteLine($"v = {item}, i = {i}"));
}
```

```java
//java
public static void Linq100(){
        print("TODO");
        }
```

#### Output

    v = 1, i = 10
    v = 2, i = 10
    v = 3, i = 10
    v = 4, i = 10
    v = 5, i = 10
    v = 6, i = 10
    v = 7, i = 10
    v = 8, i = 10
    v = 9, i = 10
    v = 10, i = 10

### linq101: Query Reuse

> The following sample shows how, because of deferred execution, queries can be used again after data changes and will then operate on the new data.

```csharp
//c#
static void Linq101()
{
    // Deferred execution lets us define a query once and then reuse it later in various ways.
    var numbers = new[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    var lowNumbers = numbers
        .Where(num => num <= 3);

    Console.WriteLine("First run numbers <= 3:");
    lowNumbers.ForEach(Console.WriteLine);

    // Modify the source data.
    for (var i = 0; i < 10; i++)
    {
        numbers[i] = -numbers[i];
    }

    // During this second run, the same query object,
    // lowNumbers, will be iterating over the new state
    // of numbers[], producing different results:
    Console.WriteLine("Second run numbers <= 3:");
    lowNumbers.ForEach(Console.WriteLine);
}   
```

```java
//java
public static void Linq101(){
        print("TODO");
        }
```

#### Output

    First run numbers <= 3:
    1
    3
    2
    0
    Second run numbers <= 3:
    -5
    -4
    -1
    -3
    -9
    -8
    -6
    -7
    -2
    0
