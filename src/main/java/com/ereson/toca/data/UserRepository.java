package com.ereson.toca.data;

import com.ereson.toca.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    //Spring Data JPA会自动实现这个接口
    //接口的命名规则就可以进行对应的实现
    User findByUsername(String username);
}
