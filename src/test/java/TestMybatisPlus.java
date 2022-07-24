import com.xxx.securityjwt.model.dao.UserMapper;
import com.xxx.securityjwt.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestMybatisPlus {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void OneTest(){
        User user = userMapper.selectById(2);
        System.out.println(user);
    }
}
