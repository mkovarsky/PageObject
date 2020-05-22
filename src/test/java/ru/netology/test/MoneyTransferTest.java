package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @ParameterizedTest
    @CsvSource({"100", "1000", "9999", "1000", "0"})
    void shouldTransferFrom0001To0002(int transfer) {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val rechargingPage = dashboardPage.deposit0001();
        val transferInfo = DataHelper.getSecondCardInformation();
        rechargingPage.submitTransfer(transfer, transferInfo);
        int balanceOfRechargeCardAfterTransaction = DataHelper.checkBalanceOfRechargeCard(balanceOfFirstCardBefore, transfer);
        int balanceOfDebitCardAfterTransaction = DataHelper.checkBalanceOfDebitCard(balanceOfSecondCardBefore, transfer);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceOfRechargeCardAfterTransaction, balanceOfFirstCardAfter);
        assertEquals(balanceOfDebitCardAfterTransaction, balanceOfSecondCardAfter);
    }

    @ParameterizedTest
    @CsvSource({"100", "1000", "9999", "1000", "0"})
    void shouldTransferFrom0002To0001(int transfer) {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val rechargingPage = dashboardPage.deposit0002();
        val transferInfo = DataHelper.getFirstCardInformation();
        rechargingPage.submitTransfer(transfer, transferInfo);
        int balanceOfRechargeCardAfterTransaction = DataHelper.checkBalanceOfRechargeCard(balanceOfSecondCardBefore, transfer);
        int balanceOfDebitCardAfterTransaction = DataHelper.checkBalanceOfDebitCard(balanceOfFirstCardBefore, transfer);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceOfRechargeCardAfterTransaction, balanceOfSecondCardAfter);
        assertEquals(balanceOfDebitCardAfterTransaction, balanceOfFirstCardAfter);
    }


    @Test
    void shouldNotSubmitOverdraft() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int transfer = DashboardPage.getCurrentBalanceOfFirstCard() + 1;
        val rechargingPage = dashboardPage.deposit0002();
        val transferInfo = DataHelper.getFirstCardInformation();
        rechargingPage.submitTransfer(transfer, transferInfo);
        dashboardPage.getError();
    }
}
