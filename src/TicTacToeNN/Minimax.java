package TicTacToeNN;

import java.util.ArrayList;

public class Minimax extends Solution{
	
	public static int player;
	public static int opponent;
	public static int vacant;
	public static double winValue;
	private static final int POSITION_NUMBER = GameMatrix.rows*GameMatrix.cols;
	private static final int GAMESIZE = GameMatrix.rows;
	private static final int ORDER = GameMatrix.consecutive_order;
	private static int wantDepth = 3;
	private static double time;
/*-----------initialize function-------------
 *   Use this function to decide who is playing now. Define the max value of win and the min value of lose.
 */
	public void initialize(){
		player=GameMatrix.color;
		if(player==GameMatrix.COLOR_WHITE) opponent = GameMatrix.COLOR_BLACK;
		else opponent = GameMatrix.COLOR_WHITE;
		vacant = GameMatrix.NO_COLOR;
		winValue=Math.pow(10, 2*ORDER);
	}
/*
 * ------------evaluate function---------
 * To evaluate each board's value.
 */
	public double evaluate(int[][] board){
		double result=0;		
		for(int i=0;i<GAMESIZE;i++){
			for(int j=0;j<GAMESIZE;j++){
				double horizontalTemp = horizontalM(board,i,j);
				double verticalTemp = verticalM(board,i,j);
				double diagonalLeftTemp = diagonalLeftUpM(board,i,j);
				double diagonalRightTemp = diagonalRightUpM(board,i,j);
				if(Math.abs(horizontalTemp)==winValue) return horizontalTemp;
				if(Math.abs(verticalTemp)==winValue) return verticalTemp;
				if(Math.abs(diagonalLeftTemp)==winValue) return diagonalLeftTemp;
				if(Math.abs(diagonalRightTemp)==winValue) return diagonalRightTemp;
				result+=horizontalTemp;
				result+=verticalTemp;
				result+=diagonalLeftTemp;
				result+=diagonalRightTemp;
			}			
		}

		return result;			
	}
	
	private double verticalM(int[][] board, int coordinateX, int coordinateY) {
		int[] arr = new int[ORDER];
		for(int index=0;index<ORDER;index++){
			if(coordinateX+index>=GAMESIZE) return 0;
			arr[index]=board[coordinateX+index][coordinateY];
		}
		if(checkElements(arr,opponent)) return -valueFunc(arr);
		else return valueFunc(arr);
	}

	private double horizontalM(int[][] board, int coordinateX, int coordinateY) {
		int[] arr = new int[ORDER];
		for(int index=0;index<ORDER;index++){
			if(coordinateY+index>=GAMESIZE) return 0;
			arr[index]=board[coordinateX][coordinateY+index];
		}
		if(checkElements(arr,opponent)) return -valueFunc(arr);
		else return valueFunc(arr);
	}
	
	private double diagonalLeftUpM(int[][] board, int i, int j){
		int[] arr = new int[ORDER];
		for(int index=0;index<ORDER;index++){
			if(i+index>=GAMESIZE||j+index>=GAMESIZE) return 0;
			arr[index]=board[i+index][j+index];
		}
		if(checkElements(arr,opponent)) return -valueFunc(arr);
		else return valueFunc(arr);
	}
	
