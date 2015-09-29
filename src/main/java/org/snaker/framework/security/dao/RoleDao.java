package org.snaker.framework.security.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.security.entity.Role;
import org.springframework.stereotype.Component;

/**
 * 角色持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class RoleDao extends ParentHBDao<Role, Long> {

}
