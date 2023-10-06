package deu.java002_02.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import deu.java002_02.db.query.QueryUser;
import deu.java002_02.dto.UserDTO;

public final class MariaMain
{
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "testbase";
	private static final String DB_ACCOUNT_ID = "root";
	private static final String DB_ACCOUNT_PW = "1234";

	private static boolean s_m_bLoadedMain = false;
	private static Connection s_m_dbConnection = null;

	// WARNING: 외부에서 호출해서는 안 됩니다.
	// MariaMain.main() 함수는 MariaMain 클래스를 포함한 모듈이 독립 모듈이 되었을 때 java.exe에 의해 호출됩니다.
	public static void main(String[] args)
	{
		if(!main_Maria())
		{
			System.out.println("main() 함수 실행에 실패했습니다.");
			return;
		}

		// TEST: main() 함수의 여기 아래부터는 테스트 코드입니다.
		QueryUser query = new QueryUser();
		UserDTO dto0 = query.byUUID(1);
		UserDTO dto1 = query.byName("mysecondnickname");
		UserDTO dto2 = query.byName("javaProgramming");

		testPrinter(dto0);
		testPrinter(dto1);
		testPrinter(dto2);
	}

	public static boolean main_Maria()
	{
		if(!createConnection())
			return false;
		
		s_m_bLoadedMain = true;
		return true;
	}

	public static Connection getDataBaseConnection()
	{
		if(!s_m_bLoadedMain)
		{
			System.out.println("DataBase가 로드되지 않았습니다. MariaMain.main_Maria() 함수가 호출되었는지 확인하세요.");
			
			/*
			TODO: 새로운 예외를 만들어 throw 해야 합니다.
			InvalidOperationException 또는 그에 준하는 예외를 던져줍니다.
			*/
			// throw new InvalidOperationException();
		}

		return s_m_dbConnection;
	}
	
	public static ResultSet getQueriedResult(String sql)
	{
		if(!s_m_bLoadedMain)
		{
			System.out.println("DataBase가 로드되지 않았습니다. MariaMain.main_Maria() 함수가 호출되었는지 확인하세요.");
		}
		
		try
		{
			return s_m_dbConnection.prepareStatement(sql).executeQuery();
		}
		catch(SQLException sqlEx)
		{
			System.out.println("SQL 실행에 실패했습니다.");
			return null;
		}
	}

	private static boolean createConnection()
	{
		try
		{
			String url = String.format("jdbc:mariadb://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
			s_m_dbConnection = DriverManager.getConnection(url, DB_ACCOUNT_ID, DB_ACCOUNT_PW);
			return true;
		}
		catch(SQLTimeoutException sqltoEx)
		{
			System.out.println("DataBase 연결 시간이 초과되었습니다.");
			return false;
		}
		catch(SQLException sqlEx)
		{
			System.out.println("DataBase에 연결할 수 없습니다. URL 정보가 정확한지 확인하세요.");
			return false;
		}
	}

	// TEST: dto를 DB에서 제대로 얻을 수 있는지 테스트를 위한 testPrinter() 함수입니다.
	private static void testPrinter(UserDTO dto)
	{
		if(dto.isValidDTO)
			System.out.println(String.format("사용자 %s의 uuid는 %d이고, 블랙리스트 여부는 %s입니다.", dto.name, dto.uuid, dto.isBlacked));
		else
			System.out.println("쿼리에 실패했습니다.");
	}
}