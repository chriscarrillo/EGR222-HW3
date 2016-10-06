/* BabyNames by Chris Carrillo for EGR222
*  This program searches 2 files to find data
*  from the Social Security Administration
*  on the inputted name & gender.
* */

import java.awt.*;
import java.io.*;
import java.util.*;

public class BabyNames {
    public static final int STARTING_YEAR = 1890; // default 1890 -> 1863
    public static final int DECADE_WIDTH = 60; // default 60 -> 50
    public static final int LEGEND_HEIGHT = 30; // default 30 -> 20
    public static final int PANEL_WIDTH = 780;
    public static final int PANEL_HEIGHT = 500 + (LEGEND_HEIGHT * 2);
    public static final String INTRO = "This program allows you to search through the\n" +
                                        "data from the Social Security Administration\n" +
                                        "to see how popular a particular name has been since " + STARTING_YEAR + ".";
    public static final File NAMES = new File("names.txt"); // default names.txt -> names2.txt
    public static final File MEANINGS = new File("meanings.txt");

    public static String name = "";
    public static String gender = "";
    public static String nameInfo = "";
    public static String nameMeaning = "";
    public static boolean found = false;

    // calls the proper methods
    public static void main(String[] args) throws IOException {
        prompt(INTRO);
        rankInfoText(name, gender);
        if (found) {
            DrawingPanel p = new DrawingPanel(PANEL_WIDTH, PANEL_HEIGHT);
            Graphics g = p.getGraphics();
            graphics(g);
        }
    }

    // prompts the user to enter the name and gender
    private static void prompt(String intro) {
        Scanner console = new Scanner(System.in);
        System.out.println(intro + "\n");
        System.out.print("Name: ");
        name = console.next();
        System.out.print("Gender (M or F): ");
        gender = console.next();
    }

    // calls find to search .txt files, then tells whether if it was found
    private static void rankInfoText(String n, String g) throws FileNotFoundException {
        Scanner names = new Scanner(NAMES);
        Scanner meaning = new Scanner(MEANINGS);
        nameInfo = find(names, n, g);
        nameMeaning = find(meaning, n, g);
        if ((nameInfo.equals("")) || (nameMeaning.equals(""))) {
            System.out.print("\"" + n + "\" not found.");
        }
        else {
            System.out.println(nameInfo);
            System.out.print(nameMeaning);
        }
    }

    // searches the names.txt and meanings.txt and returns the String
    private static String find(Scanner file, String s, String g) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            Scanner find = new Scanner(line);

            if (find.next().equalsIgnoreCase(s) && find.next().equalsIgnoreCase(g)) {
                found = true;
                return line;
            }
        }
        return "";
    }

    // graphs the data properly (text and graph)
    private static void graphics(Graphics g) throws IOException {
        int x = 0;
        Scanner nameData = new Scanner(nameInfo);
        graphTemplate(g);
        g.drawString(nameMeaning, 0, 16);
        nameData.next();
        nameData.next();
        while (nameData.hasNextInt()) {
            int d = nameData.nextInt();
            g.setColor(Color.BLACK);
            if (d == 0) {
                g.drawString("" + 0, x * DECADE_WIDTH, PANEL_HEIGHT - LEGEND_HEIGHT);
            }
            else {
                g.setColor(Color.GREEN);
                g.fillRect(x * DECADE_WIDTH, ((d / 2) + LEGEND_HEIGHT), DECADE_WIDTH / 2, PANEL_HEIGHT - ((d / 2) +
                           LEGEND_HEIGHT) - LEGEND_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawString("" + d, x * DECADE_WIDTH, ((d / 2) + LEGEND_HEIGHT));
            }
            x++;
        }
    }

    // constructs the template of the graph according to the names file
    private static void graphTemplate(Graphics g) throws IOException {
        String space = "";
        int xCount = 0;
        for (int i = 0; i <= DECADE_WIDTH; i++) {
            space += ' ';
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, PANEL_WIDTH, LEGEND_HEIGHT);
        g.fillRect(0, PANEL_HEIGHT - LEGEND_HEIGHT, PANEL_WIDTH, LEGEND_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawLine(0, LEGEND_HEIGHT, PANEL_WIDTH, LEGEND_HEIGHT);
        g.drawLine(0, PANEL_HEIGHT - LEGEND_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT - LEGEND_HEIGHT);
        if (NAMES.getPath().equals("names.txt")) {
            for (int i = STARTING_YEAR; i <= STARTING_YEAR + 130; i += 10) {
                g.drawString(i + space, xCount, PANEL_HEIGHT - 8);
                xCount += DECADE_WIDTH;
            }
        }
        else if (NAMES.getPath().equals("names2.txt")) {
            for (int i = STARTING_YEAR; i <= STARTING_YEAR + 70; i += 10) {
                g.drawString(i + space, xCount, PANEL_HEIGHT - 8);
                xCount += DECADE_WIDTH;
            }
        }
    }
}