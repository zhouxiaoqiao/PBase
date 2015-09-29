package org.snaker.framework.dictionary.dao;

import org.moon.common.db.hibernate.ParentHBDao;
import org.snaker.framework.dictionary.entity.Dictionary;
import org.springframework.stereotype.Component;

/**
 * 配置字典持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class DictionaryDao extends ParentHBDao<Dictionary, Long> {

}
