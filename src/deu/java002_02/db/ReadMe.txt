1. DTO(Data Transfer Object, ������ ���� ��ü)
��Ʈ��ũ ��� ��� �����͸� ��뿡�� ������ �������� ����ϴ� ��ü�Դϴ�.

2. DTO�� Ư¡
- �ν��Ͻ� �ʵ� ������ �����մϴ�. �ٸ� static ������ ������ �� �����ϴ�.
- ���� �ν��Ͻ� �ʵ� ������ private�� ��� �ش� �ʵ� ������ ���� ������ �� �ֵ��� getter/setter �Լ��� �����ϵ��� �մϴ�. �� ���� �ٸ� �Լ��� ���� �������� �ʽ��ϴ�.
- �ݵ�� �ʿ����� ���� ��츦 �����ϰ�� �����ڸ� ������� �ʽ��ϴ�.

3. DTO ��� �� ��� ����
- ���� ���α׷�(�̿���/��� ���α׷�)�� Business Logic ����� DataBase���� ���ϴ� ������ ��� ���� Network Interface ����� ������ ������ ��û �Լ��� ȣ���մϴ�. �� �Լ��� ��ȯ���� DTO�Դϴ�.
- Network Interface ����� Socket�� ���� ������ ���ϴ� ������ ��û�մϴ�.
- ������ ������ ������ ���� ������ Query Ŭ������ �����մϴ�.
- Query Ŭ������ ������ DTO Ŭ������ �����մϴ�.
- ������ ������ DTO Ŭ������ byte[]�� ��ȯ�մϴ�.
- ������ Socket�� ���� ���� ���α׷��� ����� Network Interface ��⿡ byte[]�� �����մϴ�.
- Network Interface ����� byte[]�� DTO Ŭ������ ��ȯ�մϴ�.
- Network Interface ����� ��ȯ�� DTO Ŭ������ Business Logic�� ��ȯ�մϴ�.
- Business Logic�� DTO Ŭ������ ����� ���ϴ� �۾��� ó���� �� �ֽ��ϴ�.

4. ���� ��ȹ
deu.java002_02.dto ��Ű���� ���� Business Logic�� ���ԵǴ� ��Ű���Դϴ�. ���Ŀ� MariaProgram ������Ʈ���� ������ �����Դϴ�.
deu.java002_02.dto ��Ű���� ���� Business Logic ������Ʈ�� ������ ������� ��⿡ ���Ե� �����Դϴ�.

5. ���� ��� ����
- MariaMain Ŭ�������� ȣ��Ʈ ������ �����ϼ���.
- users table�� blacklist��� ���ο� ���� �����߽��ϴ�. ��ɾ�� �Ʒ��� �����ϴ�.
alter table users add column blacklist bool not null default false
- QueryUtility Ŭ������ Query�� �ʿ��� ���� ���� ����� �����ϴ� Ŭ�����Դϴ�.
- ���� ���� ���Ͽ� �����ϰ� ���Ͽ��� ���� ���� �ε��� ����� �� �ֽ��ϴ�. sql ������ ��δ� QueryUtility.SQL_ROOT_PATH ������ Ȯ���ϼ���.
- sqltxt_QueryUser_byName.txt ������ ������ �Ʒ��� �����ϴ�. �ڼ��� ������ UserQuery.byName() �Լ��� �����ϼ���.
select * from users where nickname = '%s'
- sqltxt_QueryUser_byUUID.txt ������ ������ �Ʒ��� �����ϴ�. �ڼ��� ������ UserQuery.byUUID() �Լ��� �����ϼ���.
select * from users where serverid = '%d'
- �� '%s'�� '%d'�� �����ͺ��̽� ���Ǿ ���ԵǾ� �ִ��� �˰� �ʹٸ� QueryUtility.getSqlFormat() �Լ��� �����ϼ���.
- users ���̺��� ��Ÿ�����ʹ� �Ʒ��� �����ϴ�.
+-----------+-------------+------+-----+---------------------+----------------+
| Field     | Type        | Null | Key | Default             | Extra          |
+-----------+-------------+------+-----+---------------------+----------------+
| serverid  | int(11)     | NO   | PRI | NULL                | auto_increment |
| nickname  | varchar(64) | NO   |     | NULL                |                |
| account   | varchar(64) | NO   | UNI | NULL                |                |
| password  | varchar(64) | NO   |     | NULL                |                |
| regdate   | timestamp   | NO   |     | current_timestamp() |                |
| blacklist | tinyint(1)  | NO   |     | 0                   |                |
+-----------+-------------+------+-----+---------------------+----------------+