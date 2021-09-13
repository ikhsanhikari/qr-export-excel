package com.example.demo;

import com.example.demo.util.PrettyPrintingMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
        );

        //group by price
        Map<BigDecimal, List<Item>> groupByPriceMap
                = items.stream().collect(Collectors.groupingBy(Item::getPrice));

        System.out.println(new PrettyPrintingMap<BigDecimal, List<Item>>(groupByPriceMap));
 
        SpringApplication.run(DemoApplication.class, args);
    }
    
    public static class Item{
        private String name;
        private Integer total;
        private BigDecimal price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Item(String name, Integer total, BigDecimal price) {
            this.name = name;
            this.total = total;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Item{" + "name=" + name + ", total=" + total + ", price=" + price + '}';
        }
        

        public Item() {
        }
        
        
                
    }

    static final String JAVA_SOURCE_CODE = ""
            + "package on.the.fly;                                            "
            + "public class UserProxy  {                     "
            + " private String test;                                                       "
            + "public UserProxy(){"
            + "System.out.print(\"How to make\");"
            + "}"
            + "public static void main(String[] args){System.out.print(\"How to make\");} "
            + "}                                                              ";

}
