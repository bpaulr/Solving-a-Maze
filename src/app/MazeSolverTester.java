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
            System.out.println("Test " + (i + 1) + ": " + inputFiles[i]);
            ms.startSearch();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {

            if (args.length < 2) {
                System.err.println("Please provide a path and at least 1 maze input file.");
                System.err.println("<INPUT_PATH_DIR> <INPUT_FILE_1> ... <INPUT FILE N>");
            } else {
                String path = args[0];
                System.out.println("Input Path: " + "\n" + args[0] + "\n");
                int len = args.length;
                System.out.println("Input Files: ");
                String[] files = new String[len - 1];
                for (int i = 1; i < args.length; i++) {
                    System.out.println(args[i]);
                    files[i - 1] = args[i];
                }
                System.out.println();
                MazeSolverTester tester = new MazeSolverTester(path, files);
                tester.startTests();
            }

        } else {
            String path = "resources/";
            String[] files = new String[] { "input.txt", "input2.txt", "input_no_path.txt", "maze1.txt",
                    "large_input.txt", "medium_input.txt", "small_input.txt", "small_wrap_input.txt",
                    "sparse_large.txt", "sparse_medium.txt" };
            MazeSolverTester tester = new MazeSolverTester(path, files);
            tester.startTests();
        }
    }

}