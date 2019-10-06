public class ParaTry {
    static double[][] image;
    private static int width;
    private static int height;
    private static double[][] dp;
    public static void main(String[] args) {
        double[][] arr = {
                {1, 2, 3, 6, 1, 2},
                {0, 1, 4, 5, 5, 0},
                {5, 3, 1, 7, 9, 4},
                {0, 1, 4, 5, 5, 0},
                {1, 5, 6, 20, 30, 12}
        };
        image = arr;
        height = image.length;
        width = image[0].length;

        dp = new double[height][width];
//        generateDp();
        Main.print2dArray(dp);
    }




//    private static void generateDp() {
//        for(int i = 0;i < width; i++) {
//            dp[height - 1][i] = image[height - 1][i];
//        }
//
//        for(int i = height - 2; i >= 0; i--) {
//            double[] nextRow = dp[i + 1];
//            for(int j = 0; j < width; j++) {
//                double left, mid, right;
//                if(j == 0) {
//                    left = Double.MAX_VALUE;
//                } else {
//                    left = nextRow[j - 1];
//                }
//
//                mid = nextRow[j];
//
//                if(j == width - 1) {
//                    right = Double.MAX_VALUE;
//                } else {
//                    right = nextRow[j + 1];
//                }
//
//                double minChild = Math.min(Math.min(left, right), mid);
//                dp[i][j] = image[i][j] + minChild;
//            }
//        }
//    }
}
