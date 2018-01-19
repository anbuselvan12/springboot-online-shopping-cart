package com.dicka.onlineshopping.springbootcore.repository;

import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountsRepository extends JpaRepository<Accounts, Long>{

    @Query("SELECT a FROM Accounts as a WHERE a.username=?")
    Accounts findByUsername(String username);

}
