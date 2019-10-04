import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.Picture;

public class Main {

    static int[][] image;
    static int[][] dp;

    public static void main(String[] args) {
        int[][] temp = {
                {3, 2, 3},
                {2, 10, 1},
                {9, 1, 4},
                {10, 2, 3}
        };
        image = temp;
        dp = new int[image.length][image[0].length];
        generateDp();
        print2dArray(image);
        System.out.println();
        print2dArray(dp);
        List<Point> minSeam = getSeamFromDp();
        System.out.println(minSeam);
    }

    public static void generateDp() {
        for(int i = 0; i < image[0].length; i++) {
            findMinSeam(0, i);
        }
    }

    public static List<Point> getSeamFromDp() {
        int min = dp[0][0];
        int minPos = 0;

        for(int i = 0; i < image[0].length; i++) {
            if(dp[0][i] < min) {
                min = dp[0][i];
                minPos = i;
            }
        }

        List<Point> result = new ArrayList<>();
        result.add(new Point(0, minPos));
        for(int i = 1; i < image.length; i++) {
            int middle = dp[i][minPos];
            int left = minPos == 0 ? Integer.MAX_VALUE : dp[i][minPos - 1];
            int right = minPos == image[0].length - 1 ? Integer.MAX_VALUE : dp[i][minPos + 1];

            if(left <= middle && left <= right) {
                minPos = minPos - 1;
            } else if(right <= middle) {
                minPos = minPos + 1;
            }

            result.add(new Point(i, minPos));
        }
        return result;
    }

    public static void findMinSeam(int i, int j) {
        if(i + 1 >= image.length) {  // if this is the last row
            dp[i][j] = image[i][j];
        } else {  // if this is not the last row
            int left;
            if(j == 0) {  // if there is no left
                left = Integer.MAX_VALUE;
            } else {  // if there is a left
                if(dp[i + 1][j - 1] == 0) {  // if we haven't found the left minSeam yet
                    findMinSeam(i + 1, j - 1);
                }
                left = dp[i + 1][j - 1];
            }


            int middle;
            if(dp[i + 1][j] == 0) {
                findMinSeam(i + 1, j);
            }
            middle = dp[i + 1][j];


            int right;
            if(j + 1 == image[0].length) {  // if there is no right
                right = Integer.MAX_VALUE;
            } else {  // if there is a right
                if(dp[i + 1][j + 1] == 0) {  // if we haven't found the right minSeam yet
                    findMinSeam(i + 1, j + 1);
                }
                right = dp[i + 1][j + 1];
            }
            dp[i][j] = image[i][j] + Math.min(Math.min(left, right), middle);
        }
    }

    public static void print2dArray(int[][] arr) {
        for(int[] curr : arr) {
            System.out.println(Arrays.toString(curr));
        }
    }
}
