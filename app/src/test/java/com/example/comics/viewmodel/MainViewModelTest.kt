package com.example.comics.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.comics.CoroutinesTestRule
import com.example.comics.data.models.ComicsModel
import com.example.comics.data.models.DataModel
import com.example.comics.data.models.ResultModel
import com.example.comics.data.models.ThumbnailModel
import com.example.comics.domain.GetComicsUseCase
import com.example.comics.getOrAwaitValueTest
import com.example.comics.ui.MainViewModel
import com.example.comics.core.util.State
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getComicsUseCase: GetComicsUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(getComicsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetch comics should return success`() = runTest {
        // Arrange
        val comicsModel = ComicsModel(
            data = DataModel(
                results = listOf(
                    ResultModel(
                        title = "Title",
                        description = "Description",
                        thumbnail = ThumbnailModel(
                            path = "path",
                            extension = "jpg"
                        )
                    )
                )
            )
        )
        coEvery { getComicsUseCase() } returns Result.success(comicsModel)

        // Act
        viewModel.fetchComics()
        val uiState = viewModel.comics.getOrAwaitValueTest()

        // Assert
        assertTrue(uiState is State.Success)
        assertEquals(uiState.data, comicsModel.data.results)
    }

    @Test
    fun `when fetch comics should return error`() {
        // Arrange
        val errorMessage = "Error"
        coEvery { getComicsUseCase() } returns Result.failure(Throwable(errorMessage))

        // Act
        viewModel.fetchComics()
        val uiState = viewModel.comics.getOrAwaitValueTest()

        // Assert
        assertTrue(uiState is State.Error)
    }

    @Test
    fun `when fetch comics should return loading`() {
        viewModel.fetchComics()
        val uiState = viewModel.comics.getOrAwaitValueTest()

        // Assert
        assertTrue(uiState is State.Loading)
    }
}