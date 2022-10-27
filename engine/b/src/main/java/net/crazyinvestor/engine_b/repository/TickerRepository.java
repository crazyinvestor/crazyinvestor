package net.crazyinvestor.engine_b.repository;

import net.crazyinvestor.engine_b.entity.Symbol;
import net.crazyinvestor.engine_b.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {
    Optional<Ticker> findBySymbolAndTickType(Symbol symbol, String tickType);
}
