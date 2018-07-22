import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private boolean[] isOpen;
    private boolean[] isFull;
    private int numberOfOpenSites;
    private int idTopSite;
    private int idBotSite;
    private int size;
    private WeightedQuickUnionUF uf;
    
    public Percolation(int n){
        size = n;
        if( n<0){
            throw new IllegalArgumentException();
        }
        numberOfOpenSites = 0;
        isOpen = new boolean[n*n+2];
        isFull = new boolean[n*n+2];
        uf = new WeightedQuickUnionUF(n*n + 2);
        idTopSite =0;
        idBotSite = n*n+1;
        isOpen[idTopSite]=true;
        isOpen[idBotSite]=true;
        
    }
    
    public void open(int row, int col){
        if( row <1 || row > size || col <1 || col> size){
            throw new IllegalArgumentException();
        }
        int idSite = (row-1)*size + col;
        isOpen[idSite]= true;
        numberOfOpenSites +=1;
        if(row==1){
            uf.union(idSite, idTopSite);
            isFull[idSite] = true;
        }
        else if(row==size){
            uf.union(idSite, idBotSite);
        }
        if(col >1){
            if(isOpen[idSite -1]){
                uf.union(idSite,idSite -1);
            }
        }
        if(col < size){
            if(isOpen[idSite +1]){
                uf.union(idSite,idSite +1);
            }
        }
        if(row >1){
            if(isOpen[idSite - size]){
                uf.union(idSite,idSite -size);
            }
        }
        if(row < size){
            if(isOpen[idSite + size]){
                uf.union(idSite,idSite +size);
            }
        }
           
    }
    
    public boolean isOpen(int row, int col){
        if( row <1 || row > size || col <1 || col> size){
            throw new IllegalArgumentException();
        }
        int idSite = (row-1)*size + col;
        return isOpen[idSite];
    }
    
    public boolean isFull(int row, int col){
        if( row <1 || row > size || col <1 || col> size){
            throw new IllegalArgumentException();
        }
        int idSite = (row-1)*size + col;
        // if(uf.connected(idSite, idTopSite)){
        //     isFull[idSite] = true;
        // }
        if(isOpen[idSite]){
            if (col >1){
                if(isFull[idSite-1]){
                    isFull[idSite]= true;
                    return isFull[idSite];
                }
            }
            if(col <size){
                if(isFull[idSite+1]){
                    isFull[idSite]= true;
                    return isFull[idSite];
                }
            }
            if(row >1){
                if(isFull[idSite-size]){
                    isFull[idSite]= true;
                    return isFull[idSite];
                }
            }
            if(row <size){
                if(isFull[idSite+size]){
                    isFull[idSite]= true;
                    return isFull[idSite];
                }
            }
        }
        return isFull[idSite];
    }
    
    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }
    
    public boolean percolates(){
        if(uf.connected(idTopSite,idBotSite)){
            return true;
        }
        return false;
    }
    
    public static void main(String[] args){
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);
        while(!StdIn.isEmpty()){
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            perc.open(row,col);
            StdOut.println(perc.isFull(row,col));
            StdOut.println(perc.numberOfOpenSites());
            StdOut.println(perc.percolates());
            
        }
        
    }
}

