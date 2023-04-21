package com.example.loanservice.entity;

import com.example.loanservice.entity.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private Long id;

    private String order_id;
    private Long user_id;
    private Long tariff_id;
    private Double credit_rating;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime time_insert;
    private LocalDateTime time_update;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (!order_id.equals(order.order_id)) return false;
        if (!user_id.equals(order.user_id)) return false;
        if (!tariff_id.equals(order.tariff_id)) return false;
        if (!credit_rating.equals(order.credit_rating)) return false;
        if (status != order.status) return false;
        if (!time_insert.equals(order.time_insert)) return false;
        return time_update.equals(order.time_update);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + order_id.hashCode();
        result = 31 * result + user_id.hashCode();
        result = 31 * result + tariff_id.hashCode();
        result = 31 * result + credit_rating.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + time_insert.hashCode();
        result = 31 * result + time_update.hashCode();
        return result;
    }
}



















