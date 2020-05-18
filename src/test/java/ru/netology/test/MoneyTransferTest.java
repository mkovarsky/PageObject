package ru.netology.test;

import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/dataFrom0001To0002.csv")
    void shouldTransferFrom0001To0002(int transfer, int balance1, int balance2) {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.verify(verificationCode);
        val dashboardPage = new DashboardPage();
        dashboardPage.deposit0001();
        val transferInfo = DataHelper.getSecondCardInformation();
        dashboardPage.submitTransfer(transfer, transferInfo);
        dashboardPage.getBalance(balance1, balance2);
        //restore:
        dashboardPage.deposit0002();
        val transferInfo2 = DataHelper.getFirstCardInformation();
        dashboardPage.submitTransfer(transfer, transferInfo2);
        dashboardPage.getBalance(balance1 - transfer, balance2 + transfer);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/dataFrom0002To0001.csv")
    void shouldTransferFrom0002To0001(int transfer, int balance1, int balance2) {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.verify(verificationCode);
        val dashboardPage = new DashboardPage();
        dashboardPage.deposit0002();
        val transferInfo = DataHelper.getFirstCardInformation();
        dashboardPage.submitTransfer(transfer, transferInfo);
        dashboardPage.getBalance(balance1, balance2);
        //restore:
        dashboardPage.deposit0001();
        val transferInfo2 = DataHelper.getSecondCardInformation();
        dashboardPage.submitTransfer(transfer, transferInfo2);
        dashboardPage.getBalance(balance1 + transfer, balance2 - transfer);
    }
}
