package fr.tiagocerqueira;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Account implements IAccount {

    private double balance = 0.0;
    private List<Operation> operations = new ArrayList<>();

    public Account(double amount) {
        this.balance = amount;
    }

    @Override
    public void deposit(double amount) throws AccountException {
        if (amount < 0) {
            throw new AccountException();
        }

        this.balance += amount;
        addOperation(amount, OperationType.DEPOSIT);
    }

    @Override
    public void withdrawal(double amount) throws AccountException {
        if (amount > this.balance) {
            throw new AccountException();
        }

        this.balance -= amount;
        addOperation(amount, OperationType.WITHDRAWAL);
    }

    public String getHistoryOfOperation() {
        return getOperations().stream().map(Operation::toString).collect(Collectors.joining(","));
    }

    private void addOperation(double amount, OperationType type) {
        this.operations.add(Operation.createOperation(amount, type, this.balance));
    }
}
