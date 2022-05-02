package test;


import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    DashboardPage dashboardPage;


    @BeforeEach
    void setUP() {
        open("http://localhost:9999");
    }


    @Test
    void shouldTestTransferLimitFromFirstToSecond() {//Лимит по карте 1 переводим на 2
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 10000;
        val balanceOfFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.chooseSecondCardToRecharge();
        moneyTransferPage.moneyTransferCard(getFirstCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardAfter = dashboardPage.getSecondCardBalance();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfSecondCardBefore, amount);
        val balanceAfterTransactionFromRecharged = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfFirstCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfSecondCardAfter);
        assertEquals(balanceAfterTransactionFromRecharged, balanceOfFirstCardAfter);
    }


    @Test
    void shouldTestTransferLimitFromSecondToFirst() {//Лимит по карте 2 переводим на 1
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 10000;
        val balanceOfFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardAfter = dashboardPage.getSecondCardBalance();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransactionFromRecharged = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransactionFromRecharged, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTestTransferAvgFromFirstToSecond() {//Переводим среднее от лимита с карты 1 на карту 2
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 5000;
        val balanceOfFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.chooseSecondCardToRecharge();
        moneyTransferPage.moneyTransferCard(getFirstCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardAfter = dashboardPage.getSecondCardBalance();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfSecondCardBefore, amount);
        val balanceAfterTransactionFromRecharged = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfFirstCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfSecondCardAfter);
        assertEquals(balanceAfterTransactionFromRecharged, balanceOfFirstCardAfter);
    }

    @Test
    void shouldTestTransferAvgFromSecondToFirst() {//Переводим среднее от лимита с карты 2 на карту 1
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 5000;
        val balanceOfFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardAfter = dashboardPage.getSecondCardBalance();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransactionFromRecharged = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransactionFromRecharged, balanceOfSecondCardAfter);
    }


    @Test
    void shouldTransferNullMoneyFromSecondToFirst() {//Переводим ноль денег
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 0;
        val balanceOfFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceOfSecondCardAfter = dashboardPage.getSecondCardBalance();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransaction = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferOverLimitFromFirstToSecond() {//Переводим сумму больше лимита.Тест косячный
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        val amount = 20000;
        val moneyTransferPage = dashboardPage.chooseSecondCardToRecharge();
        moneyTransferPage.moneyTransferCard(getFirstCardInformation(), amount);
        moneyTransferPage.errorTransfer();
    }

}
