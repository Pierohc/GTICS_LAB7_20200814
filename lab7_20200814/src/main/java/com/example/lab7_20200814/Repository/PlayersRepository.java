package com.example.lab7_20200814.Repository;

import com.example.lab7_20200814.Entity.Players;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Players, Integer> {





    @Query(value="SELECT * FROM lab7.players order by playerId desc", nativeQuery = true)
    List<Players> listarDeMayorMenor();



    @Query(value="SELECT * FROM lab7.players order by mmr asc", nativeQuery = true)
    List<Players> listarPlayersPorMmr();

    @Transactional
    @Modifying
    @Query(value="update players set position = ?1 where playerId=?2", nativeQuery = true)
    void updatePosition(Integer position, Integer playerId);
}
