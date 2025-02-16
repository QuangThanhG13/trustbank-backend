package org.example.accoutservice.queries.query;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetAccountByIdQuery {
    private String accountId;
}
