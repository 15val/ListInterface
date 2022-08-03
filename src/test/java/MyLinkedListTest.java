import linkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


class MyLinkedListTest {
    private MyLinkedList<Integer> list = new MyLinkedList<>();



    @BeforeEach
    public void setUp() {


        list.add(1);
        list.add(2);
        list.add(3);

    }

    @Test
    void shouldReturn3WhenSizeOfListEquals3(){
        assertThat(3, equalTo(list.size()));
    }

    @Test
    void shouldReturn7WhenAddedElement7(){
        list.add(7);
        assertThat(7, equalTo(list.getByIndex(3)));
    }

    @Test
    void shouldReturn3WhenGettingElementByIndex2(){
        assertThat(3, equalTo(list.getByIndex(2)));
    }

    @Test
    void shouldReturn1WhenRemovingElementByIndex0(){
        assertThat(2, equalTo(list.removeByIndex(1)));

    }

    @Test
    void shouldReturnTrueWhenCheckingThatListContains2() {
        assertThat(list.contains(2), equalTo(true));
    }

    @Test
    void shouldReturnFalseWhenCheckingThatListContains6(){
        assertThat(list.contains(6), equalTo(false));
    }

    @Test
    void shouldReturn5WhenAddedElement5(){
        list.addAtPosition(5, 1);
        assertThat(5, equalTo(list.getByIndex(1)));
    }

    @Test
    void shouldReturn6WhenAddedElement6(){
        list.addAtPosition(5, 0);
        assertThat(5, equalTo(list.getByIndex(0)));
    }


    @Test
    void shouldReturnExceptionMessageWhenAttemptToAddElementOutOfBounds(){
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.addAtPosition(5, 1000);
        });

        assertThat(exception.getMessage(), containsString("out of bounds"));
    }

    @Test
    void shouldReturnExceptionMessageWhenHeadIsNull(){
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeByIndex(2);
            list.removeByIndex(1);
            list.removeByIndex(0);
            list.addAtPosition(5, 1000);
        });

        assertThat(exception.getMessage(), containsString("out of bounds"));

    }

    @Test
    void shouldReturnExceptionMessageWhenAttemptToRemoveElementOutOfBounds(){
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeByIndex(1000);
        });

        assertThat(exception.getMessage(), containsString("Index: 1000, Size 3"));
    }

    @Test
    void shouldReturnTrueWhenExistsNextElement(){
        Iterator<Integer> iterator = list.iterator();
        assertThat(iterator.hasNext(), equalTo(true));
    }
    @Test
    void shouldReturnFalseWhenDoesNotExistNextElement(){
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.next();
        assertThat(iterator.hasNext(), equalTo(false));
    }

    @Test
    void shouldReturn2WhenGoingToNextNextElement(){
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        assertThat(iterator.next(), equalTo(3));
    }

    @Test
    void shouldReturnExceptionMessageWhenAttemptToIterateToElementOutOfBounds(){
        Iterator<Integer> iterator = list.iterator();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.next();
        });

        assertThat(exception.getMessage(), containsString("no such element"));
    }


}
