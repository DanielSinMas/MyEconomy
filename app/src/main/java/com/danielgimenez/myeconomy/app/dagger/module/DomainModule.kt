package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.data.repository.ChartsRepository
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsUseCase
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpenseByDateUseCase
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesUseCase
import com.danielgimenez.myeconomy.domain.usecase.types.GetTypesUseCase
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule{

    @Provides
    fun provideGetExpensesUseCase(repository: ExpenseRepository) = GetExpensesUseCase(repository)

    @Provides
    fun provideGetExpensesByMonthUseCase(repository: ExpenseRepository) = GetExpenseByDateUseCase(repository)

    @Provides
    fun provideGetTypesUseCase(repository: TypeRepository) = GetTypesUseCase(repository)

    @Provides
    fun providesGetChartsUseCase(repository: ChartsRepository) = GetChartsUseCase(repository)

    @Provides
    fun providesInsertTypeUseCase(repository: TypeRepository) = InsertTypeUseCase(repository)
}