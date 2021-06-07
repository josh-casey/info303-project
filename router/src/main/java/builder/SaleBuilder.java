/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import domain.Sale;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class SaleBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:queue:new-sale")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Gson, Sale.class)
                .log("${body}")
                .to("jms:queue:sale");

        from("jms:queue:sale")
                .marshal().json(JsonLibrary.Gson)
                .log("POST to sales API: {$body}")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/api/sales")
                .to("jms:queue:http-response");

        from("jms:queue:http-response")
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setBody(constant(null)) // can't pass a body in a GET request
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://localhost:8081/api/sales/customer/${exchangeProperty.customer.id}/summary")
                .to("jms:queue:summary-response");

    }
}
