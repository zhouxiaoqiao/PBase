package org.snaker.framework.security.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.security.entity.Authority;
import org.springframework.stereotype.Component;

/**
 * 权限持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class AuthorityDao extends ParentHBDao<Authority, Long> {

}
