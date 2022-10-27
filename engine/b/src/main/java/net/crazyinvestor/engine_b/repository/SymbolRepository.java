package net.crazyinvestor.engine_b.repository;

import net.crazyinvestor.engine_b.entity.Exchange;
import net.crazyinvestor.engine_b.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    Optional<Symbol> findByExchangeAndName(Exchange exchange, String name);
}
