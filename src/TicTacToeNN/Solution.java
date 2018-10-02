package TicTacToeNN;

import java.util.Scanner;

public class Solution {
	
	public String TicTacToe(){
		GameMatrix gm = new GameMatrix();
		Minimax mini = new Minimax();
		GameMatrix.color=GameMatrix.COLOR_WHITE;
		if(GameMatrix.color == GameMatrix.COLOR_WHITE) System.out.println("White chess plays first!");
		else System.out.println("Black chess plays first!");				
		Coordinate coordinate = null;		
		
//		printGameMatrix(GameMatrix.game_matrix);
//		System.out.println("---------------------------------------");
//		Scanner start = new Scanner(System.in);
//		System.out.println("Computer first input 1, you play first input 2.");
//		int color = start.nextInt();
		while(gm.checkWinner(coordinate).equals(GameMatrix.CONTINUE)){
			if(GameMatrix.color==GameMatrix.COLOR_WHITE){
				mini.initialize();
				coordinate = mini.findBestMove();
				GameMatrix.times++;
			}
			else{
				System.out.println("Input your position£º");
				Scanner sc = new Scanner(System.in);
				int x = sc.nextInt();
				int y = sc.nextInt();
				coordinate = new Coordinate(x,y);
				GameMatrix.times++;
			}			
			
//			mini.initialize();
//			coordinate = mini.findBestMove();
//			GameMatrix.times++;
			
			
			gm.markPosition(coordinate);	
			printGameMatrix(GameMatrix.game_matrix);
			System.out.println("Where to put: "+coordinate.x+", "+coordinate.y);
			System.out.println("-----------------------Above is "+GameMatrix.times+" steps--------------------------------------------------------------------------------");
			gm.changePlayer();
		}	
		return gm.checkWinner(coordinate);
	}
/*--------------------------------------------------------------This is the separate line-----------------------------------------------------------------------------*/
	public void printGameMatrix(int[][] matrix){
		System.out.print("   0 ");
		for(int i=1;i<matrix.length;i++){
			if(i<9) System.out.print(i+" ");
			else System.out.print(i);
		} 
		System.out.println("");
		int height = matrix.length;int width = matrix[0].length;
		for(int i=0;i<height;i++){
			if(i<10) System.out.print(i+"  ");
			else System.out.print(i+" ");
			
			for(int j=0;j<width;j++){
				if(matrix[i][j]==1) System.out.print("o ");
				else if(matrix[i][j]==2) System.out.print("¡ñ ");
				else System.out.print("- ");
			}				
			System.out.println();	
		}
	}
}