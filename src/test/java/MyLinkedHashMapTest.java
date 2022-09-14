import hashmap.MyHashMap;
import linkedhashmap.MyLinkedHashMap;
import linkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;



public class MyLinkedHashMapTest {
    private MyLinkedHashMap<Integer, String> map = new MyLinkedHashMap<>();
    private MyLinkedHashMap<Integer, String> map1 = new MyLinkedHashMap<>();
    private MyLinkedHashMap<Integer, String> nullMap = new MyLinkedHashMap<>();


    @BeforeEach
    public void setUp() {


        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        map1.put(1, "one");


    }

    @Test
    void shouldReturn3WhenSizeOfListEquals3(){
        assertThat(3, equalTo(map.size()));
    }

    @Test
    void shouldReturn7WhenAddedElement7(){
        map.put(7, "seven");
        assertThat("seven", equalTo(map.getByKey(7)));
    }
    @Test
    void shouldReturn1WhenAddedElement1(){
        map.put(1, "one");
        assertThat("one", equalTo(map.getByKey(1)));
    }
    @Test
    void shouldReturn3WhenAddedElement3(){
        map.put(3, "three");
        assertThat("three", equalTo(map.getByKey(3)));
    }
    @Test
    void shouldReturn2WhenAddedElement2(){
        map.put(2, "two");
        assertThat("two", equalTo(map.getByKey(2)));
    }


    @Test
    void shouldReturn6WhenAddedElement6(){
        map1.put(6, "six");
        assertThat("six", equalTo(map1.getByKey(6)));
    }

    @Test
    void shouldReturnEightWhenUpdatedElement1(){
        map1.put(1, "eight");
        assertThat("eight", equalTo(map1.getByKey(1)));
    }

    @Test
    void shouldReturnEightWhenUpdatedElement6(){
        map1.put(6, "six");
        map1.put(6, "eight");
        assertThat("eight", equalTo(map1.getByKey(6)));
    }

    @Test
    void shouldReturnSize3WhenPuttingNullKey(){
        map.put(null, "seven");
        assertThat(3, equalTo(map.size()));
    }

    @Test
    void shouldReturn3WhenGettingElementByIndex2(){
        assertThat("three", equalTo(map.getByKey(3)));
    }

    @Test
    void shouldReturnNullWhenGettingElementByIndex6(){
        assertThat(null, equalTo(map.getByKey(6)));
    }

    @Test
    void shouldReturnNullWhenGettingElementByIndexNull(){
        assertThat(null, equalTo(map.getByKey(null)));
    }

    @Test
    void shouldReturnTrueWhenRemovingElement1(){
        assertThat(map.removeByKey(1), equalTo(true));

    }
    @Test
    void shouldReturnTrueWhenRemovingElement2(){
        assertThat(map.removeByKey(2), equalTo(true));

    }
    @Test
    void shouldReturnTrueWhenRemovingElement3(){
        assertThat(map.removeByKey(3), equalTo(true));

    }


    @Test
    void shouldReturnFalseWhenRemovingElement6(){
        assertThat(map.removeByKey(6), equalTo(false));

    }

    @Test
    void shouldReturnTrueWhenCheckingThatListContainsKey2() {
        assertThat(map.containsKey(2), equalTo(true));
    }

    @Test
    void shouldReturnFalseWhenCheckingThatListContainsKey6(){
        assertThat(map.containsKey(6), equalTo(false));
    }

    @Test
    void shouldReturnTrueWhenCheckingThatListContainsValueTwo() {
        assertThat(map.containsValue("two"), equalTo(true));
    }

    @Test
    void shouldReturnFalseWhenCheckingThatListContainsValueSix(){
        assertThat(map.containsValue("six"), equalTo(false));
    }

    @Test
    void shouldReturnArrayOfKeys123(){
        assertThat(map.keySet(), equalTo(new int[]{1, 2, 3}));
    }
    @Test
    void shouldReturnArrayOfValuesOneTwoThree(){
        assertThat(map.values(), equalTo(new String[]{"one", "two", "three"}));
    }


    @Test
    void shouldReturnTrueWhenExistsNextElement(){
        Iterator<MyLinkedHashMap.Entry<Integer, String>> iterator = map.iterator();
        assertThat(iterator.hasNext(), equalTo(true));
    }

    @Test
    void shouldReturnFalseWhenDoesNotExistNextElement(){
        Iterator<MyLinkedHashMap.Entry<Integer, String>> iterator = map.iterator();
        iterator.next();
        iterator.next();
        assertThat(iterator.hasNext(), equalTo(false));
    }

    @Test
    void shouldReturnFalseWhenCurrentElementIsNull(){
        Iterator<MyLinkedHashMap.Entry<Integer, String>> iterator = nullMap.iterator();

        assertThat(iterator.hasNext(), equalTo(false));
    }

    @Test
    void shouldReturn2WhenGoingToNextNextElement(){
        Iterator<MyLinkedHashMap.Entry<Integer, String>> iterator = map.iterator();
        MyLinkedHashMap.Entry<Integer, String> actual = iterator.next();

        assertThat(actual.getKey(), equalTo(2));
        assertThat(actual.getValue(), equalTo("two"));
        assertThat(actual.getNext(), equalTo(null));
    }

    @Test
    void shouldReturn20WhenCapacityIncreased(){
        assertThat(map.ensureCapacity(), equalTo(8));
    }

    @Test
    void shouldReturnExceptionMessageWhenAttemptToIterateToElementOutOfBounds(){
        Iterator<MyLinkedHashMap.Entry<Integer, String>> iterator = map.iterator();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
            iterator.next();
            iterator.next();
        });

        assertThat(exception.getMessage(), containsString("no such element"));
    }


}