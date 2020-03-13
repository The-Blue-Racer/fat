package MyBatis;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

import dao.Mapper;
import models.UserTestModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class mybatistest {


        private static SqlSessionFactory sqlSessionFactory;

        @Before
        public   void setUp() throws Exception {
            try (Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
            try (SqlSession session = sqlSessionFactory.openSession();
                 Connection conn = session.getConnection();
                 Reader reader = Resources.getResourceAsReader("mybatis/test/CreateDB.sql")) {
                ScriptRunner runner = new ScriptRunner(conn);
                runner.setLogWriter(null);
                runner.runScript(reader);
            }
        }

        @Test
        public void shouldGetUsers() {
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                Mapper mapper = sqlSession.getMapper(Mapper.class);
                List<UserTestModel> userTestModels = mapper.getUsers();
                assertEquals("User1", userTestModels.get(0).getName());
                assertEquals("User2", userTestModels.get(1).getName());
            }
    }


    @After
    public   void remove() throws Exception {
        try (Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
        try (SqlSession session = sqlSessionFactory.openSession();
             Connection conn = session.getConnection();
             Reader reader = Resources.getResourceAsReader("mybatis/test/RemoveDB.sql")) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.runScript(reader);
        }
    }


}
