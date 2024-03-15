package africa.semicom.ecommerceApp.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void setCreatedAt() {
        this.createdAt = now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = now();
    }
}
