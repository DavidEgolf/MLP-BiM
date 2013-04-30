public class Player
{
	boolean 		turn = false;
	Player			enemy;
	PlayingField	field;
	Portrait		picture;
	GameBoard		board = new GameBoard();
	ShipList		list;
	GameBoard		enemyBoard;
	
	Player()
	{
	
	}
	
	
	/* comment block: setters */
	
		public void setEnemy(Player in)
		{
			enemy = in;
			enemyBoard = in.getGameBoard();
			field.setEnemyGameboard(enemyBoard);
		}
	
		public void setPlayingField(PlayingField in)
		{
			field = in;
		}
	
		public void setPortrait(Portrait in)
		{
			picture = in;
		}
	
		public void setGameBoard(GameBoard in)
		{
			board = in;
			field.setGameBoard(in);
		}
	
		public void setShipList(ShipList in)
		{
			list = in;
			field.setShipList(in);
		}
	
	/* setters ended */
	
	/* comment block: getters */
	
		public PlayingField getPlayingField()
		{
			return field;
		}
	
		public Portrait	getPortrait()
		{
			return picture;
		}
	
		public GameBoard getGameBoard()
		{
			return board;
		}
	
		public ShipList getShipList()
		{
			return list;
		}
	
	/* getters ended */
	
	public void printGameBoard()
	{
		board.print(true);
	}
	
	public void setDisabled()
	{
		field.setDisabled();
	}
	
	
}