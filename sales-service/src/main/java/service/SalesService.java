package service;

import dao.SaleDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jooby.Jooby;
import org.jooby.json.Gzon;
import resource.CustomerSalesResource;
import resource.SalesResource;

public class SalesService extends Jooby {

	public SalesService() {
		SaleDAO dao = new SaleDAO();

		port(8081);

		use(new Gzon());

		use(new SalesResource(dao));
		use(new CustomerSalesResource(dao));
	}

	public static void main(String[] args) throws IOException {
		System.out.println("\n\n****** Sales Service ******\n\n");
		new SalesService().start();
	}

}
