package com.vasilevskijivan.kotlinrestapiwithmongodb.Service

import com.vasilevskijivan.kotlinrestapiwithmongodb.PlayerScoreTestWithFongo
import com.vasilevskijivan.kotlinrestapiwithmongodb.model.Player
import com.vasilevskijivan.kotlinrestapiwithmongodb.service.PlayerService
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class PlayerServiceTest : PlayerScoreTestWithFongo() {
    @Autowired
    lateinit var playerService: PlayerService

    @Test
    fun testLeaders() {
        logger.info("Begin testLeaders")

        val leaders = playerService.leaders()

        assertEquals(3, leaders.size, "There should be 3 leaders.")
        assertEquals(TEST_PLAYER_4, leaders[0], "The first leader should be dawn.")
        assertEquals(TEST_PLAYER_3, leaders[1], "The second leader should be charlie.")
        assertEquals(TEST_PLAYER_1, leaders[2], "The third leader should be alice.")

        logger.info("End testLeaders")
    }

    @Test
    fun testScore() {
        logger.info("Begin testScore")

        playerRepository.save(Player(TEST_PLAYER_HANDLE))

        playerService.score(TEST_PLAYER_HANDLE, 10)

        logger.info("Find player by Id")
        val player = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(10, player.totalScore,
                "Total score should be 10 after first scoring event.")
        assertEquals(1, player.history.size,
                "The history should have single element.")
        assertEquals(10, player.history[0].points,
                "The recorded points should be 10.")

        logger.info("Update player, add 5 score")
        playerService.score(TEST_PLAYER_HANDLE, 5)

        logger.info("Find updatingplayer by Id")
        val player2 = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(15, player2.totalScore,
                "Total score should be 15 after second game")
        assertEquals(2, player2.history.size,
                "The history should have 2 game")
        assertEquals(10, player2.history[0].points,
                "The first recorded points should be 10")
        assertEquals(5, player2.history[1].points,
                "The second recorded points should be 5")

        logger.info("End scoreTest")
    }

    companion object {
        val logger: Logger =
                LoggerFactory.getLogger(PlayerServiceTest::class.java)
        const val TEST_PLAYER_HANDLE = "testPlayer"
    }
}