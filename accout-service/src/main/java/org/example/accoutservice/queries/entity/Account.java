package org.example.accoutservice.queries.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false,unique = true, updatable = false)
    private String customerId;

    @Column(nullable = false,unique = true, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    private LocalDateTime lastModifiedDate;

    private String lastModifiedBy;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Operation> operations;
    public void addOperation(Operation operation) {
        if (operations == null) {
            operations = new ArrayList<>();
        }
        if (operation != null && !operations.contains(operation)) {
            operations.add(operation);
        }
    }
}
