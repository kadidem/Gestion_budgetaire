package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
