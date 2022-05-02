package page;


import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class DashboardPage {//Личный кабинет с картами
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");//Для просмотра баланса 1 карты
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");//Для просмотра баланса 2 карты
    private SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");//Для кнопки 1 карты
    private SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");//Для кнопки 2 карты
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {//Конструктор
        heading.shouldBe(visible);//Заголовок должен быть виден
    }

    public MoneyTransferPage chooseFirstCardToRecharge() {
        firstCardButton.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage chooseSecondCardToRecharge() {
        secondCardButton.click();
        return new MoneyTransferPage();
    }

    public int getFirstCardBalance() {
        val text = firstCard.getText();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = secondCard.getText();
        return extractBalance(text);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
