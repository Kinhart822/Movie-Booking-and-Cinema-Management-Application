package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferForm {
    //todo
    private String bankId;
    private Double price;
    private String transactionContent;
}