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

	// WARNING: �ܺο��� ȣ���ؼ��� �� �˴ϴ�.
	// MariaMain.main() �Լ��� MariaMain Ŭ������ ������ ����� ���� ����� �Ǿ��� �� java.exe�� ���� ȣ��˴ϴ�.
	public static void main(String[] args)
	{
		if(!main_Maria())
		{
			System.out.println("main() �Լ� ���࿡ �����߽��ϴ�.");
			return;
		}

		// TEST: main() �Լ��� ���� �Ʒ����ʹ� �׽�Ʈ �ڵ��Դϴ�.
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
			System.out.println("DataBase�� �ε���� �ʾҽ��ϴ�. MariaMain.main_Maria() �Լ��� ȣ��Ǿ����� Ȯ���ϼ���.");
			
			/*
			TODO: ���ο� ���ܸ� ����� throw �ؾ� �մϴ�.
			InvalidOperationException �Ǵ� �׿� ���ϴ� ���ܸ� �����ݴϴ�.
			*/
			// throw new InvalidOperationException();
		}

		return s_m_dbConnection;
	}
	
	public static ResultSet getQueriedResult(String sql)
	{
		if(!s_m_bLoadedMain)
		{
			System.out.println("DataBase�� �ε���� �ʾҽ��ϴ�. MariaMain.main_Maria() �Լ��� ȣ��Ǿ����� Ȯ���ϼ���.");
		}
		
		try
		{
			return s_m_dbConnection.prepareStatement(sql).executeQuery();
		}
		catch(SQLException sqlEx)
		{
			System.out.println("SQL ���࿡ �����߽��ϴ�.");
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
			System.out.println("DataBase ���� �ð��� �ʰ��Ǿ����ϴ�.");
			return false;
		}
		catch(SQLException sqlEx)
		{
			System.out.println("DataBase�� ������ �� �����ϴ�. URL ������ ��Ȯ���� Ȯ���ϼ���.");
			return false;
		}
	}

	// TEST: dto�� DB���� ����� ���� �� �ִ��� �׽�Ʈ�� ���� testPrinter() �Լ��Դϴ�.
	private static void testPrinter(UserDTO dto)
	{
		if(dto.isValidDTO)
			System.out.println(String.format("����� %s�� uuid�� %d�̰�, ������Ʈ ���δ� %s�Դϴ�.", dto.name, dto.uuid, dto.isBlacked));
		else
			System.out.println("������ �����߽��ϴ�.");
	}
}