public class SecurityDB extends SecurityDBBase {

    HashTable hash = new HashTable(this.size());
    private int counter = 1;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void increment() {
        setCounter(getCounter() + 1);
    }

    /**
     * Creates an empty hashtable and a variable to count non-empty elements.
     *
     * @param numPlanes             number of planes per day
     * @param numPassengersPerPlane number of passengers per plane
     */
    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
    }

    public static void main(String[] args) {
        SecurityDB db = new SecurityDB(1021,1);
        db.addPassenger("Peter Mandela", "5AUVQV");
        db.addPassenger("Richard Ross", "LFCO1P");
        db.addPassenger("Adam Smith", "ATHG8B");
        db.remove("LFCO1P");
        System.out.println(db.contains("LFCO1P"));
        System.out.println(db.contains("ATHG8B"));
    }

    @Override
    public int calculateHashCode(String key) {
        int currentVal = 1;
        int result = 0;

        if(key.length() == 0) {
            result = 1;
        }

        for(int i = 0; i < key.length(); i++) {
            currentVal += key.charAt(i);
            result += currentVal;
        }
        return result;
    }

    @Override
    public int size() {
        int constraint = this.getNumPassengersPerPlane() * this.getNumPlanes();
        int mValue = constraint + 1;

        if(constraint >= 1021) {
            return 1021;
        }
        while(!isPrime(mValue)) {
            mValue++;
        }

        return mValue;
    }

    @Override
    public String get(String passportId) {
        int index = getIndex(passportId);
        while (hash.getKeys()[index] != null) {
            if (hash.getKeys()[index].equals(passportId)) {
                return hash.getValues()[index];
            }
            index = (index + 1) % hash.getMaxSize();
        }
        return null;
    }

    @Override
    public boolean remove(String passportId){
        //cannot remove what isn't there
        if (!contains(passportId) || passportId == null)
            return false;

        // Find position key and delete
        int indexToRemove = getIndex(passportId);
        while (!passportId.equals(hash.getKeys()[indexToRemove]))
            indexToRemove = (indexToRemove + 1) % hash.getMaxSize();
        hash.getKeys()[indexToRemove] = hash.getValues()[indexToRemove] = null;

        // rehash all keys
        for (indexToRemove = (indexToRemove + 1) % indexToRemove; hash.getKeys()[indexToRemove] != null;
             indexToRemove = (indexToRemove + 1) % hash.getMaxSize()) {
            String tempName = hash.getKeys()[indexToRemove];
            String tempPassportId = hash.getValues()[indexToRemove];
            hash.getKeys()[indexToRemove] = hash.getValues()[indexToRemove] = null;
            hash.setCurrentSize(hash.getCurrentSize() - 1);
            addPassenger(tempName, tempPassportId);
        }
        hash.setCurrentSize(hash.getCurrentSize() - 1);
        return true;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        if(name == null || passportId == null) {
            return false;
        }

        //try this after we check for null
        int tempIndex = getIndex(passportId);
        int indexToCheck = tempIndex;
        int originalSize = hash.getCurrentSize();

        if(contains(passportId) ) {
            increment();
            return true;
        }

        if(getCounter() > 5) {
            System.err.print("Suspicious");
            setCounter(0);
            return false;
        }

        do {
            if (hash.getKeys()[indexToCheck] == null) {
                hash.getKeys()[indexToCheck] = passportId;
                hash.getValues()[indexToCheck] = name;
                hash.setCurrentSize(hash.getCurrentSize() + 1);
                break;
            }

            // trying to add a person with a passport that isn't unique to what is stored in
            // the hash table already
            if(hash.getKeys()[indexToCheck].equals(passportId)
                    && !hash.getValues()[indexToCheck].equals(name)) {
                return false;
            }

            indexToCheck = (indexToCheck + 1) % hash.getMaxSize();

        } while (indexToCheck != tempIndex);

        //check if size incremented?
        return hash.getCurrentSize() == originalSize + 1;
    }

    @Override
    public int count() {
        return hash.getCurrentSize();
    }

    @Override
    public int getIndex(String passportId) {
        return calculateHashCode(passportId) % hash.getMaxSize();
    }


    //is prime boolean function to check if the number is a prime so we can utilise
    // this in the size function
    public boolean isPrime(int num) {
        int count = 0;
        boolean prime = false;

        for(int i = 1; i <= num; i++) {
            if(num % i == 0) {
                count++;
            }
        }

        if(count <= 2) {
            prime = true;
        }
        return prime;
    }
}

class HashTable {
    private int currentSize;
    private int maxSize;
    private String[] keys;
    private String[] values;

    public HashTable(int maximumSize) {
        //CREATING A STATIC SIZED HASH TABLE
        currentSize = 0;
        maxSize = maximumSize;
        keys = new String[maxSize];
        values = new String[maxSize];
    }

    public void setEmpty() {
        currentSize = 0;
        keys = new String[maxSize];
        values = new String[maxSize];
    }

    public  int getCurrentSize() {
        return this.currentSize;
    }

    public boolean fullTable() {
        return currentSize == maxSize;
    }

    public  boolean isEmpty() {
        return this.getCurrentSize() == 0;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String[] getKeys() {
        return keys;
    }

    public String[] getValues() {
        return values;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
