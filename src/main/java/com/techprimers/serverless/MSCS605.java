package com.techprimers.serverless;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mscs605")
public class MSCS605 {

    @Autowired
    RomanNumeralsCalculator calculator;

    @RequestMapping("/roman_calculator")
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setBody("Hello! Welcome to Shuang Fan's solution for MSCS605." +
                "\n Your Roman Equation is: " + input.getBody() +
                "\n The result is: " + calculator.calculate(input.getBody()));
        return responseEvent;
    }
}
