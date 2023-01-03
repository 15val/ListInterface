import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import stream.MyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class Phone{

    private String name;
    private int price;

    public Phone(String name, int price){
        this.name=name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

public class MyStreamTest {

    public List<Integer> list = new ArrayList<>();
    MyStream<Integer> defaultStream;

    @BeforeEach
    public void setUp() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        defaultStream = new MyStream<>(list);
    }

    boolean assertStreamEquals(Stream<?> stream1, Stream<?> stream2) {
        Iterator<?> iter1 = stream1.iterator(), iter2 = stream2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            if(!iter1.next().equals(iter2.next())){
                return false;
            }
        }
        if (!(!iter1.hasNext() && !iter2.hasNext())){
            return false;
        }
        return true;
    }

    boolean assertIntStreamEquals(IntStream stream1, IntStream stream2) {
        Iterator<?> iter1 = stream1.iterator(), iter2 = stream2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            if(!iter1.next().equals(iter2.next())){
                return false;
            }
        }
        if (!(!iter1.hasNext() && !iter2.hasNext())){
            return false;
        }
        return true;
    }

    boolean assertLongStreamEquals(LongStream stream1, LongStream stream2) {
        Iterator<?> iter1 = stream1.iterator(), iter2 = stream2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            if(!iter1.next().equals(iter2.next())){
                return false;
            }
        }
        if (!(!iter1.hasNext() && !iter2.hasNext())){
            return false;
        }
        return true;
    }

    boolean assertDoubleStreamEquals(DoubleStream stream1, DoubleStream stream2) {
        Iterator<?> iter1 = stream1.iterator(), iter2 = stream2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            if(!iter1.next().equals(iter2.next())){
                return false;
            }
        }
        if (!(!iter1.hasNext() && !iter2.hasNext())){
            return false;
        }
        return true;
    }

    @Test
    void shouldReturnStreamOf3456(){
       assertThat(assertStreamEquals(defaultStream.filter(x-> x - 2 > 0), Stream.of(3, 4, 5, 6)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf456789(){
        assertThat(assertStreamEquals(defaultStream.map(x-> x + 3), Stream.of(4, 5, 6, 7 , 8, 9)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf123456(){
        list.add(1);
        list.add(5);
        defaultStream = new MyStream<>(list);
        assertThat(assertStreamEquals(defaultStream.distinct(), Stream.of(1, 2, 3, 4, 5, 6)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf1234(){
        assertThat(assertStreamEquals(defaultStream.limit(4), Stream.of(1, 2, 3, 4)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf456(){
        assertThat(assertStreamEquals(defaultStream.skip(3), Stream.of(4, 5, 6)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf123456elements(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        defaultStream.forEach(System.out::println);


        assertThat("1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n", equalTo(outContent.toString()));
    }

    @Test
    void shouldReturnArrayOf123456(){
        Object[] arr = new Integer[]{1, 2, 3, 4, 5, 6};
        Object[] arr2 = defaultStream.toArray();
        assertArrayEquals(arr, arr2);
    }

    @Test
    void shouldReturn1(){
        assertThat(defaultStream.min(Integer::compare), equalTo(Optional.of(1)));
    }

    @Test
    void shouldReturn6(){
        assertThat(defaultStream.max(Integer::compare), equalTo(Optional.of(6)));
    }

    @Test
    void shouldReturnTrueWhenAnyOfElementsMatch(){
        assertThat(defaultStream.anyMatch(x -> x > 5), equalTo(true));
    }

    @Test
    void shouldReturnTrueWhenNoneElementsMatch(){
        assertThat(defaultStream.noneMatch(x -> x > 10), equalTo(true));
    }

    @Test
    void shouldReturnTrueWhenAllOfElementsMatch(){
        assertThat(defaultStream.allMatch(x -> x % 1 == 0), equalTo(true));
    }

    @Test
    void shouldReturnTrueWhenExistsNextElement(){
        Iterator<Integer> iterator = defaultStream.iterator();
        assertThat(iterator.hasNext(), equalTo(true));
    }

    @Test
    void shouldReturn2WhenGoingToNextNextElement(){
        Iterator<Integer> iterator = defaultStream.iterator();
        iterator.next();
        assertThat(iterator.next(), equalTo(2));
    }

    @Test
    void shouldReturnTrueWhen6ElementsInStream(){
        assertThat(defaultStream.count(), equalTo(6L));
    }

    @Test
    void shouldReturnAnyOfElements(){
        assertThat(defaultStream.findAny(), anyOf(is(Optional.of(1)), is(Optional.of(2)), is(Optional.of(3)), is(Optional.of(4)), is(Optional.of(5)), is(Optional.of(6))));
    }

    @Test
    void shouldReturnFirstElement(){
        assertThat(defaultStream.findFirst(), equalTo(Optional.of(1)));
    }

    @Test
    void shouldReturnIntStreamOf5678910(){
        assertThat(assertIntStreamEquals(defaultStream.mapToInt(x-> x + 4), IntStream.of(5, 6, 7 , 8, 9, 10)), equalTo(true));
    }

    @Test
    void shouldReturnLongStreamOf5678910(){
        assertThat(assertLongStreamEquals(defaultStream.mapToLong(x-> x + 4), LongStream.of(5, 6, 7 , 8, 9, 10)), equalTo(true));
    }

    @Test
    void shouldReturnDoubleStreamOf5678910(){
        assertThat(assertDoubleStreamEquals(defaultStream.mapToDouble(x-> x + 4), DoubleStream.of(5, 6, 7 , 8, 9, 10)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf12345678elements(){
        list.add(8);
        list.add(7);
        defaultStream = new MyStream<>(list);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        defaultStream.sorted().forEach(System.out::println);


        assertThat("1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n", equalTo(outContent.toString()));
    }

    @Test
    void shouldReturnStreamOf123456789elements(){
        list.add(8);
        list.add(9);
        list.add(7);
        defaultStream = new MyStream<>(list);

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        defaultStream.sorted(comparator).forEach(System.out::println);


        assertThat("1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n", equalTo(outContent.toString()));
    }

    @Test
    void shouldReturnStreamOf12345678elementsByForEachOrdered(){
        list.add(8);
        list.add(7);
        defaultStream = new MyStream<>(list);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        defaultStream.forEachOrdered(System.out::println);


        assertThat("1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n", equalTo(outContent.toString()));
    }

    @Test
    void shouldReturnSumAs22(){
        assertThat(defaultStream.reduce(1, (x,y)->x + y), equalTo(22));
    }

    @Test
    void shouldReturnSumAs21(){
        assertThat(defaultStream.reduce((x,y)->x + y), equalTo(Optional.of(21)));
    }

    @Test
    void shouldReturnSumAs10(){
        Stream<Phone> phoneStream = Stream.of(new Phone("iPhone 6 S", 1),
                new Phone("Lumia 950", 2),
                new Phone("Samsung Galaxy S 6", 3),
                new Phone("LG G 4", 4));

        assertThat(phoneStream.reduce(0,
                (x,y)-> {
                    return x + y.getPrice();
                },
                (x, y)->x + y), equalTo(10));
    }


    @Test
    void shouldReturnArray0f123456(){
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6};
        Integer[] arr2 = defaultStream.toArray(Integer[]::new);
        assertArrayEquals(arr, arr2);
    }

    @Test
    void shouldReturnIntStreamOf67891011(){
        assertThat(assertIntStreamEquals(defaultStream.flatMapToInt(x-> IntStream.of(x + 5)), IntStream.of(6, 7 , 8, 9, 10, 11)), equalTo(true));
    }

    @Test
    void shouldReturnLongStreamOf67891011(){
        assertThat(assertLongStreamEquals(defaultStream.flatMapToLong(x-> LongStream.of(x + 5)), LongStream.of(6, 7 , 8, 9, 10, 11)), equalTo(true));
    }

    @Test
    void shouldReturnDoubleStreamOf67891011(){
        assertThat(assertDoubleStreamEquals(defaultStream.flatMapToDouble(x-> DoubleStream.of(x + 5)), DoubleStream.of(6, 7 , 8, 9, 10, 11)), equalTo(true));
    }

    @Test
    void shouldReturnStreamOf5678910(){
        assertThat(assertStreamEquals(defaultStream.flatMap(x-> Stream.of(x + 4)), Stream.of(5, 6, 7 , 8, 9, 10)), equalTo(true));
    }
    /*
    @Test
    void shouldReturnStreamOf456789(){
        assertThat(assertStreamEquals(defaultStream.flatMap(x-> x.longValue()), Stream.of(4, 5, 6, 7 , 8, 9)), equalTo(true));
    }*/
}

