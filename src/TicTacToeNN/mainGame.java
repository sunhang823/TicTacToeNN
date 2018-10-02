package TicTacToeNN;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*-------------Use this class to play with API----------------------*/

public class mainGame {
	public static void main(String[] args) throws IOException, InterruptedException {
		int n = 12;
		int m = 6;
		
		GameMatrix gm = new GameMatrix();
		gm.initialize(n, m);	
		Minimax mini = new Minimax();
		
		GameMatrix.color=GameMatrix.COLOR_WHITE;
		
		if(GameMatrix.color == GameMatrix.COLOR_WHITE) System.out.println("White chess plays first!");
		else System.out.println("Black chess plays first!");				
		
		Coordinate coordinate = null;				
		httpHelper net = new httpHelper();
		
		while(gm.checkWinner(coordinate).equals(GameMatrix.CONTINUE)){			
			
			getMovesResult moves = net.getMoves(1);
			
			if (moves.code.equals("FAIL")){
				mini.initialize();
				coordinate = mini.findBestMove();
				net.makeMove(coordinate.x + "," + coordinate.y);
				System.out.println("Our move: " + coordinate.x + ", "+ coordinate.y);				
				GameMatrix.times++;
				gm.markPosition(coordinate);				
				gm.changePlayer();
				printGameMatrix(GameMatrix.game_matrix);
				System.out.println("-----------------------Above is "+GameMatrix.times+" steps--------------------------------------------------------------------------------");
				
			}else if (!moves.moves.get(0).teamId.equals("1034")){				
				System.out.println(moves.moves.get(0).teamId);				
				String s = moves.moves.get(0).move;
				int x = Integer.parseInt(s.split(",")[0]);
				int y = Integer.parseInt(s.split(",")[1]);
				coordinate = new Coordinate(x,y);
				System.out.println("Opponent's move: " + coordinate.x + ", "+ coordinate.y);
				GameMatrix.times++;
				gm.markPosition(coordinate);	
				gm.changePlayer();
				printGameMatrix(GameMatrix.game_matrix);
				System.out.println("-----------------------Above is "+GameMatrix.times+" steps--------------------------------------------------------------------------------");
				
				if(!gm.checkWinner(coordinate).equals(GameMatrix.CONTINUE)){
					break;
				}
				mini.initialize();
				coordinate = mini.findBestMove();
				net.makeMove(coordinate.x + "," + coordinate.y);
				System.out.println("Our move: " + coordinate.x + ", "+ coordinate.y);				
				GameMatrix.times++;				
				gm.markPosition(coordinate);	
				gm.changePlayer();
				printGameMatrix(GameMatrix.game_matrix);
				System.out.println("-----------------------Above is "+GameMatrix.times+" steps--------------------------------------------------------------------------------");
				
			}else{
				System.out.println("Opponent hasn't move yet.");
				TimeUnit.SECONDS.sleep(5);
				continue;
			}				
	    }		
		System.out.println(gm.checkWinner(coordinate));		
    }
	
	public static void printGameMatrix(int[][] matrix){
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
