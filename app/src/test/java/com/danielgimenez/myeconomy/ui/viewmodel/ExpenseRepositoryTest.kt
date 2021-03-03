package com.danielgimenez.myeconomy.ui.viewmodel

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class ExpenseRepositoryTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var diskDataSource: IDiskDataSource

    @Mock
    lateinit var networkDataSource: INetworkDataSource

    lateinit var expenseRepository: ExpenseRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        expenseRepository = ExpenseRepository(context, diskDataSource, networkDataSource)
    }

    @Test
    fun `GIVEN expense WHEN insertExpense is called THEN success is returned`(){
        runBlockingTest {
            val request = InsertExpenseRequest(Expense(1.2f, "description", 1, LocalDate.now()))
            val response = expenseRepository.saveExpenseLocally(request)
            Assert.assertTrue(response is Response.Success)
        }
    }

}