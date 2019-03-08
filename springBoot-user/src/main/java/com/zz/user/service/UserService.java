package com.zz.user.service;

import com.zz.core.biz.IBaseBiz;
import com.zz.user.domain.User;

/**
 * @author yaozhou.chen
 * @create 2019-01-17 16:04
 */
public interface UserService extends IBaseBiz<User> {

    User save(User user);

}
