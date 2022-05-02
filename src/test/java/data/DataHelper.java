package data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }


    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }//Получить валидные данные для аутентиф

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("Goga", "auf228");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {//
        return new VerificationCode("12345");
    }


    @Value
    public static class CardInfo {
        private String cardNumber;
    }

    public static CardInfo getFirstCardInformation() {
        return new CardInfo("5559000000000001");
    }

    public static CardInfo getSecondCardInformation() {
        return new CardInfo("5559000000000002");
    }

    public static int checkBalanceOfCardFromWhereRechargeWasMade(int balance, int amount) {//Чекаем баланс карты-донора
        return balance - amount;
    }

    public static int checkBalanceOfRechargedCard(int balance, int amount) {//Чекаем баланс пополняемой карты
        return balance + amount;
    }



}


