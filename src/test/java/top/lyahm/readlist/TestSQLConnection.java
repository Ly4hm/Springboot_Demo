package top.lyahm.readlist;


import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations="classpath:top/lyahm/readlist/utils/Db.properties")
public class TestSQLConnection {
    @Resource
    private DataSource dataSource;

    @Test
    public void TestSQLConnection(){
        assertNotNull(dataSource);

        try(Connection connection = dataSource.getConnection()){
            assertNotNull(connection);
            //连接成功
            System.out.println("数据库连接成功");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！");
        }
    }
}
