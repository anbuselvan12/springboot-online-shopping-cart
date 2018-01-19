package com.dicka.onlineshopping.springbootcore.dao.impl;

import com.dicka.onlineshopping.springbootcore.dao.AccountsDao;
import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import com.dicka.onlineshopping.springbootcore.entity.Roles;
import com.dicka.onlineshopping.springbootcore.repository.AccountsRepository;
import com.dicka.onlineshopping.springbootcore.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
@Service
public class AccountsDaoImpl implements AccountsDao{

    private final AccountsRepository accountsRepository;
    private final RolesRepository rolesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountsDaoImpl(AccountsRepository accountsRepository, RolesRepository rolesRepository){
        this.accountsRepository=accountsRepository;
        this.rolesRepository=rolesRepository;
    }

    @Override
    public Accounts findAccountByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }

    @Override
    public Accounts createAccounts(Accounts accounts) {

            accounts.setPassword(bCryptPasswordEncoder.encode(accounts.getPassword()));
            accounts.setActive(1);
            Roles roles = rolesRepository.findOne(2L);
            List<Roles> rolesList = new ArrayList<>();
            rolesList.add(roles);
            accounts.setRolesList(rolesList);
            return accountsRepository.save(accounts);
    }

    @Override
    public List<Accounts> findAllAccounts() {
        return accountsRepository.findAll();
    }

    @Override
    public Accounts findOneAccountById(Long idaccount) {
        return accountsRepository.findOne(idaccount);
    }
}
