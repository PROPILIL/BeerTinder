package com.propil.beertinder.domain.logic

import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.apache.tools.ant.types.Assertions
import org.mockito.Mock
import org.mockito.Mockito
import org.testng.AssertJUnit
import org.testng.annotations.Test
import org.testng.asserts.Assertion

class GetBeerListUseCaseTest {

    @Mock
    lateinit var repository: BeerRepository

    private val dataTest = flowOf<List<Beer>>()

    @Test
    fun shouldReturnCorrectData() {

        Mockito.`when`(repository.getBeerList()).thenReturn(dataTest)

        val useCase = GetBeerListUseCase(repository)
        val actual = useCase.invoke()
        val expected = dataTest

        AssertJUnit.assertEquals(expected, actual)
    }
}