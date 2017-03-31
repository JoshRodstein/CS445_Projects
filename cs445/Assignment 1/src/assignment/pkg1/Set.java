package cs445.a1;

import java.util.Arrays;

/**
 * cs445 Assignment 1
 *
 * @author Joshua Rodstein
 */
public class Set<T> implements SetInterface<T> {

    private T[] contents;  // array to store references of objects of generic type T
    private int size;  // interp as index, loks at first available array slot
    private static final int DEFAULT_CAPACITY = 10;
    

    public Set() {
        this(DEFAULT_CAPACITY);
        size = 0;
    }

    public Set(int capacity) {
        @SuppressWarnings("unchecked")
        T[] tempSet = (T[])new Object[capacity];
        contents = tempSet;
        size = 0;
    }

    /**
     * Determines the current number of entries in this set.
     *
     * @return The integer number of entries currently in this set
     */
    public int getCurrentSize() {
        if (size > 0) {
            return (size);
        } else {
            return 0;
        }
    }

    /**
     * Determines whether this set is empty.
     *
     * @return true if this set is empty; false if not
     */
    public boolean isEmpty() {
        if (getCurrentSize() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * <p>
     * If newEntry is not null, this set does not contain newEntry, and this set
     * has available capacity, then add modifies the set so that it contains
     * newEntry. All other entries remain unmodified.
     *
     * <p>
     * If newEntry is null, then add throws java.lang.IllegalArgumentException
     * without modifying the set. If this set already contains newEntry, then
     * add returns false without modifying the set. If this set has a capacity
     * limit, and does not have available capacity, then add throws
     * SetFullException without modifying the set.
     *
     * @param newEntry The object to be added as a new entry
     * @return true if the addition is successful; false if the item already is
     * in this set
     * @throws SetFullException If this set does not have the capacity to store
     * an additional entry
     * @throws java.lang.IllegalArgumentException If newEntry is null
     */
    public boolean add(T newEntry) throws SetFullException,
            java.lang.IllegalArgumentException {
        
        if (newEntry == null) {
            throw new IllegalArgumentException();
        }
        if (!contains(newEntry)) {
            if (size >= contents.length) {
                contents = Arrays.copyOf(contents, size * 2);
            }
            contents[size] = newEntry;
            size++;
            return true;
        } 
        
        return false;
    }

    /**
     * Removes a specific entry from this set, if possible.
     *
     * <p>
     * If this set contains entry, remove will modify the set so that it no
     * longer contains entry. All other entries remain unmodified.
     *
     * <p>
     * If this set does not contain entry, remove will return false without
     * modifying the set. If entry is null, then remove throws
     * java.lang.IllegalArgumentException without modifying the set.
     *
     * @param entry The entry to be removed
     * @return true if the removal was successful; false if not
     * @throws java.lang.IllegalArgumentException If entry is null
     */
    public boolean remove(T entry) throws java.lang.IllegalArgumentException {
        if (entry == null){
            throw new IllegalArgumentException();
        }
        if (contains(entry)) {
            contents[getIndexOf(entry)] = contents[size - 1];
            contents[size - 1] = null;
            size--;
            return true;
        }

        return false;
    }

    /*
     * returns index of entry recieved as argument. 
     */
    private int getIndexOf(T entry) {
        int where = -1, index = 0;
        while (where == -1 && index < size) {
            if (entry.equals(contents[index])) {
                where = index;
            }
            index++;
        }
        return where;
    }

    /**
     * Removes an unspecified entry from this set, if possible.
     *
     * <p>
     * If this set contains at least one entry, remove will modify the set so
     * that it no longer contains one of its entries. All other entries remain
     * unmodified. The removed entry will be returned.
     *
     * <p>
     * If this set is empty, remove will return null without modifying the set.
     * Because null cannot be added, a return value of null will never indicate
     * a successful removal.
     *
     * @return The removed entry if the removal was successful; null otherwise
     */
    public T remove() {
        if (size > 0) {
            T result = contents[size - 1];
            contents[size - 1] = null;
            size--;
            return result;
        }
        return null;
    }

    /**
     * Removes all entries from this set.
     *
     * <p>
     * If this set is already empty, clear will not modify the set. Otherwise,
     * the set will be modified so that it contains no entries.
     */
    public void clear() {
        int index = 0;
        if (size > 0) {
            while (index < size) {
                contents[index] = null;
            }
            size = 0;
        }
    }

    /**
     * Tests whether this set contains a given entry.
     *
     * <p>
     * If this set contains entry, then contains returns true. Otherwise
     * (including if this set is empty), contains returns false. If entry is
     * null, then remove throws java.lang.IllegalArgumentException without
     * modifying the set.
     *
     * @param entry The entry to locate
     * @return true if this set contains entry; false if not
     * @throws java.lang.IllegalArgumentException If entry is null
     */
    public boolean contains(T entry) throws java.lang.IllegalArgumentException {
        if (entry == null) {
            return false;
        }
        int index = 0;
        while (index < size) {
            if (entry.equals(contents[index])) {
                return true;
            }
            index++;
        }
        return false;

    }

    /**
     * Retrieves all entries that are in this set.
     *
     * <p>
     * An array is returned that contains a reference to each of the entries in
     * this set. Its capacity will be equal to the number of elements in this
     * set, and thus the array will contain no null values.
     *
     * <p>
     * If the implementation of set is array-backed, toArray will not return the
     * private backing array. Instead, a new array will be allocated with the
     * appropriate capacity.
     *
     * @return A newly-allocated array of all the entries in this set
     */
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[size];
        for (int i = 0; i < size; i++){
            result[i] = contents[i];
        }
        return result;
    }

}
