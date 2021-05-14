

## Percolation

In this homework, we are going to solve the percolation problem using Monte Carlo simulation and disjoint sets as the data structure. 

The Monte Carlo method is used to simulate the development of the grid world, that is  given a N-by-N grid system, grid will be opened randomly until it reach the percolation state. Finally we compute the average probability of a system can percolate.

Below is my solution.

### Task 1：Percolation.java

The gird system is defined as a N-by-N boolean 2-D array. A grid with true value means it is being opened. Given that we are going to use disjoint set from Princeton's library, the element type must be Integer, I first write a help function to convert 2-D index to 1-D index. 

```java
/** help function to convert 2D representation to 1D index*/
private int xyTo1D(int x, int y){
    return x * grid.length + y + 1;
}
```

Since the corner case mentioned in task sheet, I write a help function to check if the position is valid.

```java
/** help function to check if the position is valid, if not, then throw exception*/
private void validate(int x, int y){
    if (x < 0 || x >= grid.length || y < 0 || y>= grid.length){
        throw new IndexOutOfBoundsException();
    }
}
```

To check whether the grid is open, just need to look up the value of that position.

```java
/** check if the site is open, return the boolean value*/
public boolean isOpen(int row, int col){
    validate(row, col);
    return grid[row][col];
}
```

Since we need to know how many grids are open when the system reaches the percolate state, I add a attribute called `openCount`. Remember, we need to write add one in `open` method later.

```java
/** number of open sites*/
public int numberOfOpenSites(){
    return openCount;
}
```

Now let's implement `open`. Each open operation need to check whether it can be unioned or not. If the value of adjacent grid is true, then union and set itself to true. It's natural to think that we can use a disjoint set to store these sites with true value, so `ufSet` is added in the attribute with size $N\times N + 2$. We added 2 because then we had to judge the connectivity, so add the top node and the bottom node.

```java
/** open the site (row, col) if it is not open already*/
public void open(int row, int col){
    validate(row, col);
    if (!isOpen(row, col)){
        grid[row][col] = true;
        openCount += 1;
        // union if adjacent sites are both true
        if (row - 1 >= 0 && isOpen(row - 1, col)){
            ufSet.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (row + 1 < grid.length && isOpen(row + 1, col)){
            ufSet.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)){
            ufSet.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (col + 1 < grid.length && isOpen(row, col + 1)){
            ufSet.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }
}
```

So now that we are ready to implement `open`, the next thing we need to think about is how to judge Full and Percolate.

For Brute Force, if we want to judge whether a site is full or not, we need to traverse all sites on the top level to see if one of them is connected to this site. Instead, we can use two virtual sites to represent the top and bottom sites, but need to solve the backwash problem, like this:

<img src="C:\Users\Symmetric_QIAN\AppData\Roaming\Typora\typora-user-images\image-20210511135602297.png" alt="image-20210511135602297" style="zoom:50%;" />

The solution I came up with is to use two disjoint sets to record true sites, one containing two virtual sites, one containing only top virtual site. In this way, when determining whether it is percolate, only the first set is needed to check whether top and bottom are connected.The second set is used to determine whether the site and top are connected.

So now add `topVirtualSite`, `BottomVirtualSite` as attributes, and another set, and make some changes to the `open` method:

```java
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
```

```java
/** if the site is full, return true*/
public boolean isFull(int row, int col){
    return ufSetOnlyWithTop.connected(xyTo1D(row, col), topVirtualSite);
}
```

```java
/** if the top virtual site is connected to the bottom virtual site, return true*/
public boolean percolates(){
    return ufSet.connected(topVirtualSite, bottomVirtualSite);
}
```





### Task 2：PercolationStats.java

What we are going to do in this part is to use Monte Carlo to do simulation. The specific process is as follows:

Suppose we run round T, running simulations on an N by N grid at a time, randomly going to open sites, until percolate. Calculate the number of open times per percolate over the total number of cells and record it. At the end of round T, calculate the mean and variance. The mean is the answer we want to get, and the variance will be used to calculate the confidence interval.

The main calculation is carried out in a for loop, which is implemented as follows:

```java
public PercolationStats(int N, int T, PercolationFactory pf){
    if (N <= 0 || T <= 0) {
        throw new IllegalArgumentException();
    }
    res = new double[T];
    for (int i = 0; i < T; i += 1){
        Percolation percolation = pf.make(N);
        while (!percolation.percolates()){
            percolation.open(StdRandom.uniform(N), StdRandom.uniform(N));
        }
        res[i] = ((double)percolation.numberOfOpenSites()) / (double)(N * N);
    }

    mean = StdStats.mean(res);
    stddev = StdStats.stddev(res);
}
```



