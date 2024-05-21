package excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/ExcelToDatabase")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class ExcelToDatabase extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // ��ʼ���������� Servlet ��һ�α�����ʱִ��
    @Override
    public void init() throws ServletException {
        try {
            // ���� MySQL JDBC ��������
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ���Խ������ݿ�����
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/duane?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "1234567890")) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");

                // ��ȡ�ϴ����ļ�
                Part filePart = request.getPart("excelFile");
                InputStream fileContent = filePart.getInputStream();

                // ���� Excel �ļ�������ʹ�� Apache POI ��
                try (Workbook workbook = WorkbookFactory.create(fileContent)) {

                    // ��ȡ��һ��������
                    Sheet sheet = workbook.getSheetAt(0);

                    // �����������е�ÿһ�У��ӵڶ��п�ʼ�������һ���Ǳ��⣩
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        int Id = (int) row.getCell(0).getNumericCellValue();
                        String name = row.getCell(1).getStringCellValue();
                        String password = row.getCell(2).getStringCellValue();

                        // �����û����ݵ����ݿ�
                        String sql = "INSERT INTO user (Id, name, password) VALUES (?, ?, ?)";
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            statement.setInt(1, Id);
                            statement.setString(2, name);
                            statement.setString(3, password);
                            statement.executeUpdate();
                        }
                    }

                    // �ϴ��ɹ����ض��򵽳ɹ�ҳ��
                    response.sendRedirect("uploadSuccess.jsp");

                } catch (Exception e) {
                    e.printStackTrace();
                    // �����쳣
                    response.sendRedirect("uploadError.jsp");
                }

            } else {
                System.out.println("Failed to make database connection!");
                // �������ݿ�����ʧ�ܵ����
                response.sendRedirect("databaseError.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // �������ݿ������쳣
            response.sendRedirect("databaseError.jsp");
        }
    }
}
