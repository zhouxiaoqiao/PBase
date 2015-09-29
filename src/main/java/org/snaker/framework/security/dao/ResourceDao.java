package org.snaker.framework.security.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.security.entity.Resource;
import org.springframework.stereotype.Component;

/**
 * 资源持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class ResourceDao extends ParentHBDao<Resource, Long> {

}
