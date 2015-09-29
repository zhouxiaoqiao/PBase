package org.snaker.framework.security.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.security.entity.User;
import org.springframework.stereotype.Component;

/**
 * 用户持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class UserDao extends ParentHBDao<User, Long> {

}
