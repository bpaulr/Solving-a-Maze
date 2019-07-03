package app;

public class MazeSolverTester {

    private String path;
    private String[] inputFiles;
    
    public MazeSolverTester(String path, String[] files) {
        this.path = path;
        inputFiles = files;
    }

    public void startTests() {
        for (int i = 0; i < inputFiles.length; i++) {
            MazeSolver ms = new MazeSolver(path + inputFiles[i]);
            System.out.println("Test " + (i+1) + ": " + inputFiles[i]);
            ms.startSearch();
        }
    }    

    public static void main(String[] args) {
        String path = "resources/";
        String[] files = new String[] {"input.txt", "input2.txt", "input_no_path.txt", "maze1.txt", "large_input.txt", "medium_input.txt", "small_input.txt", "small_wrap_input.txt", "sparse_large.txt", "sparse_medium.txt"};
        MazeSolverTester tester = new MazeSolverTester(path, files);
        tester.startTests();
    }

}