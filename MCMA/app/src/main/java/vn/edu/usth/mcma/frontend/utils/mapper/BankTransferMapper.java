package vn.edu.usth.mcma.frontend.utils.mapper;

import vn.edu.usth.mcma.frontend.model.parcelable.BankTransferParcelable;
import vn.edu.usth.mcma.frontend.model.response.BankTransferResponse;

public class BankTransferMapper {
    public static BankTransferParcelable fromResponse(BankTransferResponse response) {
        return BankTransferParcelable.builder()
                .bankId(response.getBankId())
                .price(response.getPrice())
                .transactionContent(response.getTransactionContent()).build();
    }
}
