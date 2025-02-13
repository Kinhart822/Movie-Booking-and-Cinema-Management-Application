package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class BankTransferResponse {
    //todo
    private String bankId;
    private Double price;
    private String transactionContent;
}
