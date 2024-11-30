package org.example.softfun_funsoft.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class Card {
    public Card() {
        Dotenv dotenv = Dotenv.load();
        String secretKey = dotenv.get("STRIPE_API_KEY");
        Stripe.apiKey = secretKey;
    }

    public Charge createCharge(String token, int amount, String email, String cardHolderName, String cardNumber, String cvc, String expiration, String country, String zip) throws StripeException {
        validateCard(cardNumber, cvc, expiration);
        validateEmail(email);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount * 100);
        chargeParams.put("currency", "php");
        chargeParams.put("description", "Charge for " + email);
        chargeParams.put("source", token);
        chargeParams.put("receipt_email", email);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("cardholder_name", cardHolderName);
        chargeParams.put("metadata", metadata);

        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(Stripe.apiKey)
                .build();

        return Charge.create(chargeParams, requestOptions);
    }

    private void validateCard(String cardNumber, String cvc, String expiration) {
        if (!isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid card number.");
        }
        if (!isValidCVC(cvc)) {
            throw new IllegalArgumentException("Invalid CVC.");
        }
        if (!isValidExpiration(expiration)) {
            throw new IllegalArgumentException("Invalid expiration date.");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }

    private boolean isValidCardNumber(String cardNumber) {
        // Implement Luhn algorithm to check card number validity
        int nDigits = cardNumber.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond) d = d * 2;
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    private boolean isValidCVC(String cvc) {
        // Check if CVC is a 3 or 4 digit number
        return cvc.matches("\\d{3,4}");
    }

    private boolean isValidExpiration(String expiration) {
        // Check if expiration date is in the format MM/YY and is not expired
        if (!expiration.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }
        String[] parts = expiration.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt("20" + parts[1]);

        java.util.Calendar now = java.util.Calendar.getInstance();
        int currentYear = now.get(java.util.Calendar.YEAR);
        int currentMonth = now.get(java.util.Calendar.MONTH) + 1;

        return (year > currentYear) || (year == currentYear && month >= currentMonth);
    }
}