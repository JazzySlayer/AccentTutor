package accenttutor;

/**
 * Created by Sushant on 8/10/2016.
 */
public class DTW {

    protected float[] seq1;
    protected float[] seq2;
    protected float[][] warpingPath;
    protected int n;
    protected int m;
    protected int K;

    protected double warpingDistance;
    public DTW(float[] sample, float[] templete) {
        seq1 = sample;
        seq2 = templete;

        n = seq1.length;
        m = seq2.length;
        K = 1;

        warpingPath = new float[n + m][2];        // max(n, m) <= K < n + m
        warpingDistance = 0.0;

        this.compute();

    }

    public void compute() {
        float accumulatedDistance = 0;

        float[][] d = new float[n][m];        // local distances
        float[][] D = new float[n][m];        // global distances

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = distanceBetween(seq1[i], seq2[j]);
            }
        }

        D[0][0] = d[0][0];

        for (int i = 1; i < n; i++) {
            D[i][0] = d[i][0] + D[i - 1][0];
        }

        for (int j = 1; j < m; j++) {
            D[0][j] = d[0][j] + D[0][j - 1];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                accumulatedDistance = Math.min(Math.min((int)D[i-1][j], (int)D[i-1][j-1]), (int)D[i][j-1]);
                accumulatedDistance += d[i][j];
                D[i][j] = accumulatedDistance;
            }
        }
        accumulatedDistance = D[n - 1][m - 1];

//
//        for(int i=0;i<n;i++){
//            for(int j=0;j<m;j++){
//                System.out.print("D["+i+"]["+j+"]="+D[i][j]);
//            }
//            System.out.println();
//        }

        int i = n - 1;
        int j = m - 1;
        int minIndex = 1;

        warpingPath[K - 1][0] = i;
        warpingPath[K - 1][1] = j;

        while ((i + j) != 0) {
            if (i == 0) {
                j -= 1;
            } else if (j == 0) {
                i -= 1;
            } else {        // i != 0 && j != 0
                double[] array = { D[i - 1][j], D[i][j - 1], D[i - 1][j - 1] };
                minIndex = this.getIndexOfMinimum(array);

                if (minIndex == 0) {
                    i -= 1;
                } else if (minIndex == 1) {
                    j -= 1;
                } else if (minIndex == 2) {
                    i -= 1;
                    j -= 1;
                }
            } // end else
            K++;
            warpingPath[K - 1][0] = i;
            warpingPath[K - 1][1] = j;
        } // end while
        warpingDistance = accumulatedDistance / K;

        this.reversePath(warpingPath);
    }

    /**
     * Changes the order of the warping path (increasing order)
     *
     * @param path  the warping path in reverse order
     */
    protected void reversePath(float[][] path) {
        float[][] newPath = new float[K][2];
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < 2; j++) {
                newPath[i][j] = path[K - i - 1][j];
//                System.out.print("newPath = " + newPath[i][j]);
            }
//            System.out.println();
        }
        warpingPath = newPath;
    }
    /**
     * Returns the warping distance
     *
     * @return
     */
    public double getDistance() {
        return warpingDistance;
    }

    /**
     * Computes a distance between two points
     *
     * @param p1    the point 1
     * @param p2    the point 2
     * @return              the distance between two points
     */
    protected float distanceBetween(float p1, float p2) {
        return (p1 - p2) * (p1 - p2);
    }
    //HERE p1=(p1-p2)*(p1-p2)
    //here p2=(p1-p2)*(p1-p2)

    /**
     * Finds the index of the minimum element from the given array
     *
     * @param array         the array containing numeric values
     * @return                              the min value among elements
     */
    protected int getIndexOfMinimum(double[] array) {
        int index = 0;
        double val = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < val) {
                val = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     *      Returns a string that displays the warping distance and path
     */
    public String toString() {
        String retVal = "Warping Distance: " + warpingDistance + "\n";
        retVal += "Warping Path: {";
        for (int i = 0; i < K; i++) {
            retVal += "(" + warpingPath[i][0] + ", " +warpingPath[i][1] + ")";
            retVal += (i == K - 1) ? "}" : ", ";

        }
        return retVal;
    }

    /**
     * Tests this class
     *
     * @param args  ignored
     */
}
