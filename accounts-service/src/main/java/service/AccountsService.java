package service;

import dao.AccountsDAO;
import java.io.IOException;
import org.jooby.Jooby;
import org.jooby.handlers.Cors;
import org.jooby.handlers.CorsHandler;
import org.jooby.json.Gzon;
import resource.AccountResource;
import resource.AccountCollectionResource;

public class AccountsService extends Jooby {

	public AccountsService() {
		AccountsDAO dao = new AccountsDAO();

		port(8086);

		use(new Gzon());

		use("*", new CorsHandler(new Cors().withMethods("*")));

		use(new AccountCollectionResource(dao));
		use(new AccountResource(dao));
	}

	public static void main(String[] args) throws IOException {
		System.out.println("\n\n****** Accounts Service ******\n\n");
		new AccountsService().start();
	}

}
