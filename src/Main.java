import java.util.Arrays;

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

    }

    public static void generateDp() {
        for(int i = 0; i < image[0].length; i++) {
            findMinSeam(0, i);
        }
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
