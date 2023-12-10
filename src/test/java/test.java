import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.entity.Borrow;
import com.example.javawebstudy.mapper.BorrowMapper;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class test {

    @Test
    void testGetAllBorrowInfo() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
        List<Borrow> allBorrowInfo = borrowMapper.SelectAllBorrowInfo();
        for(Borrow borrow: allBorrowInfo) {
            System.out.println(borrow);
        }
        System.out.println(Integer.toString(borrowMapper.getBorrowQuantity()));
    }

}