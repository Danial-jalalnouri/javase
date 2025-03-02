package com.mysite.banking.service.impl;

import com.mysite.banking.dao.ATMStockDao;
import com.mysite.banking.dao.impl.ATMStockDaoImpl;
import com.mysite.banking.model.ATMStock;
import com.mysite.banking.model.Amount;
import com.mysite.banking.service.ATMService;
import com.mysite.banking.service.AccountService;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMServiceImpl implements ATMService {

    //public static final int[] billValues = new int[]{5, 10, 20, 50, 100, 200, 500};
    //private int[] moneyStock;

    private ATMStockDao atmStockDao;

    private final AccountService accountService;

    private static final ATMServiceImpl INSTANCE;

    public static ATMServiceImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new ATMServiceImpl();
    }
    private ATMServiceImpl() {
        atmStockDao = ATMStockDaoImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
    }
    private int[] withdrawSpecificAmount(BigDecimal amount) throws ValidationException {
        List<ATMStock> all = atmStockDao.getAll();
        int[] billsToWithdraw = new int[all.size()];
        BigDecimal remainingAmount = amount;

        for(int i = all.size() - 1; i >= 0; i--){
            int billValue = all.get(i).getDenomination();
            BigDecimal billValueDecimal = new BigDecimal(billValue);
            int numBillsNeeded = remainingAmount.divideToIntegralValue(billValueDecimal).intValue();

            if(numBillsNeeded > all.get(i).getQuantity()){
                numBillsNeeded = all.get(i).getQuantity();
            }

            billsToWithdraw[i] = numBillsNeeded;
            remainingAmount = remainingAmount.subtract(
                    billValueDecimal.multiply(BigDecimal.valueOf(numBillsNeeded))
            );
        }

        if(remainingAmount.compareTo(BigDecimal.ZERO) != 0){
            throw new ValidationException("The amount is larger than ATM balance!");
        }
        return billsToWithdraw;
    }

    public List<Integer> getBillValues(){
        List<ATMStock> all = atmStockDao.getAll();
        List<Integer> billValues = new ArrayList<>();
        for (ATMStock atmStock : all) {
            billValues.add(atmStock.getDenomination());
        }
        return billValues;
    }

    private int calculateBalance() {
        List<ATMStock> all = atmStockDao.getAll();
        int balance = 0;
        for (int i = 0; i < all.size(); i++) {
            //balance = balance + (billValues[i] * moneyStock[i]);
            balance += (all.get(i).getDenomination() * all.get(i).getQuantity());
        }
        return balance;
    }

    @Override
    public int[] withdraw(int accountId, Amount amount) throws AccountNotFindException, ValidationException {
        if(amount.getValue().compareTo(BigDecimal.valueOf(calculateBalance())) > 0){
            throw new ValidationException("The amount is larger than ATM balance!");
        }
        int[] billsToWithdraw = withdrawSpecificAmount(amount.getValue());
        accountService.withdraw(accountId, amount);
        finalWithdrawSpecificAmount(billsToWithdraw);
        return billsToWithdraw;
    }

    @Override
    public void deposit(int denomination, int quantity) {
        List<ATMStock> byDenomination = atmStockDao.getByDenomination(denomination);
        if(byDenomination.isEmpty()){
            ATMStock atmStock = new ATMStock();
            atmStock.setDenomination(denomination);
            atmStock.setQuantity(quantity);
            atmStockDao.save(atmStock);
        }else{
            ATMStock first = byDenomination.getFirst();
            first.setQuantity(first.getQuantity() + quantity);
            atmStockDao.update(first);
        }
    }

    private void finalWithdrawSpecificAmount(int[] billsToWithdraw) {
        List<ATMStock> all = atmStockDao.getAll();
        for (int i = 0; i < all.size(); i++) {
            if(billsToWithdraw[i]>0){
                //moneyStock[i] = moneyStock[i] - billsToWithdraw[i];
                all.get(i).setQuantity(all.get(i).getQuantity() - billsToWithdraw[i]);
                atmStockDao.update(all.get(i));
            }
        }
    }
}
