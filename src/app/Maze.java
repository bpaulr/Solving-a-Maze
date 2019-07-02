package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {

    private int width;
    private int height;
    private Square[][] maze;

    public Maze() {

    }

    public Maze(int width, int height, String mazeFilePath) {
        this.width = width;
        this.height = height;
        initMazeArray(mazeFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int num = maze[j][i].isWall() ? 1 : 0;
                sb.append("" + num + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void initMazeArray(String mazeFilePath) {
        Scanner s;
        try {
            s = new Scanner(new File(mazeFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Skip config lines
        s.nextLine();
        s.nextLine();
        s.nextLine();

        maze = new Square[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int input = s.nextInt();
                boolean isWall = input == 0 ? false : true;
                maze[j][i] = new Square(j, i, isWall);
            }
        }
        s.close();
    }

    public Square getSquare(int i, int j) {
        return maze[i][j];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}