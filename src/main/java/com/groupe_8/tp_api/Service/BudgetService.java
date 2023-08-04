package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;


}
