package com.example.comics.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.comics.BuildConfig
import com.example.comics.CoroutinesTestRule
import com.example.comics.data.MainRepository
import com.example.comics.data.MainRepositoryImpl
import com.example.comics.data.external.MainAPI
import com.example.comics.data.models.ComicsModel
import com.example.comics.data.models.DataModel
import com.example.comics.data.models.ResultModel
import com.example.comics.data.models.ThumbnailModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
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
class ComicsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val mainAPI: MainAPI = mockk(relaxed = true)
    private lateinit var repository: MainRepository

    @Before
    fun setup() {
        repository = MainRepositoryImpl(mainAPI)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetch comics fails should throw exception`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { mainAPI.getComics(any(), any(), any()) } throws Exception(errorMessage)

        // Act & Assert
        try {
            repository.getComics()
            fail("Expected an exception to be thrown")
        } catch (e: Exception) {
            assertEquals(errorMessage, e.message)
        }
    }

    @Test
    fun `when fetch comics should call API with correct parameters`() = runTest {
        // Arrange
        val ts = BuildConfig.MARVEL_TS
        val apikey = BuildConfig.MARVEL_API_KEY
        val hash = BuildConfig.MARVEL_HASH
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

        coEvery { mainAPI.getComics(ts, apikey, hash) } returns comicsModel

        // Act
        val result = repository.getComics()

        // Assert
        coVerify { mainAPI.getComics(ts, apikey, hash) }
        assertEquals(comicsModel, result)
    }

    @Test
    fun `when fetch comics returns empty list`() = runTest {
        // Arrange
        val comicsModel = ComicsModel(
            data = DataModel(
                results = emptyList()
            )
        )

        coEvery { mainAPI.getComics(any(), any(), any()) } returns comicsModel

        // Act
        val result = repository.getComics()

        // Assert
        assertTrue(result.data.results.isEmpty())
    }
}