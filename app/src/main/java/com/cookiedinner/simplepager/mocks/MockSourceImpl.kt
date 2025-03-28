package com.cookiedinner.simplepager.mocks

import android.util.Log
import com.cookiedinner.simplepager.utils.subListSafe
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class MockSourceImpl : MockSource {
    private val mockDelay = 100.milliseconds

    override suspend fun getMocks(dirtyList: Boolean, page: Int, pageSize: Int): List<Mock> {
        delay(mockDelay)
        Log.d("Tests", "Returning page: $page for pageSize: $pageSize")
        return (when (page) {
            in 0..2 -> emptyList()
            in 3..5 -> listOf(
                Mock(id = 90, name = "Houston Sandoval", value = 5.4),
                Mock(id = 91, name = "Casey Osborne", value = 6.4),
                Mock(id = 92, name = "Christopher Guthrie", value = 78.5)
            )
            else -> listOf(
                Mock(id = 90, name = "Houston Sandoval", value = 5.4),
                Mock(id = 91, name = "Casey Osborne", value = 6.4),
                Mock(id = 92, name = "Christopher Guthrie", value = 78.5),
                Mock(id = 93, name = "Dan Norton", value = 116.117),
                Mock(id = 94, name = "Amalia Morse", value = 120.121),
                Mock(id = 95, name = "Thelma McCullough", value = 124.125),
            )
        } + if (dirtyList) mocksList else distinctMocksList).subListSafe((page) * pageSize, (page) * pageSize + pageSize)
    }

    companion object {
        val mocksList = listOf(
            Mock(id = 1, name = "Renee Pittman", value = 8.9),
            Mock(id = 1, name = "Renee Pittman", value = 8.9),
            Mock(id = 1, name = "Renee Pittman", value = 8.9),
            Mock(id = 2, name = "Corine Hinton", value = 12.13),
            Mock(id = 2, name = "Corine Hinton", value = 12.13),
            Mock(id = 2, name = "Corine Hinton", value = 12.13),
            Mock(id = 3, name = "Sandy Rose", value = 16.17),
            Mock(id = 3, name = "Sandy Rose", value = 16.17),
            Mock(id = 3, name = "Sandy Rose", value = 16.17),
            Mock(id = 4, name = "Ruthie Leon", value = 22.23),
            Mock(id = 4, name = "Ruthie Leon", value = 22.23),
            Mock(id = 4, name = "Ruthie Leon", value = 22.23),
            Mock(id = 5, name = "Monroe Sparks", value = 26.27),
            Mock(id = 5, name = "Monroe Sparks", value = 26.27),
            Mock(id = 5, name = "Monroe Sparks", value = 26.27),
            Mock(id = 6, name = "Angela Hicks", value = 30.31),
            Mock(id = 6, name = "Angela Hicks", value = 30.31),
            Mock(id = 6, name = "Angela Hicks", value = 30.31),
            Mock(id = 7, name = "Lanny Mosley", value = 36.37),
            Mock(id = 7, name = "Lanny Mosley", value = 36.37),
            Mock(id = 7, name = "Lanny Mosley", value = 36.37),
            Mock(id = 8, name = "Sheldon Case", value = 40.41),
            Mock(id = 8, name = "Sheldon Case", value = 40.41),
            Mock(id = 8, name = "Sheldon Case", value = 40.41),
            Mock(id = 9, name = "Marion Bonner", value = 44.45),
            Mock(id = 9, name = "Marion Bonner", value = 44.45),
            Mock(id = 9, name = "Marion Bonner", value = 44.45),
            Mock(id = 10, name = "Glenna Graham", value = 48.49),
            Mock(id = 10, name = "Glenna Graham", value = 48.49),
            Mock(id = 10, name = "Glenna Graham", value = 48.49),
            Mock(id = 11, name = "Heather Wiggins", value = 52.53),
            Mock(id = 11, name = "Heather Wiggins", value = 52.53),
            Mock(id = 11, name = "Heather Wiggins", value = 52.53),
            Mock(id = 12, name = "Millie Stevenson", value = 58.59),
            Mock(id = 12, name = "Millie Stevenson", value = 58.59),
            Mock(id = 12, name = "Millie Stevenson", value = 58.59),
            Mock(id = 13, name = "Aubrey Meyer", value = 62.63),
            Mock(id = 13, name = "Aubrey Meyer", value = 62.63),
            Mock(id = 13, name = "Aubrey Meyer", value = 62.63),
            Mock(id = 14, name = "Mariano Clements", value = 66.67),
            Mock(id = 14, name = "Mariano Clements", value = 66.67),
            Mock(id = 14, name = "Mariano Clements", value = 66.67),
            Mock(id = 15, name = "Roberta Kline", value = 70.71),
            Mock(id = 15, name = "Roberta Kline", value = 70.71),
            Mock(id = 15, name = "Roberta Kline", value = 70.71),
            Mock(id = 16, name = "Amy Guerrero", value = 74.75),
            Mock(id = 16, name = "Amy Guerrero", value = 74.75),
            Mock(id = 16, name = "Amy Guerrero", value = 74.75),
            Mock(id = 17, name = "Otis Marsh", value = 78.79),
            Mock(id = 17, name = "Otis Marsh", value = 78.79),
            Mock(id = 17, name = "Otis Marsh", value = 78.79),
            Mock(id = 18, name = "Charles Walker", value = 82.83),
            Mock(id = 18, name = "Charles Walker", value = 82.83),
            Mock(id = 18, name = "Charles Walker", value = 82.83),
            Mock(id = 19, name = "Don Cruz", value = 86.87),
            Mock(id = 19, name = "Don Cruz", value = 86.87),
            Mock(id = 19, name = "Don Cruz", value = 86.87),
            Mock(id = 20, name = "Sergio Vazquez", value = 90.91),
            Mock(id = 20, name = "Sergio Vazquez", value = 90.91),
            Mock(id = 20, name = "Sergio Vazquez", value = 90.91),
            Mock(id = 21, name = "Elwood Nguyen", value = 96.97),
            Mock(id = 21, name = "Elwood Nguyen", value = 96.97),
            Mock(id = 21, name = "Elwood Nguyen", value = 96.97),
            Mock(id = 22, name = "Herminia Macias", value = 100.101),
            Mock(id = 22, name = "Herminia Macias", value = 100.101),
            Mock(id = 22, name = "Herminia Macias", value = 100.101),
            Mock(id = 23, name = "Erma Munoz", value = 104.105),
            Mock(id = 23, name = "Erma Munoz", value = 104.105),
            Mock(id = 23, name = "Erma Munoz", value = 104.105),
            Mock(id = 24, name = "Gregorio Reynolds", value = 108.109),
            Mock(id = 24, name = "Gregorio Reynolds", value = 108.109),
            Mock(id = 24, name = "Gregorio Reynolds", value = 108.109),
            Mock(id = 25, name = "Elliott O'Donnell", value = 112.113),
            Mock(id = 25, name = "Elliott O'Donnell", value = 112.113),
            Mock(id = 25, name = "Elliott O'Donnell", value = 112.113),
        )

        val distinctMocksList = mutableListOf(
            Mock(id = 1, name = "Renee Pittman", value = 8.9),
            Mock(id = 2, name = "Corine Hinton", value = 12.13),

            Mock(id = 3, name = "Sandy Rose", value = 16.17),
            Mock(id = 4, name = "Ruthie Leon", value = 22.23),

            Mock(id = 5, name = "Monroe Sparks", value = 26.27),
            Mock(id = 6, name = "Angela Hicks", value = 30.31),

            Mock(id = 7, name = "Lanny Mosley", value = 36.37),
            Mock(id = 8, name = "Sheldon Case", value = 40.41),

            Mock(id = 9, name = "Marion Bonner", value = 44.45),
            Mock(id = 10, name = "Glenna Graham", value = 48.49),

            Mock(id = 11, name = "Heather Wiggins", value = 52.53),
            Mock(id = 12, name = "Millie Stevenson", value = 58.59),

            Mock(id = 13, name = "Aubrey Meyer", value = 62.63),
            Mock(id = 14, name = "Mariano Clements", value = 66.67),

            Mock(id = 15, name = "Roberta Kline", value = 70.71),
            Mock(id = 16, name = "Amy Guerrero", value = 74.75),

            Mock(id = 17, name = "Otis Marsh", value = 78.79),
            Mock(id = 18, name = "Charles Walker", value = 82.83),

            Mock(id = 19, name = "Don Cruz", value = 86.87),
            Mock(id = 20, name = "Sergio Vazquez", value = 90.91),

            Mock(id = 21, name = "Elwood Nguyen", value = 96.97),
            Mock(id = 22, name = "Herminia Macias", value = 100.101),

            Mock(id = 23, name = "Erma Munoz", value = 104.105),
            Mock(id = 24, name = "Gregorio Reynolds", value = 108.109),

            Mock(id = 25, name = "Elliott O'Donnell", value = 112.113),
        )
    }
}