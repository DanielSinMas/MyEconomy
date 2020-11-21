package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesUseCase
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseUseCase
import com.danielgimenez.myeconomy.domain.usecase.types.GetTypesUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule{

    @Provides
    fun provideInserExpenseUseCase(repository: ExpenseRepository) = InsertExpenseUseCase(repository)

    @Provides
    fun provideGetExpensesUseCase(repository: ExpenseRepository) = GetExpensesUseCase(repository)

    @Provides
    fun provideGetTypesUseCase(repository: TypeRepository) = GetTypesUseCase(repository)
}