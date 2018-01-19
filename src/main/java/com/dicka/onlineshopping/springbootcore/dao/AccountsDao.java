package com.dicka.onlineshopping.springbootcore.dao;

import com.dicka.onlineshopping.springbootcore.entity.Accounts;

import java.util.List;

public interface AccountsDao {

    Accounts findAccountByUsername(String username);

    Accounts createAccounts(Accounts accounts);

    List<Accounts> findAllAccounts();

    Accounts findOneAccountById(Long idaccount);
}
