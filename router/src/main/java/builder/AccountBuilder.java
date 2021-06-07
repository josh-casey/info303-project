package builder;

import domain.Account;
import domain.Customer;
import conversion.AccountToCustomer;
import conversion.CustomerToAccount;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class AccountBuilder extends RouteBuilder {

	@Override
	public void configure() {
		from("jetty:http://localhost:9000/register?enableCORS=true")
			.setExchangePattern(ExchangePattern.InOnly)
			.log("Registration request recieved: ${body}")
			.unmarshal().json(JsonLibrary.Gson, Account.class)
			.log("Send to register queue: ${body}")
			.to("jms:queue:register");

		from("jms:queue:register")
			.bean(AccountToCustomer.class, "accountToCustomer(${body})")
			.log("Converted Account to Customer: ${body}")
			.log("Send to Vend: ${body}")
			.to("jms:queue:vend");

		from("jms:queue:vend")
			.removeHeaders("*") // remove headers as not sent to vend
			.setHeader("Authorization", constant("Bearer KiQSsELLtocyS2WDN5w5s_jYaBpXa0h2ex1mep1a"))
			.marshal().json(JsonLibrary.Gson) // only necessary if the message is an object, not JSON
			.setHeader(Exchange.CONTENT_TYPE).constant("application/json")
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			//.to("http://localhost:8089/api/2.0/customers")
			.to("https://info303otago.vendhq.com/api/2.0/customers?throwExceptionOnFailure=false")
			.choice()
			.when().simple("${header.CamelHttpResponseCode} == '201'") // change to 200 for PUT
			.log("Succesful creation send to vend-response")
			.convertBodyTo(String.class)
			.to("jms:queue:vend-response")
			.otherwise()
			.log("ERROR RESPONSE ${header.CamelHttpResponseCode} ${body}")
			.convertBodyTo(String.class)
			.to("jms:queue:vend-error")
			.endChoice();

		from("jms:queue:vend-response")
			.log("curr body: ${body}")
			.setBody().jsonpath("$.data")
			.marshal().json(JsonLibrary.Gson)
			.unmarshal().json(JsonLibrary.Gson, Customer.class)
			.log("Unmarshal Json as Customer class: ${body}")
			.bean(CustomerToAccount.class, "customerToAccount(${body})")
			.log("Convert customer to account and send to rest-account: ${body}")
			.to("jms:queue:account-api");
			
		from("jms:queue:account-api")
			.marshal().json(JsonLibrary.Gson) 
			.log("Marshal to JSON: ${body}")
			.removeHeaders("*")
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.log("Post to accounts API ${body}")
			.to("http://localhost:8086/api/accounts")
			.to("jms:queue:http-response");

	}

}
