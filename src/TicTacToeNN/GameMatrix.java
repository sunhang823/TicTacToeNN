package TicTacToeNN;

class Coordinate{
	int x;int y;
	Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
}

public class GameMatrix {
	
	public static final int COLOR_WHITE = 1;
	public static final int COLOR_BLACK = 2;
	public static final int NO_COLOR = 0;
	public static final String WHITE_WIN = "White win";
	public static final String BLACK_WIN = "Black win";
	public static final String TIE = "Tie";
	public static final String CONTINUE = "Continue";
	
	public static int color;
	public static int rows;
	public static int cols;	
	public static int consecutive_order;
	public static int[][] game_matrix;
	public static int times = 0;

	/*-----------------------------------------------------------
	 * The following functions are GameMatrix self-contained functions, which include to initialize game rules, to check who wins,
	 * to change player,to decide if this game is over.
	 * -----------------------------------------*/
	public void initialize(int n, int m){
		game_matrix = new int[n][n];
		rows = n;
		cols = n;
		consecutive_order = m;
	}
	public void changePlayer(){
		if(GameMatrix.color==GameMatrix.COLOR_BLACK) GameMatrix.color=GameMatrix.COLOR_WHITE;
		else GameMatrix.color=GameMatrix.COLOR_BLACK;
	}
	public void markPosition(Coordinate coordinate){
		game_matrix[coordinate.x][coordinate.y] = GameMatrix.color;
	}	
	public String checkWinner(Coordinate coordinate){
		if(coordinate==null) return GameMatrix.CONTINUE;
		int res = verticalCheck(coordinate)+horizontalCheck(coordinate)+diagonalCheck(coordinate);
		if(res==2) return GameMatrix.BLACK_WIN;
		if(res==1) return GameMatrix.WHITE_WIN;		
		if(Continue()) return GameMatrix.CONTINUE;
        else return GameMatrix.TIE;
	}	
	public int verticalCheck(Coordinate coordinate){
		int count = 1;
		int min = Math.min(coordinate.x+GameMatrix.consecutive_order,GameMatrix.rows);
		int max = Math.max(coordinate.x-GameMatrix.consecutive_order+1, 0);
        for(int i = coordinate.x + 1; i < min; i++)  
        {  
            if(GameMatrix.game_matrix[i][coordinate.y] != GameMatrix.NO_COLOR)  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][coordinate.y]%2))  
                {  
                    count++;  
                }else break;  
            }else break;  
        }  
        for(int i = coordinate.x-1; i >=max; i--)  
        {  
            if(GameMatrix.game_matrix[i][coordinate.y] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][coordinate.y]%2))  
                    count++;  
                else break;  
            }else break;  
        }
        
        if(count >= GameMatrix.consecutive_order)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_WHITE)  return 1;
            else if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_BLACK) return 2;
        }
        return 0;
	}
	public int horizontalCheck(Coordinate coordinate){
		int count = 1;
        int min = Math.min(coordinate.y+GameMatrix.consecutive_order,GameMatrix.cols);
		int max = Math.max(coordinate.y-GameMatrix.consecutive_order+1, 0);
        for(int i = coordinate.y+1; i < min; i++)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][i] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[coordinate.x][i])%2)  
                    count++;  
                else break;  
            }else break;  
        }  
        for(int i = coordinate.y-1; i >= max; i--)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][i] != GameMatrix.NO_COLOR )  
            {  
                if(GameMatrix.game_matrix[coordinate.x][coordinate.y]%2 == GameMatrix.game_matrix[coordinate.x][i]%2)  
                    count++;  
                else break;  
            }else break;  
        }  
        if(count >= GameMatrix.consecutive_order)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_WHITE) return 1; 
            else if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_BLACK)return 2;
        }
        return 0;
	}
	public int diagonalCheck(Coordinate coordinate) {
        
        // top left to bottom right check
        int count = 1;    
        for(int i = coordinate.x - 1, j = coordinate.y - 1; i >= 0 && j >= 0; i--, j--)  
        {  
            if(GameMatrix.game_matrix[i][j] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][j]%2))  
                    count++;  
                else break;  
            }else break;  
        }  
        for(int i = coordinate.x + 1, j = coordinate.y + 1; i<GameMatrix.cols && j < GameMatrix.rows; i++, j++)  
        {  
            if(GameMatrix.game_matrix[i][j] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][j]%2))  
                    count++;  
                else break;  
            }else break;  
        }  
        if(count>=GameMatrix.consecutive_order)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_WHITE)  return 1; 
            else if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_BLACK)  return 2;    
        }
        
     // bottom left to top right check
        count = 1;  
        for(int i = coordinate.x - 1, j = coordinate.y + 1; i >= 0 && j < GameMatrix.rows; i--, j++)  
        {  
            if(GameMatrix.game_matrix[i][j] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][j]%2))  
                    count++;  
                else break;  
            }else break;  
        }  
        for(int i = coordinate.x + 1, j = coordinate.y - 1; i < GameMatrix.cols && j >= 0; i++, j--)  
        {  
            if(GameMatrix.game_matrix[i][j] != GameMatrix.NO_COLOR )  
            {  
                if((GameMatrix.game_matrix[coordinate.x][coordinate.y]%2) == (GameMatrix.game_matrix[i][j]%2))  
                    count++;  
                else break;  
            }else break;  
        }  
        if(count>=GameMatrix.consecutive_order)  
        {  
            if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_WHITE)  return 1; 
            else if(GameMatrix.game_matrix[coordinate.x][coordinate.y] == GameMatrix.COLOR_BLACK)  return 2;    
        }  
    	return 0;
	}
	public boolean Continue() {
		if(GameMatrix.times<rows*cols) return true;
		else return false;
	}
	
}
