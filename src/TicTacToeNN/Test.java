package TicTacToeNN;


/*-------------------------Use this class to test with player or computer itself-------------------------------*/

public class Test {

	public static void main(String[] args){
		int n = 12;
		int m = 6;	
		GameMatrix gm = new GameMatrix();
		Solution a = new Solution();
		double startTime = System.currentTimeMillis();
		gm.initialize(n, m);				
		System.out.println(a.TicTacToe());
		System.out.println("This game has already processed "+GameMatrix.times+" times.");
		double endTime = System.currentTimeMillis();
		double time = (endTime-startTime)/60000;
		System.out.println("This game costs: "+time+" minutes");

		
		
	}
}
