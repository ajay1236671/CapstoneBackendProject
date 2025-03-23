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
import org.springframework.objenesis.ObjenesisHelper;

import java.util.*;

public class stripePaymentGateway implements PaymentGateway  {
    @Value("${stripe.key.secret}")
    private String apiKey;

    public String generatePaymentLink(String orderId, Long amount) throws StripeException{
        Stripe.apiKey = "sk_test_51PpwjmRwcLbnctZ8IQKuZ1RJnD20nyz6Fh0FvD985wApVhxkMPvHNtVzJa1rroOOgxpt9zwrhPfN2TuEgAX7JuDm00XaSbaCr7";

        ProductCreateParams productCreateParams =
                ProductCreateParams.builder().setName("Gold Plan").build();

        Product product = Product.create(productCreateParams);

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(amount)
                        .setProduct(product.getId())
                        .build();

        Price price = Price.create(params);


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
                                        .setUrl("http://localhost:8080/webhook")
                                        .build())
                                .build()).build();

        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);
        return paymentLink.getUrl();
    }
}
