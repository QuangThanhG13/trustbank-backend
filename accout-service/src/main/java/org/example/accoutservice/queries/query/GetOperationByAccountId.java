package org.example.accoutservice.queries.query;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetOperationByAccountId {
    private String accountId;
    private int page;
    private int size;
}
