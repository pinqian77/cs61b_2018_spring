package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean grid[][];  // the system
    private int openCount;     // count the number of open sites
    private WeightedQuickUnionUF ufSet;
    private WeightedQuickUnionUF ufSetOnlyWithTop;
    private int topVirtualSite;
    private int bottomVirtualSite;

    /** create N-by-N grid, with all sites initially blocked*/
    public Percolation(int N){
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size cannot be less than 0!");
        }
        grid = new boolean[N][N];
        openCount = 0;
        ufSet = new WeightedQuickUnionUF(N * N + 2);
        ufSetOnlyWithTop = new WeightedQuickUnionUF(N * N + 1);
        topVirtualSite = 0;
        bottomVirtualSite = N * N + 1;
    }

    /** help function to convert 2D representation to 1D index*/
    private int xyTo1D(int x, int y){
        return x * grid.length + y + 1;
    }

    /** help function to check if the position is valid, if not, then throw exception*/
    private void validate(int row, int col){
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length){
            throw new IndexOutOfBoundsException();
        }
    }

    /** open the site (row, col) if it is not open already*/
    public void open(int row, int col){
        validate(row, col);
        if (!isOpen(row, col)){
            grid[row][col] = true;
            openCount += 1;
            // union if adjacent sites are both true
            if (row - 1 >= 0 && isOpen(row - 1, col)){
                ufSet.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                ufSetOnlyWithTop.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row + 1 < grid.length && isOpen(row + 1, col)){
                ufSet.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                ufSetOnlyWithTop.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col - 1 >= 0 && isOpen(row, col - 1)){
                ufSet.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                ufSetOnlyWithTop.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col + 1 < grid.length && isOpen(row, col + 1)){
                ufSet.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                ufSetOnlyWithTop.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            // top row or bottom row
            if (row == 0){
                ufSet.union(xyTo1D(row, col), topVirtualSite);
                ufSetOnlyWithTop.union(xyTo1D(row, col), topVirtualSite);
            }
            if (row == grid.length - 1){
                ufSet.union(xyTo1D(row, col), bottomVirtualSite);
            }
        }
    }

    /** check if the site is open, return the boolean value*/
    public boolean isOpen(int row, int col){
        validate(row, col);
        return grid[row][col];
    }

    /** if the site is full, return true*/
    public boolean isFull(int row, int col){
        return ufSetOnlyWithTop.connected(xyTo1D(row, col), topVirtualSite);
    }


    /** number of open sites*/
    public int numberOfOpenSites(){
        return openCount;
    }

    /** if the top virtual site is connected to the bottom virtual site, return true*/
    public boolean percolates(){
        return ufSet.connected(topVirtualSite, bottomVirtualSite);
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(4);
        test.open(0,0);
        test.open(1,0);
        test.open(2,0);
        test.open(3,0);
        System.out.println(test.percolates());
        test.open(2, 2);
        test.open(3, 2);
        System.out.println(test.isFull(2,2));
    }

}
