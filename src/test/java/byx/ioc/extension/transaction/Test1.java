package byx.ioc.extension.transaction;

import byx.ioc.core.Container;
import byx.ioc.extension.transaction.test1.MyService;
import byx.ioc.factory.AnnotationContainerFactory;
import byx.util.jdbc.JdbcUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test1 {
    @Test
    public void test() {
        Container container = new AnnotationContainerFactory(MyService.class).create();

        MyService service = container.getObject(MyService.class);
        JdbcUtils jdbcUtils = container.getObject(JdbcUtils.class);
        assertSame(service.getJdbcUtils(), jdbcUtils);

        service.service1();
        assertEquals(100, jdbcUtils.querySingleValue("select value from A", Integer.class));
        assertEquals(0, jdbcUtils.querySingleValue("select value from B", Integer.class));

        try {
            service.service2();
        } catch (Exception ignored) {}

        assertEquals(90, jdbcUtils.querySingleValue("select value from A", Integer.class));
        assertEquals(0, jdbcUtils.querySingleValue("select value from B", Integer.class));

        jdbcUtils.update("update A set value = 100");
        jdbcUtils.update("update B set value = 0");
    }
}
