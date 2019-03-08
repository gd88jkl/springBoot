package com.zz.user.service.impl;

import com.zz.core.biz.impl.BaseBizImpl;
import com.zz.core.persistence.BaseJpaRepository;
import com.zz.user.dao.UserRepository;
import com.zz.user.domain.User;
import com.zz.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yaozhou.chen
 * @create 2019-01-17 16:04
 */
@Service

public class UserServiceImpl extends BaseBizImpl<User, BaseJpaRepository<User, String>> implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        String id = user.getId();
        if(id == null || id.isEmpty()) {
            user.setCreateTime(new Date());
        } else {
            User _user = findById(id);
            if(_user == null) {
                user.setCreateTime(new Date());
            } else {
                user.setCreateTime(_user.getCreateTime());
                BeanUtils.copyProperties(user,_user);
                return userRepository.save(_user);
            }
        }
        return userRepository.save(user);
    }


}