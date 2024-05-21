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
    // 初始化方法，在 Servlet 第一次被调用时执行
    @Override
    public void init() throws ServletException {
        try {
            // 加载 MySQL JDBC 驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 尝试建立数据库连接
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/duane?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "1234567890")) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");

                // 获取上传的文件
                Part filePart = request.getPart("excelFile");
                InputStream fileContent = filePart.getInputStream();

                // 处理 Excel 文件，这里使用 Apache POI 库
                try (Workbook workbook = WorkbookFactory.create(fileContent)) {

                    // 获取第一个工作表
                    Sheet sheet = workbook.getSheetAt(0);

                    // 遍历工作表中的每一行，从第二行开始（假设第一行是标题）
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        int Id = (int) row.getCell(0).getNumericCellValue();
                        String name = row.getCell(1).getStringCellValue();
                        String password = row.getCell(2).getStringCellValue();

                        // 插入用户数据到数据库
                        String sql = "INSERT INTO user (Id, name, password) VALUES (?, ?, ?)";
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            statement.setInt(1, Id);
                            statement.setString(2, name);
                            statement.setString(3, password);
                            statement.executeUpdate();
                        }
                    }

                    // 上传成功后重定向到成功页面
                    response.sendRedirect("uploadSuccess.jsp");

                } catch (Exception e) {
                    e.printStackTrace();
                    // 处理异常
                    response.sendRedirect("uploadError.jsp");
                }

            } else {
                System.out.println("Failed to make database connection!");
                // 处理数据库连接失败的情况
                response.sendRedirect("databaseError.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理数据库连接异常
            response.sendRedirect("databaseError.jsp");
        }
    }
}
