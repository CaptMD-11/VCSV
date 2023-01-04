/*
 * This is VCSV, a Java library made by Vignesh Nydhruva, that helps run VStats functions on CSV files. 
    Copyright (C) 2022  Vignesh Nydhruva

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class VCSV {

    private VCSV() {

    }

    /**
     * Returns the number of rows that contain data in a CSV file.
     * 
     * @param filePath the path of the CSV file (with respect to the root directory
     *                 of the Java project).
     * @return the number of rows that contain data in the CSV file with location
     *         <strong>filePath</strong>.
     */
    public static int getNumRows(String filePath) {
        File file = new File(filePath);
        try {
            int count = 1;
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                count++;
                line = scanner.nextLine();
            }
            return count;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * Returns the number of columns that contain data in a CSV file.
     * 
     * @param filePath the path of the CSV file (with respect to the root directory
     *                 of
     *                 the Java project).
     * @return the number of columns that contain data in the CSV file with location
     *         <strong>filePath</strong>.
     */
    public static int getNumCols(String filePath) {
        File file = new File(filePath);
        try {
            int count = 0;
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.substring(i, i + 1).equals(","))
                    count++;
            }
            return count + 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * Returns an array of numbers that exist in a certain row of a CSV file.
     * <p>
     * This method throws <code>java.lang.NumberFormatException</code> when the
     * input column contains a character that cannot be converted into a
     * <code>double</code>.
     * 
     * @param filePath the path of the CSV file (with respect to the root directory
     *                 of the Java project).
     * @param row      the input row number (0-based index logic).
     * @return an array of numbers that exist in row <strong>row</strong> of the CSV
     *         file located at <strong>filePath</strong>.
     */
    public static double[] getRowOfNumbers(String filePath, int row) {
        File file = new File(filePath);
        ArrayList<Double> res = new ArrayList<Double>();
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            int curr = 0;
            while (scanner.hasNextLine()) {
                if (curr == row) {
                    ArrayList<String> strList = new ArrayList<String>();
                    String temp = "";
                    for (int i = 0; i < line.length(); i++) {
                        if (line.substring(i, i + 1).equals(",")) {
                            strList.add(temp);
                            temp = "";
                        } else
                            temp += line.substring(i, i + 1);
                    }
                    strList.add(line.substring(line.lastIndexOf(",") + 1));
                    for (int i = 0; i < strList.size(); i++) {
                        res.add(Double.parseDouble(strList.get(i)));
                    }
                    break;
                } else
                    curr++;
                line = scanner.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        double[] output = new double[res.size()];
        for (int i = 0; i < res.size(); i++) {
            output[i] = res.get(i);
        }
        return output;
    }

    /**
     * Returns an array of numbers that exist in a certain column of a CSV file.
     * <p>
     * This method throws <code>java.lang.NumberFormatException</code> when the
     * input column contains a character that cannot be converted into a
     * <code>double</code>.
     * 
     * @param filePath the path of the CSV file (with respect to the root directory
     *                 of the Java project).
     * @param col      the input column number (0-based index logic).
     * @return an array of numbers that exist in column <strong>col</strong> of the
     *         CSV file located at <strong>filePath</strong>.
     */
    public static double[] getColumnOfNumbers(String filePath, int col) {
        File file = new File(filePath);
        ArrayList<Double> res = new ArrayList<Double>();
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            line = scanner.nextLine();
            while (line.length() != 0) {
                if (col == 0)
                    res.add(Double.parseDouble(line.substring(0, line.indexOf(","))));
                else if (col == getNumCols(filePath) - 1)
                    res.add(Double.parseDouble(line.substring(line.lastIndexOf(",") + 1)));
                else
                    res.add(Double.parseDouble(
                            line.substring(getNthIndexOf(line, col, ",") + 1, getNthIndexOf(line, col +
                                    1, ","))));
                line = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        double[] output = new double[res.size()];
        for (int i = 0; i < res.size(); i++) {
            output[i] = res.get(i);
        }
        return output;
    }

    // HELPER METHOD
    private static int getNthIndexOf(String line, int n, String str) {
        int count = 0;
        ArrayList<Integer[]> tracker = new ArrayList<Integer[]>();
        for (int i = 0; i < line.length(); i++) {
            if (line.substring(i, i + 1).equals(str)) {
                count++;
                Integer[] arr = { count, i };
                tracker.add(arr);
            }
        }
        for (int i = 0; i < tracker.size(); i++) {
            if (n == tracker.get(i)[0])
                return tracker.get(i)[1];
        }
        return 0;
    }

}