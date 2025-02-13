package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class BankTransferForm {
    //todo
    private String bankId;
    private Double price;
    private String transactionContent;
}
