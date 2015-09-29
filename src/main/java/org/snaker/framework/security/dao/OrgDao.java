package org.snaker.framework.security.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.security.entity.Org;
import org.springframework.stereotype.Component;

/**
 * 部门持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class OrgDao extends ParentHBDao<Org, Long> {

}
