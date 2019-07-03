package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MazeSolver {

    private Maze maze;
    private Square startSquare;
    private Square endSquare;

    public MazeSolver(String mazeFilePath) {
        Scanner s;
        try {
            s = new Scanner(new File(mazeFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Read inputs in from file
        int width = s.nextInt();
        int height = s.nextInt();
        int startX = s.nextInt();
        int startY = s.nextInt();
        int endX = s.nextInt();
        int endY = s.nextInt();

        maze = new Maze(width, height, mazeFilePath);

        // Set start and end positions
        startSquare = maze.getSquare(startX, startY);
        endSquare = maze.getSquare(endX, endY);
    }

    // Print the specified path following the specification
    private void printPath(Set<Square> path) {
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

    // Pythagorean distance between 2 squares
    // (sqrt( (x_2 - x_1)^2 + (y_2 - y_1)^2))
    private double dist(Square a, Square b) {
        int sqX = a.getX() - b.getX();
        int sqY = a.getY() - b.getY();

        return Math.hypot((double) sqX, (double) sqY);
    }

    // Return the key set of a map as a straight set
    // had difficulties getting .keySet() working
    private Set<Square> getKeySet(Map<Square, Square> pathPairs) {
        Set<Square> keys = new HashSet<>();
        for (Square key : pathPairs.keySet()) {
            keys.add(key);
        }
        return keys;
    }

    // Construct a route from end square to start square from pair mapping
    private Set<Square> constructPath(Map<Square, Square> pathPairs, Square current) {
        Set<Square> path = new HashSet<>();
        path.add(current);
        Square lookUpSquare = current;
        Set<Square> keySet = getKeySet(pathPairs);

        // Trace back though path pair mapping until
        // a square doesnt have a previous square
        while (keySet.contains(lookUpSquare)) {
            lookUpSquare = pathPairs.get(lookUpSquare);
            path.add(lookUpSquare);
        }
        return path;
    }

    // A* implementation of path finding
    // followed pseudo-code at:
    // https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
    public void startSearch() {

        // Set of visitied squares
        Set<Square> closedSet = new HashSet<>();

        // Set of evaled squares
        Set<Square> openSet = new HashSet<>();
        openSet.add(startSquare); // Start at start square

        // Map of sqaure to square, used to trace back paths
        // sq1 -> sq2 => got to sq1 by moving from sq2 OR
        // moved from sq2 to sq1
        Map<Square, Square> cameFrom = new HashMap<>();

        // Map of square to square's distance to start square
        Map<Square, Double> gScore = new HashMap<>();

        // Map of square to square's distance to end square
        Map<Square, Double> fScore = new HashMap<>();

        // Init distances of all squares to infinity (max double value)
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                Square sq = maze.getSquare(j, i);
                // Max possible distance (in steps taken) is every
                // square in the maze
                gScore.put(sq, Double.MAX_VALUE);
                fScore.put(sq, Double.MAX_VALUE);
            }
        }

        // Distance from start node to start node is 0
        gScore.put(startSquare, 0.0);

        // Heuristic guess of what distance it will be from
        // start square to end square
        double guess = dist(startSquare, endSquare);
        fScore.put(startSquare, guess);

        // While there are still squares to explore from
        while (openSet.size() > 0) {
            Square current = openSet.iterator().next();
            Double currentfScore = fScore.get(current);
            for (Square sq : openSet) {
                Double tempfScore = fScore.get(sq);
                if (tempfScore.compareTo(currentfScore) < 0) {
                    current = sq;
                }
            }

            // If reached end square, found a path
            if (current == endSquare) {
                // print path
                System.out.println("FOUND PATH");
                Set<Square> path = constructPath(cameFrom, current);
                printPath(path);
                return;
            }

            // Move current square from open to close as its
            // has been evaluated
            openSet.remove(current);
            closedSet.add(current);

            // Traverse non-wall neighbours
            List<Square> travNeighbourSquares = getTravelableNeighbours(current);
            for (Square sq : travNeighbourSquares) {
                // If square has already been evaluated
                if (closedSet.contains(sq)) {
                    continue;
                }

                // Score of square (from start to end)
                Double tempgScore = gScore.get(current) + dist(current, sq);

                // If square has not yet started evaluation then add to open set
                if (!openSet.contains(sq)) {
                    openSet.add(sq);
                } else if (tempgScore.compareTo(gScore.get(sq)) > -1) {
                    // If the score isnt better then the current evaled one then skip
                    continue;
                }

                // Update scores and path pairing
                cameFrom.put(sq, current);
                gScore.put(sq, tempgScore);
                fScore.put(sq, tempgScore + dist(sq, endSquare));
            }
        }
        System.out.println("No found path from " + startSquare + " to " + endSquare + ".\n");
    }

    private List<Square> getTravelableNeighbours(Square current) {
        List<Square> neighbours = new ArrayList<>();
        int sqX = current.getX();
        int sqY = current.getY();

        int sqXWest = sqX - 1;
        int sqXEast = sqX + 1;
        int sqYNorth = sqY - 1;
        int sqYSouth = sqY + 1;

        // Account for wrapping
        if (sqXWest == -1) {
            sqXWest = maze.getWidth() - 1;
        }
        if (sqXEast == maze.getWidth()) {
            sqXEast = 0;
        }
        if (sqYNorth == -1) {
            sqYNorth = maze.getHeight() - 1;
        }
        if (sqYSouth == maze.getHeight()) {
            sqYSouth = 0;
        }
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
}