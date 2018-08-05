package com.vasilevskijivan.kotlinrestapiwithmongodb.repository

import com.vasilevskijivan.kotlinrestapiwithmongodb.model.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository : CrudRepository<Player, String> {
    fun findTop3ByOrderByTotalScoreDesc() : List<Player>
}