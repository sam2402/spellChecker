// Pre-requisites for using the Dictionary class:
// 1. The words are sorted alphabetically in ascending order
// 2. The words are all lowercase
// These are enforced at instantiation and whenever a new word is added
// via the set, add or insert methods

public class Dictionary extends StringArray {

    public Dictionary() {
        super();
    }

    public Dictionary(StringArray a) {
        super(a);
        validateDictionary();
    }

    // These methods are being overridden ensure the dictionary meets the pre-requisites
    // The validateEntry function has O(1) time complexity
    @Override
    public void set(int index, String s) {
        super.set(index, s);
        validateEntry(index);
    }

    @Override
    public void add(String s) {
        super.add(s);
        validateEntry(lastIndex());
    }

    @Override
    public void insert(int index, String s) {
        super.insert(index, s);
        validateEntry(index);
    }

    // These methods are being overridden to take advantage of the data being sorted
    @Override
    public boolean contains(String s) {
        String lowerS = s.toLowerCase();
        return containsMatchingCase(lowerS);
    }

    @Override
    public boolean containsMatchingCase(String s) {
        return !(binarySearch(0, lastIndex(), s) == -1);
    }

    @Override
    public int indexOf(String s) {
        String lowerS = s.toLowerCase();
        return indexOfMatchingCase(lowerS);
    }

    @Override
    public int indexOfMatchingCase(String s) {
        return binarySearch(0, lastIndex(), s);
    }

    private int binarySearch(int low, int high, String word) {
        if (high >= low) {
            int mid = low + (high - low) / 2;

            if (get(mid).compareTo(word) == 0) {
                return mid;
            }
            if (get(mid).compareTo(word) < 0) {
                return binarySearch(mid + 1, high, word);
            }
            if (get(mid).compareTo(word) > 0) {
                return binarySearch(low, mid - 1, word);
            }

        }
        return -1;
    }

    private void validateDictionary() {
        if (!(dictionaryIsLowerCase())) {
            throw new RuntimeException("Dictionary is not all lowercase");
        }
        if (!(dictionaryIsOrdered())) {
            throw new RuntimeException("Dictionary is not sorted");
        }
    }

    private boolean dictionaryIsOrdered() {
        for (String word: this) {
            if (word.compareTo(iterator().next()) < 0) {
                System.out.println(word);
                System.out.println(iterator().next());
                return false;
            }
        }
        return true;
    }

    private boolean dictionaryIsLowerCase() {
        for (String word: this) {
            if (!(entryIsLowerCase(word))) {
                return false;
            }
        }
        return true;
    }

    private void validateEntry(int index) {
        if (!(entryIsLowerCase(get(index)))) {
            throw new RuntimeException("\"" + get(index) + "\" is not lowercase");
        }
        if (!(entryIsOrdered(index))) {
            throw new RuntimeException("Entry \"" + get(index) + "\" causes dictionary to become unsorted");
        }
    }

    private boolean entryIsOrdered(int index) {
        if ((index-1 >= 0) && get(index).compareTo(get(index - 1)) < 0) {
            return false;
        } else return (index + 1 > size() - 1) || get(index).compareTo(get(index + 1)) <= 0;
    }

    private boolean entryIsLowerCase(String word) {
        return word.equals(word.toLowerCase());
    }

}
