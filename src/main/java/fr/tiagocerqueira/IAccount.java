package fr.tiagocerqueira;

public interface IAccount {
    void deposit(double amount) throws AccountException;

    void withdrawal(double amount) throws AccountException;
}
