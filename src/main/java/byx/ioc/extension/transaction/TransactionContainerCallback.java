package byx.ioc.extension.transaction;

import byx.ioc.core.Container;
import byx.ioc.core.ContainerCallback;
import byx.ioc.core.Dependency;
import byx.ioc.core.ObjectDefinition;
import byx.util.jdbc.JdbcUtils;

import javax.sql.DataSource;

/**
 * Container回调器：向容器中注册一个JdbcUtils
 *
 * @author byx
 */
public class TransactionContainerCallback implements ContainerCallback {
    @Override
    public void afterContainerInit(Container container) {
        // 向容器中注册一个JdbcUtils
        container.registerObject("jdbcUtils", new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return JdbcUtils.class;
            }

            @Override
            public Dependency[] getInstanceDependencies() {
                return new Dependency[]{Dependency.type(DataSource.class)};
            }

            @Override
            public Object getInstance(Object[] params) {
                return new JdbcUtils((DataSource) params[0]);
            }
        });
    }
}
