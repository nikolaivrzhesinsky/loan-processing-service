package com.example.loanservice.entity;


import com.example.loanservice.entity.enums.TariffType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "tariff")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TariffType type;

    private String interest_rate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (!id.equals(tariff.id)) return false;
        if (type != tariff.type) return false;
        return interest_rate.equals(tariff.interest_rate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + interest_rate.hashCode();
        return result;
    }
}


