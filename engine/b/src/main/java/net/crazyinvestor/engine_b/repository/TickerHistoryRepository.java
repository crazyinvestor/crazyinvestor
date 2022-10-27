package net.crazyinvestor.engine_b.repository;

import net.crazyinvestor.engine_b.entity.Symbol;
import net.crazyinvestor.engine_b.entity.Ticker;
import net.crazyinvestor.engine_b.entity.TickerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TickerHistoryRepository extends JpaRepository<TickerHistory, Long> {
    Optional<TickerHistory> findByTicker(Ticker ticker);
}
