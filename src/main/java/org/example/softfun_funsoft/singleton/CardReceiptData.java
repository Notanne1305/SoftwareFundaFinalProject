package org.example.softfun_funsoft.singleton;

public class CardReceiptData {
    private static CardReceiptData instance;
    private String receiptId;
    private String receiptUrl;
    private String paymentDateTime;
    private String cardHolderName;
    private String cardType;
    private CardReceiptData() {}

    public static synchronized CardReceiptData getInstance() {
        if (instance == null) {
            instance = new CardReceiptData();
        }
        return instance;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}