	private double diagonalRightUpM(int[][] board, int i, int j){
		int[] arr = new int[ORDER];
		for(int index=0;index<ORDER;index++){
			if(i+index>=GAMESIZE||j-index<0) return 0;
			arr[index]=board[i+index][j-index];
		}
		if(checkElements(arr,opponent)) return -valueFunc(arr);
		else return valueFunc(arr);
	}
	/*-------- Calculate each window's value:
	 * e.g. Gomoku(consecutive_order=5). {o,o,o,_,o} = 10*10*10+10+10*10*10*10=11010, {o,o,_,o,o} = 10*10+10*10+10*10*10*10=10200, {o,o,o,_,_}=10*10*10=1000
	 * 
	 * --------Which can be changed-----------------*/
	private double valueFunc(int[] arr) {		
		double result=1;
		double sumValue=0;
		if(checkElements(arr,player)&&checkElements(arr,opponent)) return 0;
		if(!checkElements(arr,vacant)) return winValue;
		else{
			int length = arr.length;
			for(int i=0;i<length;i++){
				int sum = 0;
				if(arr[i]==vacant) continue;
				else sum=sum+10;
				for(int j=i+1;j<length;j++){
					if(j==length-1) i=length;
					if(arr[j]!=vacant) sum*=10;
					else{
						i=j;
						break;
					}
				}
				sumValue+=sum;
				result*=sum;
			}
		}
		result+=sumValue;
		return result*result;
	}
/*-----------------------------------------------------------Above is evaluate function------------------------------------------------------*/	
	public double minimax(int depth,boolean isMax, int[][] board, double alpha, double beta, int wantDepth){
		double score = evaluate(board)-depth;
		if(depth>wantDepth) return score;
		if(score==winValue-depth) return score;
		if(score==-winValue-depth) return score;
		if(Continue()==false) return 0;
		if (isMax){
	        double best = -winValue;
	        ArrayList<Coordinate> tempArr = actions(board,1);
	        for(Coordinate move : tempArr) {
	        	int i = move.x; int j = move.y;
	        	if (GameMatrix.game_matrix[i][j]==0){
                    GameMatrix.game_matrix[i][j] = player;
                    GameMatrix.times++;
                    best = Math.max(best,minimax(depth+1, !isMax, GameMatrix.game_matrix, alpha, beta, wantDepth));
                    alpha = Math.max(alpha, best);
                    GameMatrix.game_matrix[i][j] = 0;
                    GameMatrix.times--;
                    if(beta<=alpha) break;
                }
	        }
	        return best;
	    }		
	    else{
	        double best = winValue;
	        ArrayList<Coordinate> tempArr = actions(board,1);
	        for(Coordinate move : tempArr) {
	        	int i = move.x; int j = move.y;
	        	if (GameMatrix.game_matrix[i][j]==0){
                    GameMatrix.game_matrix[i][j] = opponent;
                    GameMatrix.times++;
                    best = Math.min(best,minimax(depth+1, !isMax, GameMatrix.game_matrix, alpha, beta, wantDepth));
                    GameMatrix.game_matrix[i][j] = 0;
                    GameMatrix.times--;
                    beta = Math.min(best, beta);
                    if(beta<=alpha) break;
                }
	        }
			return best;
	    }
	}
		
	public Coordinate findBestMove(){
		double startTime = System.currentTimeMillis();
		 double bestVal = -winValue*10;
		 Coordinate bestMove = new Coordinate(-1,-1);
		 int num = 2;
		 ArrayList<Coordinate> arr = actions(GameMatrix.game_matrix, num);
		 if(arr.size()>70) num = 1;
		 arr = actions(GameMatrix.game_matrix, num);
		 System.out.println("Coordinate num: "+ arr.size());
		 if(arr.size()==0) bestMove = new Coordinate(GAMESIZE/2,GAMESIZE/2);
		 double alpha = -winValue*10;
		 double beta = winValue*10;
		 for(Coordinate move:arr){
			 int i=move.x; int j=move.y;
			 if (GameMatrix.game_matrix[i][j]==0){
				 GameMatrix.game_matrix[i][j] = player;
				 GameMatrix.times++;
				 double moveVal = minimax(0, false, GameMatrix.game_matrix,alpha,beta, Math.max(wantDepth,2));
				 GameMatrix.game_matrix[i][j] = 0;
				 GameMatrix.times--;
				 if (moveVal > bestVal){
					 bestMove.x = i;
					 bestMove.y = j;
					 bestVal = moveVal;
				 }
			 }			 
		 }
		 double endTime = System.currentTimeMillis();
		 time = (endTime - startTime)/60000;
		 System.out.println("This steps cost: "+ time+" minutes.");
		 if(time>1.3) wantDepth--;
		 else if(time<0.05) wantDepth++;
		 return bestMove;
	}

/*-----------------------------Decide if there is still vacant position on board--------------------------*/	
	public boolean Continue() {
		if(GameMatrix.times<POSITION_NUMBER) return true;
		else return false;			
	}
/*-----------------Check if an array has given color-----------------*/
	private boolean checkElements(int[] arr, int color){
		int length = arr.length;
		for(int i=0;i<length;i++){
			if(arr[i]==color) return true;
		}
		return false;
	}
	/*
	 * ---------------------------Choose coordinates function----------------------
	 * This function is used to choose coordinates to avoid whole board search. We simply use coordinates that around positions that already been occupied.
	 */
	private ArrayList<Coordinate> actions(int[][] state, int num){
		ArrayList<Coordinate> moves = new ArrayList<>();
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				if(state[i][j] != 0) continue;
				else{
					int upx = Math.max(i-num, 0);
					int downx = Math.min(i+num, GAMESIZE-1);
					int lefty = Math.max(j-num, 0);
					int righty = Math.min(j+num, GAMESIZE-1);
					
					int add = 0;
					for(int m = upx; m <= downx; m++){
						for(int n = lefty; n <= righty; n++){
							add += state[m][n];
						}
					}
					if(add > 0){
						moves.add(new Coordinate(i,j));
					}
					
				}				
			}
		}
		
		return moves;
	}

}
