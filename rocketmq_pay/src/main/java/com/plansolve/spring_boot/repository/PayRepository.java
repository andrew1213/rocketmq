package com.plansolve.spring_boot.repository;

import com.plansolve.spring_boot.model.database.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Andrew
 * @Date: 2018/6/1
 * @Description:
 **/

public interface PayRepository extends JpaRepository<Pay, String> {

    Pay findByIdUser(String idUser);

}
