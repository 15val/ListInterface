package stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MyStream<E> implements Stream<E>, Iterable<E> {
    List<E> defaultList;
    public MyStream(List<E> receivedList) {
        defaultList = receivedList;
    }


    @Override
    public Stream<E> filter(Predicate<? super E> predicate) {
        Stream.Builder<E> builder = Stream.builder();
         ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(predicate.test(current)){
                builder.add(current);
            }
        }
        return builder.build();

    }

    @Override
    public <R> Stream<R> map(Function<? super E, ? extends R> mapper) {
        Stream.Builder<R> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            builder.add(mapper.apply(current));
        }
        return builder.build();
    }


    @Override
    public Stream<E> distinct() {

        Stream.Builder<E> builder = Stream.builder();
        List<E> notDistinctList = new ArrayList<>();

        ListIterator<E> listIterator = defaultList.listIterator();

        E current;
        E currentForEachIteration;
        int count;
        int count2;
        while (listIterator.hasNext()){
            current = listIterator.next();
            count = 0;
            count2 = 0;
            ListIterator<E> listIteratorForSearchingDistinct = defaultList.listIterator();
            while ((listIteratorForSearchingDistinct.hasNext())) {
                currentForEachIteration = listIteratorForSearchingDistinct.next();
                if (current == currentForEachIteration) {
                    count++;
                }
            }

            if(count == 1) {
                builder.add(current);
            }
            else{
                notDistinctList.add(current);
                ListIterator<E> notDistinctListIterator = notDistinctList.listIterator();
                while (notDistinctListIterator.hasNext()) {
                    if (notDistinctListIterator.next() == current) {
                        count2++;
                    }
                }
                if(count2 == 1) {
                    builder.add(current);
                }
            }
        }
        return builder.build();
    }


    @Override
    public Stream<E> limit(long maxSize) {
        if (maxSize < 0){
            throw new UnsupportedOperationException("maxSize must be >= 0");
        }
        ListIterator<E> listIterator = defaultList.listIterator();
        Stream.Builder<E> builder = Stream.builder();
        E current;
        int count = 0;
        while (listIterator.hasNext()){
            current = listIterator.next();
            count++;
            if(count > maxSize){
                break;
            }
            builder.add(current);

        }
        return builder.build();
    }

    @Override
    public Stream<E> skip(long n) {
        if (n < 0){
            throw new UnsupportedOperationException("n must be >= 0");
        }
        ListIterator<E> listIterator = defaultList.listIterator();
        Stream.Builder<E> builder = Stream.builder();
        E current;
        int count = 0;
        while (listIterator.hasNext()){
            current = listIterator.next();
            count++;
            if(count <= n){
                continue;
            }
            builder.add(current);

        }
        return builder.build();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            action.accept(current);
        }
    }



    @Override
    public Object[] toArray() {

        Object[] array = new Object[defaultList.size()];
        for(int i = 0; i < defaultList.size(); i++){
            array[i] = defaultList.get(i);
        }
        return array;
    }





    @Override
    public Optional<E> min(Comparator<? super E> comparator) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        E min = defaultList.get(0);
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(comparator.compare(current, min) < 0) {
                min = current;
                }
            }
        return Optional.of(min);
    }



    @Override
    public Optional<E> max(Comparator<? super E> comparator) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        E max = defaultList.get(0);
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(comparator.compare(current, max) > 0) {
                max = current;
            }
        }
        return Optional.of(max);
    }


    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(predicate.test(current)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(!predicate.test(current)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(Predicate<? super E> predicate) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            if(predicate.test(current)){
                return false;
            }
        }
        return true;
    }


    @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private int currentIndex = 0;

                @Override
                public boolean hasNext() {
                    return currentIndex < defaultList.size();
                }

                @Override
                public E next() {
                    if (currentIndex < defaultList.size()) {
                        return defaultList.get(currentIndex++);
                    } else {
                        throw new NoSuchElementException("no such element");
                    }
                }
        };
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    @Override
    public E reduce(E identity, BinaryOperator<E> accumulator) {
        return null;
    }

    @Override
    public Optional<E> reduce(BinaryOperator<E> accumulator) {
        return Optional.empty();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super E, U> accumulator, BinaryOperator<U> combiner) {
        return null;
    }
    @Override
    public void forEachOrdered(Consumer<? super E> action) {

    }
    @Override
    public <R, A> R collect(Collector<? super E, A, R> collector) {
        return null;
    }
    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super E> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }
    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<E> findFirst() {
        return Optional.empty();
    }
    @Override
    public Stream<E> sorted() {

        return null;
    }

    @Override
    public Stream<E> sorted(Comparator<? super E> comparator) {
        return null;
    }

    @Override
    public Stream<E> peek(Consumer<? super E> action) {
        return null;
    }

    @Override
    public Optional<E> findAny() {
        return Optional.empty();
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public Stream<E> sequential() {
        return null;
    }

    @Override
    public Stream<E> parallel() {
        return null;
    }

    @Override
    public Stream<E> unordered() {
        return null;
    }

    @Override
    public Stream<E> onClose(Runnable closeHandler) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super E> mapper) {
        return null;
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super E> mapper) {
        return null;
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super E> mapper) {
        return null;
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super E, ? extends Stream<? extends R>> mapper) {
        return null;
    }

    @Override
    public IntStream flatMapToInt(Function<? super E, ? extends IntStream> mapper) {
        return null;
    }

    @Override
    public LongStream flatMapToLong(Function<? super E, ? extends LongStream> mapper) {
        return null;
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super E, ? extends DoubleStream> mapper) {
        return null;
    }

}
