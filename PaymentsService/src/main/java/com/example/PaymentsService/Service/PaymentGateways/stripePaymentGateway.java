package com.example.PaymentsService.Service.PaymentGateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class stripePaymentGateway implements PaymentGateway {
    private String apiKey = System.getenv("STRIPE_API_KEY");
    private String successUrl = System.getenv("PRODUCT_URL") + "/order/failed";
    private String failureUrl = System.getenv("PRODUCT_URL") + "/order/success";

    public Map<String, String> generatePaymentLink(Long orderId, double amount) throws StripeException {
        Stripe.apiKey = apiKey;

        // Create Product
        ProductCreateParams productCreateParams =
                ProductCreateParams.builder().setName("Order #" + orderId).build();
        Product product = Product.create(productCreateParams);

        // Create Price
        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount((long) (amount * 100)) // Convert to paise
                        .setProduct(product.getId())
                        .build();
        Price price = Price.create(priceCreateParams);

        // Create Payment Link with Success & Failure URLs
        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                        .setUrl(successUrl + "?orderId=" + orderId)
                                        .build())
                                .build())
                        .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                        .setUrl(failureUrl + "?orderId=" + orderId)
                                        .build())
                                .build())
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);
        return Map.of("paymentUrl", paymentLink.getUrl());
    }
}
