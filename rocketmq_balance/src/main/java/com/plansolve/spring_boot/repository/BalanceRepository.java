package com.plansolve.spring_boot.repository;

import com.plansolve.spring_boot.model.database.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Andrew
 * @Date: 2015/6/1
 * @Description:
 **/

public interface BalanceRepository extends JpaRepository<Balance, String> {

    Balance findByIdUser(String idUser);

}
