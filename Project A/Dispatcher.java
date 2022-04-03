public class Dispatcher extends DispatcherBase {
    LinkedList<Plane> planes = new LinkedList<>();

    @Override
    public int size() {
        return planes.getSize();
    }

    @Override
    public void addPlane(String planeNumber, String time) {
        Plane planeToAdd = new Plane(planeNumber, time);

        if(planes.headNode == null) {
            planes.insertElement(planeToAdd);
            planes.size++;
            return;
        }

        if(planes.headNode.element.compareTo(planeToAdd) > 0) {
            ListNode<Plane> nodeToCheck = new ListNode<>(planeToAdd);
            nodeToCheck.nextElement = planes.headNode;
            planes.headNode = nodeToCheck;
            planes.size++;
        }

        else {
            ListNode<Plane> nodeToCheck = planes.headNode;
            int positionToMove = 1;
            while (nodeToCheck.nextElement != null) {
                if(nodeToCheck.nextElement.element.compareTo(planeToAdd) > 0) {
                    planes.sortedElementInsert(planeToAdd, positionToMove - 1);
                }
                nodeToCheck = nodeToCheck.nextElement;
                positionToMove++;
            }
            planes.insertElement(planeToAdd);
            planes.size++;
        }

    }

    @Override
    public String allocateLandingSlot(String currentTime) {
        String result = null;
        Plane planeToCheck = planes.headNode.element;

        String planeNum = planeToCheck.getPlaneNumber();
        String planeTIme = planeToCheck.getTime();

        int planeTimeMins = comparatorTime(planeTIme);
        int currentTimeMins = comparatorTime(currentTime);

        if(currentTimeMins >=  planeTimeMins - 5) {

            result = planeNum;
        }
        planes.removeElement(0);

        if(result != null) {
            planes.size--;
        }
        return result;
    }

    /***
     * Finds the plane which matches the input and removes it
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1236", "ENC3455"
     * @return the plane that was removed
     */
    @Override
    public String emergencyLanding(String planeNumber) {
        ListNode<Plane> currentNode = planes.headNode;
        if(!isPresent(planeNumber)) {
            return null;
        }

        if(isPresent(planeNumber)) {
            planes.size--;
        }

        int indexToRemove = 0;
        while(currentNode != null) {

            if((currentNode.element.getPlaneNumber().equals(planeNumber))) {
                planes.removeElement(indexToRemove);
                return planeNumber;
            }
            else {
                indexToRemove++;
            }
            currentNode = currentNode.nextElement;
        }
        return null;
    }



    /***
     * A boolean method to check if the planeNumber is present
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1235", "ENC3454"
     * @return if the plane is present amongst the queue of planes
     */
    @Override
    public boolean isPresent(String planeNumber) {
        ListNode<Plane> currentNode = planes.headNode;
        while(currentNode.nextElement != null) {
            //Plane planeToCompare = planes.headNode.element;
            if(currentNode.element.getPlaneNumber().equals(planeNumber)) {
                return true;
            }
            currentNode = currentNode.nextElement;
        }
        return currentNode.element.getPlaneNumber().equals(planeNumber);
    }

    public int comparatorTime(String time) {
        int totalTime = 0;
        // converting characters to integers in O(1) TC
        int hourOne = time.charAt(0) - '0'; //1st Hour digit
        int hourTwo = time.charAt(1) - '0'; //2nd Hour digit
        int minsOne = time.charAt(3) - '0'; //1st Minute digit
        int minsTwo = time.charAt(4) - '0'; //2nd Minute Digit

        // 60 mins/ hour, 1st digit is a "tenth", multiply by 10
        // i.e. 12:12pm, 60 mins/hr (10 hrs) + 60mins (2 hrs)
        // i.e. 12:12pm, 10mins * 10 + 2mins * 1
        // total = 732 minutes for comparison
        totalTime = 60 * (10 * hourOne + hourTwo)
                +  10 * minsOne + minsTwo;

        return totalTime;
    }
}

/***
 * A node to store a generic paramater with knowledge of the
 * address of the next node
 * @param <T> Generic paramater of the LinkedList
 */
class ListNode<T> {
    T element;
    ListNode<T> nextElement;

    public ListNode(T element) {
        this.element = element;
        this.nextElement = null;
    }
}


//Generic Linked List of Element T, but for our example, we will use
// Plane as the elements in our array
class LinkedList<Plane> {
    ListNode<Plane> headNode;
    int size = 0;

    public void insertElement(Plane element) {
        ListNode<Plane> newNode = new ListNode<>(element);
        newNode.element = element;
        newNode.nextElement = null;

        if(headNode == null) {
            headNode = newNode;
        }
        else {
            ListNode<Plane>  nodeToAdd = headNode;
            while(nodeToAdd.nextElement != null) {

                //iterating through list elements until null
                nodeToAdd = nodeToAdd.nextElement;
            }
            nodeToAdd.nextElement = newNode;
        }
    }

    public void show() {
        ListNode<Plane> nodeToPrint = headNode;

        while(nodeToPrint.nextElement != null) {
            System.out.println(nodeToPrint.element);
            nodeToPrint = nodeToPrint.nextElement;
        }
        System.out.println(nodeToPrint.element);
    }

    public void sortedElementInsert(Plane element, int specificPos) {
        ListNode<Plane> nodeToInsert = new ListNode<>(element);
        nodeToInsert.element = element;
        nodeToInsert.nextElement = null;

        ListNode<Plane> currentNode = headNode;
        for(int i = 1; i < specificPos - 1; i++) {
            currentNode = currentNode.nextElement;
        }
        nodeToInsert.nextElement = currentNode.nextElement;
        currentNode.nextElement = nodeToInsert;
    }

    public int getSize() {
        return size;
    }

    public void removeElement(int posToRemove) {
        if(posToRemove == 0) {
            headNode = headNode.nextElement;
        }
        else {
            ListNode<Plane> tempNode;
            ListNode<Plane> nodeToRemove = headNode;
            for(int j = 0; j < posToRemove - 1; j++) {
                nodeToRemove = nodeToRemove.nextElement;
            }
            tempNode = nodeToRemove.nextElement;
            nodeToRemove.nextElement = tempNode.nextElement;
        }
    }
}