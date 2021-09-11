package com.example.javastream.flatmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TestFlatMap {

    private static final Logger log = LoggerFactory.getLogger(TestFlatMap.class);

    @Test
    void runFlatMapTest01() {

        List<Integer> primeNumbers = Arrays.asList(3, 5, 7, 11, 13);
        List<Integer> oddNumbers = Arrays.asList(1, 3, 5, 7);
        List<Integer> evenNumbers = Arrays.asList(2, 4, 6, 8, 10);

        List<List<Integer>> finalList = Arrays.asList(primeNumbers, oddNumbers, evenNumbers);
        log.info("@finalList=====>{}", finalList);

        //List<Integer> intList = finalList.stream().flatMap(list -> list.stream())
        List<Integer> intList = finalList.stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("@intList=====>{}", intList);

        final List<Integer> resultList = Arrays.asList(3, 5, 7, 11, 13, 1, 3, 5, 7, 2, 4, 6, 8, 10);
        assertThat(intList).isEqualTo(resultList);

        intList.stream()
                .filter(el -> el != 3)
                .forEach(el -> log.info("el->{}", el));

    }

    @Test
    void runFlatMapTest02() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1001, "Order #1", Arrays.asList("bat", "ball", "helmat")));
        orderList.add(new Order(1002, "Order #2", Arrays.asList("pad", "bat", "helmat")));
        orderList.add(new Order(1003, "Order #3", Arrays.asList("stumps", "bat", "gloves")));

        List<String> itemList = orderList.stream()
                .map(Order::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("@itemList=====>{}", itemList);

        List<String> itemList2 = orderList.stream()
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.toList());
        log.info("@itemList2=====>{}", itemList2);

        List<String> resultItems = Arrays.asList("bat", "ball", "helmat", "pad", "bat", "helmat", "stumps", "bat", "gloves");
        assertThat(itemList).isEqualTo(resultItems);
        assertThat(itemList2).isEqualTo(resultItems);

        orderList.stream()
                .map(Order::getItems)
                .flatMap(Collection::stream)
                .distinct()
                .forEach(o -> log.info("uniqueItem->{}", o));

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private int id;
        String name;
        List<String> items;
    }


}
