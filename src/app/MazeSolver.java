package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MazeSolver {

    private Maze maze;
    private Square startSquare;
    private Square endSquare;

    private Set<Square> path;

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
        path = new HashSet<>();
    }

    // if can make move that has not been made make it (use set if vistited squares)
    // if sqaure is end node return true
    // if return true then add to set "route"
    // else
    // repeat

    private void printPath() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                Square currentSquare = maze.getSquare(j, i);
                String s = currentSquare.isWall() ? "#" : " ";
                if (path.contains(currentSquare)) {
                    if (currentSquare == startSquare) {
                        s = "S";
                    } else if (currentSquare == endSquare) {
                        s = "E";
                    } else {
                        s = "X";
                    }
                }
                sb.append("" + s);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public void startSearch() {
        Set<Square> visitedSquares = new HashSet<>();
        boolean foundPath = dfs(startSquare, visitedSquares);
        if (foundPath) {
            path.add(startSquare);
            printPath();
        } else {
            System.out.println("COULD NOT FIND PATH!");
        }
    }

    private boolean dfs(Square currentSquare, Set<Square> visitedSquares) {
        if (currentSquare == endSquare) {
            path.add(currentSquare);
            return true;
        }
        List<Square> neighbours = getTravelableNeighbours(currentSquare);
        visitedSquares.add(currentSquare);
        for (Square sq : neighbours) {
            if (visitedSquares.contains(sq)) {
                continue;
            }
            boolean foundPath = dfs(sq, visitedSquares);
            if (foundPath) {
                path.add(currentSquare);
                return true;
            }
        }
        return false;
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
        if (sqXWest == -1)
            sqXWest = maze.getWidth() - 1;
        if (sqXEast == maze.getWidth())
            sqXEast = 0;
        if (sqYNorth == -1)
            sqYNorth = maze.getHeight() - 1;
        if (sqYSouth == maze.getHeight())
            sqYSouth = 0;

        // Add non wall neighbours to list
        Square loopupSquare = maze.getSquare(sqXWest, sqY);
        if (!loopupSquare.isWall()) {
            neighbours.add(loopupSquare);
        }
        loopupSquare = maze.getSquare(sqXEast, sqY);
        if (!loopupSquare.isWall()) {
            neighbours.add(loopupSquare);
        }
        loopupSquare = maze.getSquare(sqX, sqYNorth);
        if (!loopupSquare.isWall()) {
            neighbours.add(loopupSquare);
        }
        loopupSquare = maze.getSquare(sqX, sqYSouth);
        if (!loopupSquare.isWall()) {
            neighbours.add(loopupSquare);
        }
        return neighbours;
    }

    public Maze getMaze() {
        return maze;
    }

    public static void main(String[] args) throws Exception {
        String path = "resources/";
        MazeSolver ms = new MazeSolver(path + "sparse_medium.txt");
        // List<Square> test = ms.getTravelableNeighbours(ms.getMaze().getSquare(1, 1));
        // for (Square s : test)
        //     System.out.println(s);
        System.out.println(ms.getMaze());
        ms.startSearch();
    }
}