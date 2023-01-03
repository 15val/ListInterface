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
    public long count() {
        ListIterator<E> listIterator = defaultList.listIterator();
        long count = 0;
        while (listIterator.hasNext()){
            listIterator.next();
            count++;
        }
        return count;
    }

    @Override
    public Optional<E> findFirst() {
        ListIterator<E> listIterator = defaultList.listIterator();
        if(listIterator.hasNext()) {
            return Optional.of(listIterator.next());
        }
        else return Optional.empty();
    }

    @Override
    public Optional<E> findAny() {
        ListIterator<E> listIterator = defaultList.listIterator();
        if(listIterator.hasNext()) {
            return Optional.of(listIterator.next());
        }
        else return Optional.empty();
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super E> mapper) {
        IntStream.Builder builder = IntStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            builder.add(mapper.applyAsInt(current));
        }
        return builder.build();
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super E> mapper) {
        LongStream.Builder builder = LongStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            builder.add(mapper.applyAsLong(current));
        }
        return builder.build();
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super E> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            builder.add(mapper.applyAsDouble(current));
        }
        return builder.build();
    }

    @Override
    public Stream<E> onClose(Runnable closeHandler) {
        closeHandler.run();
        Stream.Builder<E> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        while (listIterator.hasNext()){
            builder.add(listIterator.next());
        }
        return builder.build();
    }

    @Override
    public void close() {
        defaultList.clear();
    }

    @Override
    public Stream<E> sorted() {
        Collections.sort(defaultList, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        Stream.Builder<E> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        while (listIterator.hasNext()){
            builder.add(listIterator.next());
        }
        return builder.build();

    }

    @Override
    public Stream<E> sorted(Comparator<? super E> comparator) {
        Collections.sort(defaultList, comparator);
        Stream.Builder<E> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        while (listIterator.hasNext()){
            builder.add(listIterator.next());
        }
        return builder.build();

    }

    @Override
    public void forEachOrdered(Consumer<? super E> action) {
        Collections.sort(defaultList, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()) {
            current = listIterator.next();
            action.accept(current);
        }
    }

    @Override
    public E reduce(E identity, BinaryOperator<E> accumulator) {
        ListIterator<E> listIterator = defaultList.listIterator();
        E result = identity;
        while (listIterator.hasNext()) {
            result = accumulator.apply(result, listIterator.next());
        }
        return result;
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super E, U> accumulator, BinaryOperator<U> combiner) {
        ListIterator<E> listIterator = defaultList.listIterator();
        U result = identity;
        while (listIterator.hasNext()) {
            result = combiner.apply(result, accumulator.apply(result, listIterator.next()));
        }
        return result;
    }

    @Override
    public Optional<E> reduce(BinaryOperator<E> accumulator) {
        ListIterator<E> listIterator = defaultList.listIterator();
        Optional<E> result = Optional.empty();
        if(listIterator.hasNext()) {
            result = Optional.of(listIterator.next());
        }
        while (listIterator.hasNext()) {
            result = Optional.of(accumulator.apply(result.get(), listIterator.next()));
        }
        return result;
    }

    @Override
    public Stream<E> peek(Consumer<? super E> action) {
        Stream.Builder<E> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            action.accept(current);
            builder.add(current);
        }
        return builder.build();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        A[] array = generator.apply(defaultList.size());
        ListIterator<E> listIterator = defaultList.listIterator();
        int count = 0;
        while (listIterator.hasNext()){
           array[count] = (A) listIterator.next();
           count++;
        }
        return array;
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super E, ? extends Stream<? extends R>> mapper) {
        Stream.Builder<R> builder = Stream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        Optional<?> temp;
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            temp =  mapper.apply(current).findFirst();
            builder.add((R) temp.get());
        }
        return builder.build();

    }

    @Override
    public IntStream flatMapToInt(Function<? super E, ? extends IntStream> mapper) {
        IntStream.Builder builder = IntStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        OptionalInt temp;
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            temp = mapper.apply(current).findFirst();
            builder.add(temp.getAsInt());
        }
        return builder.build();
    }

    @Override
    public LongStream flatMapToLong(Function<? super E, ? extends LongStream> mapper) {
        LongStream.Builder builder = LongStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        OptionalLong temp;
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            temp = mapper.apply(current).findFirst();
            builder.add(temp.getAsLong());
        }
        return builder.build();
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super E, ? extends DoubleStream> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();
        ListIterator<E> listIterator = defaultList.listIterator();
        OptionalDouble temp;
        E current;
        while (listIterator.hasNext()){
            current = listIterator.next();
            temp = mapper.apply(current).findFirst();
            builder.add(temp.getAsDouble());
        }
        return builder.build();
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



}
