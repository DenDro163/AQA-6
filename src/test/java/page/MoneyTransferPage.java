package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class MoneyTransferPage {

    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public MoneyTransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage moneyTransferCard(DataHelper.CardInfo fromCardInfo, int amount) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        fromField.setValue(fromCardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void errorTransfer() {
        errorNotification.shouldBe(visible, ofSeconds(5)).shouldHave(text("Ошибка!"));
    }
}
