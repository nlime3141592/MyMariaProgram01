package deu.java002_02.db.query;

public class QueriedString
{
	public boolean isValidQuery; // NOTE: 쿼리에 성공했을 때 true.
	public int row;
	public int column;
	public String[] data;

	public QueriedString()
	{
		
	}

	public QueriedString(boolean isValidQuery)
	{
		this.isValidQuery = isValidQuery;
		this.row = 0;
		this.column = 0;
		this.data = null;
	}
}