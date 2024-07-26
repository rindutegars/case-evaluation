import java.util.LinkedList;
import java.util.Scanner;

public class JalanTercepat {

    // kanan (0), kiri (0), bawah (1), atas (-1)
    private static final int[] dRow = { 0, 0, 1, -1 };
    private static final int[] dCol = { 1, -1, 0, 0 };
    private static final String[] direction = { "kanan", "kiri", "bawah", "atas" };

    // resentasi posisi
    private static class Node {
        int row, col, dist;
        String path;
        int[] steps;

        // Inisialisasi posisi baru
        Node(int row, int col, int dist, String path, int[] steps) {
            this.row = row; // baris
            this.col = col; // kolom
            this.dist = dist; // jarak dari titik awal
            this.path = path; // jalur yang diambil
            this.steps = steps; // jumlah langkah dalam setiap arah
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LinkedList<String> map = new LinkedList<>(); // LinkedList menyimpan baris
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("OK"))
                break;
            map.add(line);
        }

        // Konversi LinkedList ke array 2D
        int n = map.size(); // jumlah baris
        int m = map.get(0).length(); // jumlah kolom peta
        char[][] grid = new char[n][m];

        int startRow = -1, startCol = -1; // Variabel untuk menyimpan posisi awal ('^')
        for (int i = 0; i < n; i++) { // Loop untuk mengisi grid
            for (int j = 0; j < m; j++) { // Loop untuk setiap karakter dalam baris
                grid[i][j] = map.get(i).charAt(j); // Mengisi grid dengan karakter
                if (grid[i][j] == '^') { // Jika karakter adalah '^', ini adalah posisi awal
                    startRow = i;
                    startCol = j;
                }
            }
        }
        String result = bfs(grid, startRow, startCol, n, m);
        System.out.println(result);
    }

    // Mencari jalur terpendek menggunakan BFS
    private static String bfs(char[][] grid, int startRow, int startCol, int n, int m) {
        boolean[][] visited = new boolean[n][m];
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(new Node(startRow, startCol, 0, "", new int[4]));
        visited[startRow][startCol] = true;

        while (true) {
            if (queue.isEmpty()) {
                return "tidak ada jalan ";
            }

            Node current = queue.removeFirst();

            if (grid[current.row][current.col] == '*') {
                return formatPath(current.steps) + "\n" + current.dist + " langkah";
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];

                if (isValid(newRow, newCol, n, m, grid, visited)) {
                    visited[newRow][newCol] = true;
                    int[] newSteps = current.steps.clone();
                    newSteps[i]++;
                    queue.add(new Node(newRow, newCol, current.dist + 1, current.path, newSteps));
                }
            }
        }
    }

    private static boolean isValid(int row, int col, int n, int m, char[][] grid, boolean[][] visited) {
        return row >= 0 && row < n && col >= 0 && col < m && grid[row][col] != '#' && !visited[row][col];
    }

    private static String formatPath(int[] steps) {
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (steps[i] > 0) {
                if (!result.isEmpty()) {
                    result += "\n";
                }
                result += steps[i] + " " + direction[i];
            }
        }
        return result;
    }
}
