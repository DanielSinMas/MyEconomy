package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule{

    @Provides
    fun provideInserExpenseUseCase(repository: ExpenseRepository) = InsertExpenseUseCase(repository)
}