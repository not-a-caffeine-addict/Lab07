package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private final T[] array;
    private Predicate<T> pred;

    public IterableWithPolicyImpl(T[] values) {

        Predicate<T> filter = new Predicate<T>() {
            @Override 
            public boolean test(T element){
                return true;
            }
        };
        
        this(values, filter);
    }

    public IterableWithPolicyImpl(T[] values, Predicate<T> filter) {
        array = values;
        pred = filter;
    }

    @Override 
    public void setIterationPolicy(Predicate<T> filter){
        this.pred = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    class CustomIterator implements Iterator<T> {

        private int index = 0;

        private int findNextIndex() {
            int localIndex = index;
            while (localIndex < array.length) {
                if (pred.test(array[localIndex])) {
                    return localIndex;
                }
                localIndex++;
            }
            return -1;
        }

        @Override
        public boolean hasNext() {
            return findNextIndex() != -1;
        }
        
        @Override
        public T next() {
            
            if (hasNext()) {
                int localInd = findNextIndex();
                T value = array[localInd];
                index = localInd + 1;
                return value;
            }

            throw new NoSuchElementException("Collection of size " + array.length + " has no element " + index);
        }
    }
}