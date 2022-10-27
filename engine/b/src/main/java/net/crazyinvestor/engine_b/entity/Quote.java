package net.crazyinvestor.engine_b.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    @Id
    @Column(name = "symbol_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
