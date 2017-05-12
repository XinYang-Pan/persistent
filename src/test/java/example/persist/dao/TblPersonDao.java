package example.persist.dao;

import org.springframework.stereotype.Repository;

import example.persist.po.TblPerson;
import io.github.xinyangpan.persistent.dao.impl.ActiveableTraceableDao;

@Repository
public class TblPersonDao extends ActiveableTraceableDao<TblPerson, Long, Long> {

}