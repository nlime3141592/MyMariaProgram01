package deu.java002_02.db.query;

import java.sql.ResultSet;

import deu.java002_02.db.MariaMain;
import deu.java002_02.dto.UserDTO;

public class QueryUser extends Query
{
	public UserDTO byUUID(int uuid)
	{
		String sql = QueryUtility.getSqlFormat(QueryUtility.SQL_ROOT_PATH + "sqltxt_QueryUser_byUUID.txt", uuid);
		return getUserDTO(sql);
	}

	public UserDTO byName(String name)
	{
		String sql = QueryUtility.getSqlFormat(QueryUtility.SQL_ROOT_PATH + "sqltxt_QueryUser_byName.txt", name);
		return getUserDTO(sql);
	}

	private UserDTO getUserDTO(String sql)
	{
		ResultSet queriedResult = MariaMain.getQueriedResult(sql);
		QueriedString qstr = QueryUtility.getQueriedString(queriedResult);

		UserDTO dto = new UserDTO();

		// NOTE: ����� ������ �� �ϳ��� ��(�� �� ���� �����)���� ������ �� ���� ���� �ùٸ� �������� ����Ǿ����� �ǹ��մϴ�.
		if(qstr.row == 1)
		{
			dto.isValidDTO = true;
			dto.uuid = Integer.parseInt(qstr.data[0]);
			dto.name = qstr.data[1];
			dto.accountId = qstr.data[2];
			dto.accountPw = qstr.data[3];
			dto.regdata = qstr.data[4];
			dto.isBlacked = Integer.parseInt(qstr.data[5]) != 0;
		}
		else
		{
			dto.isValidDTO = false;
			dto.uuid = -1;
			dto.name = "";
			dto.accountId = "";
			dto.accountPw = "";
			dto.regdata = "";
			dto.isBlacked = false;
		}

		return dto;
	}
}