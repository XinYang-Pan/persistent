package io.github.xinyangpan.persistent.dao.po.traceable;

import java.util.Date;

public interface UserIdAndTimeGetter<U> {
	
    U getUserId();
    
    Date currentTime();

}
