package com.example.comics.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.comics.CoroutinesTestRule
import com.example.comics.data.MainRepository
import com.example.comics.data.models.ComicsModel
import com.example.comics.data.models.DataModel
import com.example.comics.data.models.ResultModel
import com.example.comics.data.models.ThumbnailModel
import com.example.comics.domain.GetComicsUseCase
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
class GetComicsUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val repository: MainRepository = mockk(relaxed = true)
    private lateinit var useCase: GetComicsUseCase

    @Before
    fun setup() {
        useCase = GetComicsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when invoke should return comics model`() = runTest {
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

        coEvery { repository.getComics() } returns comicsModel

        // Act
        val result = useCase()

        // Assert
        assertEquals(Result.success(comicsModel), result)
    }

    @Test
    fun `when invoke should return failure`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { repository.getComics() } throws Exception(errorMessage)

        // Act
        val result = useCase()

        // Assert
        assert(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }

    @Test
    fun `when invoke returns empty list`() = runTest {
        // Arrange
        val comicsModel = ComicsModel(
            data = DataModel(
                results = emptyList()
            )
        )

        coEvery { repository.getComics() } returns comicsModel

        // Act
        val result = useCase()

        // Assert
        assert(result.isSuccess)
        assertTrue(result.getOrNull()?.data?.results?.isEmpty() == true)
    }
}