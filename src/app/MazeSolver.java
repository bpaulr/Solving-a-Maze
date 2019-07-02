package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class MazeSolver {

    private Maze maze;
    private Square startSquare;
    private Square endSquare;

    public MazeSolver() {

    }

    public MazeSolver(String mazeFilePath) {
        Scanner s;
        try {
            s = new Scanner(new File(mazeFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int width = s.nextInt();
        int height = s.nextInt();
        int startX = s.nextInt();
        int startY = s.nextInt();
        int endX = s.nextInt();
        int endY = s.nextInt();

        maze = new Maze(width, height, mazeFilePath);

        startSquare = maze.getSquare(startX, startY);
        endSquare = maze.getSquare(endX, endY);

    }

    public Maze getMaze() {
        return maze;
    }

    public static void main(String[] args) throws Exception {
        MazeSolver ms = new MazeSolver("src/app/maze1.txt");
        // System.out.println(ms.getMaze());
        List<Square> test = ms.getMaze().getTravelableNeighbours(ms.maze.getSquare(1, 1));
        for (Square s : test) System.out.println(s);
    }
}