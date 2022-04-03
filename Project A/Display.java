class DisplayRandom extends DisplayRandomBase {

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    @Override
    public Plane[] getData() {
        return super.getData();
    }

    @Override
    public Plane[] sort() {
        Plane[] planesToSort = getData();
        mergeSort(planesToSort, 0, planesToSort.length - 1);
        return getData();
    }

    public void mergeSort(Plane[] planesToSort, int leftIndex, int rightIndex) {
        if(leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            mergeSort(planesToSort, leftIndex, middleIndex);
            mergeSort(planesToSort, middleIndex + 1, rightIndex);
            merge(planesToSort, leftIndex, rightIndex,(leftIndex + rightIndex) / 2);
        }
    }

    public void merge(Plane[] planesToSort, int leftIndex, int rightIndex, int middleIndex) {
        int sizeLeftHalf = middleIndex - leftIndex + 1;
        int sizeRightHalf = rightIndex - middleIndex;

        Plane[] planeLeftHalf = new Plane[sizeLeftHalf];
        Plane[] planeRightHalf = new Plane[sizeRightHalf];

        if (sizeLeftHalf >= 0)
            System.arraycopy(planesToSort, leftIndex , planeLeftHalf, 0, sizeLeftHalf);
        for (int j = 0; j < sizeRightHalf; j++)
            planeRightHalf[j] = planesToSort[middleIndex + 1 + j];

        int i = 0;
        int j = 0;
        int k = leftIndex;

        while((i < sizeLeftHalf) && (j < sizeRightHalf)) {
            if(planeLeftHalf[i].compareTo(planeRightHalf[j]) < 0) {
                planesToSort[k] = planeLeftHalf[i];
                i++;
            }
            else {
                planesToSort[k] = planeRightHalf[j];
                j++;
            }
            k++;
        }

        while(i < sizeLeftHalf) {
            planesToSort[k] = planeLeftHalf[i];
            i++;
            k++;
        }

        while(j < sizeRightHalf) {
            planesToSort[k] = planeRightHalf[j];
            j++;
            k++;
        }
    }
}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    @Override
    public Plane[] getSchedule() {
        return super.getSchedule();
    }

    @Override
    public Plane[] getExtraPlanes() {
        return super.getExtraPlanes();
    }

    /***
     *  A method which appends two sets of data together, and returns the result
     * @param currentData a set of data that is already sorted
     * @param dataToAppend a secondary set of data that isn't sorted
     * @return a resultant list which contains all data
     */
    public Plane[] appendData(Plane[] currentData , Plane[] dataToAppend) {
        int sizeDataToAppend = dataToAppend.length;
        int sizeCurrentData = currentData.length;
        int sizeResultData = sizeCurrentData + sizeDataToAppend;

        Plane[] resultData = new Plane[sizeResultData];

        System.arraycopy(currentData, 0, resultData, 0, sizeCurrentData);
        System.arraycopy(dataToAppend, 0, resultData, sizeCurrentData, sizeDataToAppend);

        return resultData;
    }

    /***
     * Driver method for sorting using sorting
     * algorithms used in the class
     * @return A sorted Plane list,
     *          sorted by insertion sort
     */
    @Override
    public Plane[] sort() {
        Plane[] planeData = appendData(getSchedule(), getExtraPlanes());
        insertionSort(planeData, planeData.length);
        return planeData;
    }

    /***
     * A void returned Insertion sort method which
     * sorts a set of input data of a set size.
     * @param planes the planes we wish to sort
     * @param size the number of planes in the
     *             list which are to be sorted
     */
    public void insertionSort(Plane[] planes, int size) {
        for(int i = 0; i < size; i++) {
            Plane pivot = planes[i];
            int j = i - 1;

            while(j >= 0 && planes[j].compareTo(pivot) >= 0) {
                planes[j + 1] = planes[j];
                j = j - 1;
            }
            planes[j + 1] = pivot;
        }
    }
}
