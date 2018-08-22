/******************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {
    private int[] blocks;
    private int dimension, dimension1d;



  public Board(int[][] blocks){

      dimension = blocks.length;
      dimension1d = dimension*dimension;
      this.blocks = new int[dimension1d];
      for(int i=0; i<dimension; i++){
          for(int j =0; j<dimension; j++){
              this.blocks[i*dimension +j] = blocks[i][j];
          }
      }

  }

  public int dimension(){
      return dimension;
  }

  public int hamming(){
      int hamming =0;
      for(int i =0; i<dimension1d; i++){
        if(blocks[i]!=(i+1)&& blocks[i]!=0){
            hamming += 1;
        }
      }
      return hamming;
  }

  public int manhattan(){
      int manhattan =0;

      for(int i=0; i<dimension1d; i++){

          if(blocks[i]!=(i+1)&& blocks[i]!=0){
              int rowBlock = i/dimension;
              int colBlock = i%dimension;
              int rowPos = (blocks[i]-1)/dimension;
              int colPos = (blocks[i]-1)%dimension;
              manhattan += Math.abs(rowBlock-rowPos) + Math.abs(colBlock-colPos);
                // int dRow = Math.abs((i+1-blocks[i])/dimension);
                // int dCol = Math.abs((i+1-blocks[i])%dimension);
                // manhattan += dCol+ dRow;
          }
      }
      return manhattan;
  }

  public boolean isGoal(){
      for(int i =0; i<dimension1d;i++){
          if(blocks[i]!=(i+1)&& blocks[i]!=0){
              return false;
          }
      }
      return true;
  }

  public Board twin(){
      // int[] copyBlocks = new int[dimension1d];
      // for(int i =0; i<dimension1d; i++){
      //         copyBlocks[i]=blocks[i];
      // }
      // for (int i =0; i<dimension1d; i++){
      //     if(copyBlocks[i]!=0 && copyBlocks[i+1]!=0&& (i+1)%dimension!=0){
      //         int swap = copyBlocks[i];
      //         copyBlocks[i]= copyBlocks[i+1];
      //         copyBlocks[i+1] = swap;
      //         break;
      //     }
      // }
      // int[][] copy2d = new int[dimension][dimension];
      // for(int i =0; i<dimension; i++){
      //     for(int j =0; j<dimension; j++){
      //         copy2d[i][j] = copyBlocks[i*dimension+j];
      //     }
      // }
      // return new Board(copy2d);

      Board copyBoard = new Board(new int[dimension][dimension]);
      for(int i =0; i<dimension1d; i++){
              copyBoard.blocks[i]=blocks[i];
      }
      for (int i =0; i<dimension1d; i++){
          if(copyBoard.blocks[i]!=0 && copyBoard.blocks[i+1]!=0&& (i+1)%dimension!=0){
              int swap = copyBoard.blocks[i];
              copyBoard.blocks[i]= copyBoard.blocks[i+1];
              copyBoard.blocks[i+1] = swap;
              break;
          }
      }
      return copyBoard;

  }

  public boolean equals(Object y){
      if(y == null) return false;
      if(y.getClass() != this.getClass()) return false;
      Board that = (Board) y;
      if(that.dimension()!= this.dimension) {
          return false;
      }
      else {
          for(int i =0; i<dimension1d; i++)
          {
              if(this.blocks[i]!=that.blocks[i]){
                  return false;
              }
          }
          return true;
      }
  }

  private Board swapBoard(int pos1, int pos2){
      Board copyBoard = new Board(new int[dimension][dimension]);
      for(int i =0; i<dimension1d; i++){
          copyBoard.blocks[i]=blocks[i];
      }
      int tempPos;
      tempPos =copyBoard.blocks[pos1] ;
      copyBoard.blocks[pos1] = copyBoard.blocks[pos2];
      copyBoard.blocks[pos2] = tempPos;
      return copyBoard;
  }

  public Iterable<Board> neighbors(){
      Queue<Board> neighborsQueue =new Queue<Board>();

      for(int i =0; i<dimension1d; i++){
          if(blocks[i]== 0){
              if(i% dimension>0){
                  neighborsQueue.enqueue(swapBoard(i, i-1));

              }
              if(i% dimension < dimension -1){
                  neighborsQueue.enqueue(swapBoard(i, i+1));

              }
              if(i/dimension >0){
                  neighborsQueue.enqueue(swapBoard(i,i-dimension));

              }
              if(i/dimension <dimension-1){
                  neighborsQueue.enqueue(swapBoard(i,i+dimension));

              }
          }
      }
      return neighborsQueue;
  }

  public String toString(){
      StringBuilder s = new StringBuilder();
      s.append(dimension + "\n");
      for (int i = 0; i < dimension; i++) {
          for (int j = 0; j < dimension; j++) {
              s.append(String.format("%2d ", blocks[i*dimension +j]));
          }
          s.append("\n");
      }
      return s.toString();
  }


}
