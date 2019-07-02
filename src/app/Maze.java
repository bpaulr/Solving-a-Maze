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
                int num = maze[i][j].isWall() ? 1 : 0;
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
                maze[i][j] = new Square(i, j, isWall);
            }
        }
        s.close();
    }

    public List<Square> getTravelableNeighbours(Square current) {
        List<Square> neighbours = new ArrayList<>();
        int sqX = current.getX();
        int sqY = current.getY();

        int sqXWest = sqX - 1;
        int sqXEast = sqX + 1;
        int sqYNorth = sqY - 1;
        int sqYSouth = sqY + 1;

        // Account for wrapping
        if (sqXWest == -1) sqXWest = width - 1;
        if (sqXEast == width) sqXEast = 0;
        if (sqYNorth == -1) sqYNorth = height - 1;
        if (sqYSouth == height) sqYSouth = 0;
        
        if (!maze[sqXWest][sqY].isWall()) {
            neighbours.add(maze[sqXWest][sqY]);
        }
        if (!maze[sqXEast][sqY].isWall()) {
            neighbours.add(maze[sqXEast][sqY]);
        }
        if (!maze[sqX][sqYNorth].isWall()) {
            neighbours.add(maze[sqX][sqYNorth]);
        }
        if (!maze[sqX][sqYSouth].isWall()) {
            neighbours.add(maze[sqX][sqYSouth]);
        }
        return neighbours;
    }

    public Square getSquare(int i, int j) {
        return maze[i][j];
    }
}