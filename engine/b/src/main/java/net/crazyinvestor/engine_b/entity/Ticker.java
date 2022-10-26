package net.crazyinvestor.engine_b.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "tick_type")
    private String tickType;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "high_price")
    private Double highPrice;

    @Column(name = "low_price")
    private Double lowPrice;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "timestamp")
    private Long timestamp;
}
