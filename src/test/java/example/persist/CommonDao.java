package example.persist;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import io.github.xinyangpan.persistent.dao.EntityDao;
import io.github.xinyangpan.persistent.dao.impl.DelegateCommonDao;

@Repository
public class CommonDao extends DelegateCommonDao {
	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		this.init(applicationContext.getBeansOfType(EntityDao.class));
	}

}
