public class Plane extends PlaneBase {

    public Plane(String planeNumber, String time) {
        super(planeNumber, time);
    }

    /**
     Accessor method for Plane's Number
     return: String of the Plane's Number
     */
    @Override
    public String getPlaneNumber() {
        return super.getPlaneNumber();
    }

    /**
      Accessor method for Plane's time in hours:minutes format
      return: String of the Plane's time in hours:minutes
     */
    @Override
    public String getTime() {
        return super.getTime();
    }

    /***
     * Extracts the time from the planes current time
     * @param time the planes time string
     * @return total time in minutes as an integer
     */
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


    @Override
    public int compareTo(PlaneBase planeToCompare) {
        //The times corresponding to planeOne and planeTwo
        int planeOneTime = comparatorTime(this.getTime());
        int planeTwoTime = comparatorTime(planeToCompare.getTime());

        //the planeNumber string corresponding to planeOne and planeTwo
        String planeOneLetters = this.getPlaneNumber();
        String planeTwoLetters = planeToCompare.getPlaneNumber();

        //the times match, compare by
        if(planeOneTime == planeTwoTime) {
            return planeOneLetters.compareTo(planeTwoLetters);
        }

        //time is not equal, return -1 if planeOneTime is smaller
        //return 1 if planeTwoTime is smaller
        return planeOneTime < planeTwoTime ? -1 : 1;
    }
}
