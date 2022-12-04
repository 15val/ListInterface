import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import stream.MyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

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

        defaultStream.forEach(x -> System.out.println(x));


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
}
