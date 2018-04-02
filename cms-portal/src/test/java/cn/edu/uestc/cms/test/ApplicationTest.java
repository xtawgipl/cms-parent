package cn.edu.uestc.cms.test;

import cn.edu.uestc.cms.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    private Logger logger = LoggerFactory.getLogger(ApplicationTest.class);


    @Test
    public void test(){
        logger.info("test....");
    }



}
