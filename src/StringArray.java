// The array has a minimum size of 50
// If the array size is greater than 50, then the size is adjusted to ensure the array
// is always between 25% and 75% capacity.
// If the array is outside of that capacity then it is resized to be at 50% capacity

import java.util.Arrays;
import java.util.Iterator;

public class StringArray implements Iterable<String> {

    private String[] array;
    private int arraySize;
    private int noneNullCount;

    private final int MIN_SIZE = 50;

    public StringArray() {
        this.arraySize = MIN_SIZE;
        this.array = new String[arraySize];
        this.noneNullCount = 0;
    }

    public StringArray(StringArray a) {
        this.array = a.getArray();
        this.arraySize = a.getArraySize();
        this.noneNullCount = a.getNoneNullCount();
    }

    public StringArray(String... strings) {
        this();
        for (String string: strings) {
            add(string);
        }
    }

    // Getters have been marked private to ensure encapsulation
    // They are only used when instantiating a new object with the 'StringArray(StringArray a)'
    // constructor
    private String[] getArray() {
        return array;
    }

    private int getArraySize() {
        return arraySize;
    }

    private int getNoneNullCount() {
        return noneNullCount;
    }

    // I have chosen to store this as an instance variable to ensure this method is O(1)
    // Using a loop to count each non-null element would have been O(n)
    public int size() {
        return noneNullCount;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String get(int index) {
        validateIndex(index);
        return array[index];
    }

    public void set(int index, String s) {
        validateIndex(index);
        array[index] = s;
    }

    public void add(String s) {
        array[size()] = s;
        noneNullCount++;
        adjustSize();
    }

    public void insert(int index, String s) {
        if (isEmpty() && index == 0) { // handle special case when StringArray is empty
            add(s);
        } else {
            validateIndex(index);
            shiftItems(index);
            array[index] = s;
        }
    }

    public void remove(int index) {
        validateIndex(index);
        removeItem(index);
        noneNullCount--;
        adjustSize();
    }

    public boolean contains(String s) {
        String[] lowerArray = lowerArray();
        return Arrays.asList(lowerArray).contains(s.toLowerCase());
    }

    public boolean containsMatchingCase(String s) {
        return Arrays.asList(array).contains(s);
    }

    public int indexOf(String s) {
        String[] lowerArray = lowerArray();
        return Arrays.asList(lowerArray).indexOf(s.toLowerCase());
    }

    public int indexOfMatchingCase(String s) {
        return Arrays.asList(array).indexOf(s);
    }

    public int lastIndex() {
        return size()-1;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, 0, size()));
    }

    // The following are private utility methods

    // Returns the percentage of the array that is not null as a float between 0 and 1
    private float arrayCapacity() {
        return (float) size()/arraySize;
    }

    // Returns what the size of the array needs to be for arrayCapacity
    // to be 50%.
    // I have chosen 50% as the target capacity because it provides equal
    // performance for removing and inserting elements.
    private int idealArraySize() {
        float targetCapacity = 0.5F;
        float adjustmentFactor = (float) (1.0 / (targetCapacity* arraySize/size()));
        int newSize = Math.round(arraySize*adjustmentFactor);
        return Math.max(newSize, MIN_SIZE);
    }

    // Gets the array to between 25% and 75% capacity if it greater than 50 in private size.
    private void adjustSize() {
        if ((arraySize >= MIN_SIZE) && (arrayCapacity() > 0.75 || arrayCapacity() < 0.25)) {
            arraySize = idealArraySize();
            String[] newArray = new String[arraySize];
            System.arraycopy(array, 0, newArray, 0, size());
            array = newArray;
        }
    }

    // Returns true if the user has entered a valid index and throws an exception otherwise
    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (!(index >= 0 && index <= lastIndex())) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
    }

    // Removes the item at the given index
    private void removeItem(int index) {
        if (size() - index >= 0) {
            System.arraycopy(array, index + 1, array, index, size() - index);
        }
        array[lastIndex()] = null;
    }

    // Shifts each item in the array up by one from the given index
    private void shiftItems(int index) {
        String lastItem = get(lastIndex());
        add(lastItem);
        if (size() - 2 - index >= 0) {
            System.arraycopy(array, index, array, index + 1, size() - 2 - index);
        }
    }

    // Returns a copy of the private array with all strings in lowercase
    private String[] lowerArray() {
        String[] lowerArray = Arrays.copyOf(array, size());
        for (int i = 0; i < lowerArray.length; i++) {
            lowerArray[i] = lowerArray[i].toLowerCase();
        }
        return lowerArray;
    }

    // I've added an iterator to allow this class to have enhanced for loops
    @Override
    public Iterator<String> iterator() {
        Iterator<String> iterator = new Iterator<>() {

            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public String next() {
                return get(currentIndex++);
            }

            @Override
            public void remove() {
                removeItem(--currentIndex);
            }
        };
        return iterator;
    }

}
