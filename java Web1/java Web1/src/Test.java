import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1,��������
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2���û���Ϣ��URL
        String url="jdbc:mysql://localhost:3306/123?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String username="root";
        String password="yy20021220";

        //3�����ӳɹ������ݿ����  Connection�������ݿ�
        Connection connection = DriverManager.getConnection(url, username, password);

        //4��ִ��SQL����  statement ִ��sql����
        Statement statement = connection.createStatement();

        //5��ִ��SQL���� ȥ ִ��SQL�����ܴ��ڽ�����鿴���ؽ��
        String sql="SELECT * FROM user";
        ResultSet resultSet = statement.executeQuery(sql);//���ؽ����,�����װ������ȫ����ѯ�Ľ��

        while (resultSet.next()){
            System.out.println("id="+resultSet.getObject("id"));
            System.out.println("name="+resultSet.getObject("name"));
            System.out.println("password="+resultSet.getObject("password"));
            System.out.println("--------------");
        }

        //6���ͷ�����

        resultSet.close();
        statement.close();
        connection.close();
    }

}