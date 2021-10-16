package fr.tiagocerqueira;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

@Getter
public class Operation {

    private Date date = new Date();
    private double amount;
    private double actualBalanceAtOperation;
    private OperationType operationType;

    private Operation() {
    }

    private Operation(double amount, OperationType operation, double actualBalanceAtOperation) {
        this.amount = amount;
        this.operationType = operation;
        this.actualBalanceAtOperation = actualBalanceAtOperation;
    }

    public static Operation createOperation(double amount, OperationType operationType, double actualBalanceAt) {
        return new Operation(amount, operationType, actualBalanceAt);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(
                "An %s type operation of %.2f€ amount was effectuate on your account on %s. Balance after the operation %.2f€",
                this.getOperationType().name(), getAmount(), sdf.format(getDate()), getActualBalanceAtOperation());
    }
}
