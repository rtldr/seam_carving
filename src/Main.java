import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.Picture;

public class Main {

    static double[][] image;
    static double[][] dp;
    static Picture pic;
    static int width;
    static int height;

    public static void main(String[] args) {
        // TODO: Code needs refactoring
        // TODO: Optimize algorithm
        // TODO: Add UI and height resizing

        String filePath = "images/water.jpg";
        pic = new Picture(filePath);
        Picture backup = new Picture(pic);

        for(int i = 1; i < 400; i++) {

            if(i % 20 == 0) {
                System.out.println(i);
            }
            width = pic.width();
            height = pic.height();
            image = new double[height][width];
            dp = new double[height][width];



            double[] timeResults = new double[4];
            double start = System.currentTimeMillis();
            applyEnergy(image);
            double end = System.currentTimeMillis();
            timeResults[0] = end - start;

            start = System.currentTimeMillis();
            generateDpPara();
            end = System.currentTimeMillis();
            timeResults[1] = end - start;

            start = System.currentTimeMillis();
            List<Point> minSeam = getSeamFromDp();
            end = System.currentTimeMillis();
            timeResults[2] = end - start;

//            highlightSeam(minSeam, backup);

            start = System.currentTimeMillis();
            removePixels(minSeam);
            end = System.currentTimeMillis();
            timeResults[3] = end - start;

            System.out.println(Arrays.toString(timeResults));
            System.out.println();
        }

        pic.show();
        backup.show();

    }

    public static void removePixels(List<Point> minSeam) {
        Picture newPic = new Picture(pic.width() - 1, pic.height());
        for(int i = 0; i < newPic.height(); i++) {
            int badColumn = (int) minSeam.get(i).getY();
            for(int j = 0; j < newPic.width(); j++) {
                if(j < badColumn) {
                    newPic.set(j, i, pic.get(j, i));
                } else {
                    newPic.set(j, i, pic.get(j + 1, i));
                }
            }
        }
        pic = newPic;
    }

    public static double energy(int x, int y) {
        if (x == 0 || x == pic.height() - 1 || y == 0 || y == pic.width() - 1) {
            return Math.pow(255.0, 2) * 3;
        }

        double deltaX = 0.0;
        double deltaY = 0.0;
        Color x1, x2, y1, y2;

        x1 = pic.get(y, x - 1);
        x2 = pic.get(y, x + 1);
        y1 = pic.get(y - 1, x);
        y2 = pic.get(y + 1, x);

        deltaX = Math.pow((x1.getRed() - x2.getRed()), 2) + Math.pow((x1.getGreen() - x2.getGreen()), 2) + Math.pow((x1.getBlue() - x2.getBlue()), 2);
        deltaY = Math.pow((y1.getRed() - y2.getRed()), 2) + Math.pow((y1.getGreen() - y2.getGreen()), 2) + Math.pow((y1.getBlue() - y2.getBlue()), 2);
        return deltaX + deltaY;
    }

    public static void generateDp() {
        for(int i = 0; i < pic.width(); i++) {
            findMinSeam(0, i);
        }
    }

    public static void applyEnergy(double[][] image) {
        for(int i = 0; i < pic.height(); i++) {
            for(int j = 0; j < pic.width(); j++) {
                image[i][j] = energy(i, j);
            }
        }
    }

    public static void highlightSeam(List<Point> minSeam, Picture pic) {
        for(Point p : minSeam) {
            pic.set((int)p.getY(), (int)p.getX(), Color.RED);
        }
    }

    public static List<Point> getSeamFromDp() {
        double min = dp[0][0];
        int minPos = 0;

        for(int i = 0; i < pic.width(); i++) {
            if(dp[0][i] < min) {
                min = dp[0][i];
                minPos = i;
            }
        }

        List<Point> result = new ArrayList<>();
        result.add(new Point(0, minPos));
        for(int i = 1; i < pic.height(); i++) {
            double middle = dp[i][minPos];
            double left = minPos == 0 ? Integer.MAX_VALUE : dp[i][minPos - 1];
            double right = minPos == pic.width() - 1 ? Integer.MAX_VALUE : dp[i][minPos + 1];

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
        if(i + 1 >= pic.height()) {  // if this is the last row
            dp[i][j] = image[i][j];
        } else {  // if this is not the last row
            double left;
            if(j == 0) {  // if there is no left
                left = Integer.MAX_VALUE;
            } else {  // if there is a left
                if(dp[i + 1][j - 1] == 0) {  // if we haven't found the left minSeam yet
                    findMinSeam(i + 1, j - 1);
                }
                left = dp[i + 1][j - 1];
            }


            double middle;
            if(dp[i + 1][j] == 0) {
                findMinSeam(i + 1, j);
            }
            middle = dp[i + 1][j];


            double right;
            if(j + 1 == pic.width()) {  // if there is no right
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

    private static void generateDpPara() {
        for(int i = 0;i < width; i++) {
            dp[height - 1][i] = image[height - 1][i];
        }

        for(int i = height - 2; i >= 0; i--) {
            double[] nextRow = dp[i + 1];
            for(int j = 0; j < width; j++) {
                double left, mid, right;
                if(j == 0) {
                    left = Double.MAX_VALUE;
                } else {
                    left = nextRow[j - 1];
                }

                mid = nextRow[j];

                if(j == width - 1) {
                    right = Double.MAX_VALUE;
                } else {
                    right = nextRow[j + 1];
                }

                double minChild = Math.min(Math.min(left, right), mid);
                dp[i][j] = image[i][j] + minChild;
            }
        }
    }

    public static void print2dArray(double[][] arr) {
        for(double[] curr : arr) {
            System.out.println(Arrays.toString(curr));
        }
    }
}
