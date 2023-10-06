package deu.java002_02.db.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class QueryUtility
{
	static final String SQL_ROOT_PATH = "C:/Test/";

	static String getSql(String sqlTxtFilePath)
	{
		try
		{
			File sqlTxtFile = new File(sqlTxtFilePath);
			InputStream stream = new FileInputStream(sqlTxtFile);
			byte[] sqlBytes = stream.readAllBytes();
			stream.close();
			
			return new String(sqlBytes);
		}
		catch(FileNotFoundException fnfEx)
		{
			fnfEx.printStackTrace();
			System.out.println("파일을 찾을 수 없습니다. 파일 경로를 확인하세요.");
			return null;
		}
		catch (IOException ioEx)
		{
			ioEx.printStackTrace();
			System.out.println("SQL을 불러오는 데 실패했습니다.");

			return null;
		}
	}

	static String getSqlFormat(String sqlTxtFilePath, Object... arguments)
	{
		return String.format(QueryUtility.getSql(sqlTxtFilePath), arguments);
	}
	
	static QueriedString getQueriedString(ResultSet queriedResult)
	{
		try
		{
			queriedResult.last();
			
			int row = queriedResult.getRow();
			int column = queriedResult.getMetaData().getColumnCount();
			String[] strings = new String[row * column];
			
			int i = -1;
			queriedResult.beforeFirst();
			
			while(queriedResult.next())
			{
				for(int j = 1; j <= column; ++j)
					strings[++i] = queriedResult.getString(j); 
			}

			QueriedString queriedString = new QueriedString(true);
			queriedString.row = row;
			queriedString.column = column;
			queriedString.data = strings;
			return queriedString;
		}
		catch(SQLException sqlEx)
		{
			return new QueriedString(false);
		}
	}
}