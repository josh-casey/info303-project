import api.AccountApi;
import api.AccountsApi;
import domain.Account;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;


public class AccountsIntegrationTest {

	Retrofit retrofit = new Retrofit.Builder()
			  .baseUrl("http://localhost:8086/api/")
			  .addConverterFactory(GsonConverterFactory.create())
			  .build();

	AccountsApi accountsApi = retrofit.create(AccountsApi.class);
	AccountApi accountApi = retrofit.create(AccountApi.class);

	Account account1;
	Account account2;
	Account account3;

	@BeforeEach
	public void setUp() throws IOException {
		account1 = new Account()
				  .id("cust1")
				  .username("cust1_username")
				  .firstName("cust1_firstName")
				  .lastName("cust1_lastName")
				  .email("cust1@example.net")
				  .group("regular");

		account2 = new Account()
				  .id("cust2")
				  .username("cust2_username")
				  .firstName("cust2_firstName")
				  .lastName("cust2_lastName")
				  .email("cust2@example.net")
				  .group("regular");

		account3 = new Account()
				  .id("cust3")
				  .username("cust3_username")
				  .firstName("cust3_firstName")
				  .lastName("cust3_lastName")
				  .email("cust3@example.net")
				  .group("regular");

		accountsApi.createAccount(account1).execute();
		accountsApi.createAccount(account2).execute();
		// intentionally not creating account3
	}

	@AfterEach
	public void cleanUp() throws IOException {
		accountApi.deleteAccount(account1.getId()).execute();
		accountApi.deleteAccount(account2.getId()).execute();
		accountApi.deleteAccount(account3.getId()).execute();
	}

	@Test
	public void testCreateAccount() throws IOException {
		Response<Account> createRsp = accountsApi.createAccount(account3).execute();
		assertThat("create operation succeeded", createRsp.isSuccessful(), is(true));
		assertThat("response code should be 201", createRsp.code(), is(201));
		
		assertThat("check that returned account as same properties as account 3", createRsp.body(), samePropertyValuesAs(account3));
		
		Response<List<Account>> allAccountsRsp = accountsApi.getAccounts().execute();
		assertThat("get operation succeeded", allAccountsRsp.isSuccessful(), is(true));
		assertThat("account exists at service", allAccountsRsp.body(), hasItem(account3));

		createRsp = accountsApi.createAccount(account3).execute();
		assertThat("creating duplicate account should fail", createRsp.isSuccessful(), is(not(true)));		
		assertThat("response code should be 422", createRsp.code(), is(422));
	}

	@Test
	public void testGetAllAccounts() throws IOException {
		Response<List<Account>> rsp = accountsApi.getAccounts().execute();
		assertThat("operation should be successful", rsp.isSuccessful(), is(true));
		assertThat("response should contain both test accounts", rsp.body(), hasItems(account1, account2));
	}

	@Test
	public void testDeleteAccount() throws IOException {
		List<Account> accounts = accountsApi.getAccounts().execute().body();
		assertThat("make sure the account we are about to delete actually exists at service", accounts, hasItem(account1));

		Response<Void> response = accountApi.deleteAccount(account1.getId()).execute();
		assertThat("operation was successful", response.isSuccessful(), is(true));
		assertThat("response code should be 204", response.code(), is(204));

		accounts = accountsApi.getAccounts().execute().body();
		assertThat("account should no longer exist at service", accounts, not(hasItem(account1)));
		
		Response<Void> notFoundResponse = accountApi.deleteAccount("BAD ID").execute();
		assertThat("bad ID should fail", notFoundResponse.isSuccessful(), is(not(true)));
		assertThat("bad ID should cause 404 response code", notFoundResponse.code(), is(404));
	}

	@Test
	public void testUpdateAccount() throws IOException {
		account1.setFirstName("new firstname");
		account1.setGroup("VIP");

		Response<Account> response = accountApi.updateCustomer(account1.getId(), account1).execute();
		assertThat("operation should succeed",  response.isSuccessful(), is(true));
		assertThat("response code should be 200",  response.code(), is(200));

		List<Account> accounts = accountsApi.getAccounts().execute().body();

		@SuppressWarnings("null")
		Account updatedAccount = accounts.get(accounts.indexOf(account1));

		assertThat("first name updated at service", updatedAccount.getFirstName(), is("new firstname"));
		assertThat("group updated at service", updatedAccount.getGroup(), is("VIP"));
		
		String originalID = account1.getId();
		account1.setId("NEW ID");
		Response<Account> conflictResponse = accountApi.updateCustomer(originalID, account1).execute();
		assertThat("changing ID should fail", conflictResponse.isSuccessful(), is(not(true)));
		assertThat("changing ID should cause 409", conflictResponse.code(), is(409));
		account1.setId(originalID);
		
		Response<Account> notFoundResponse = accountApi.updateCustomer("BAD ID", account1).execute();
		assertThat("bad ID should fail", notFoundResponse.isSuccessful(), is(not(true)));
		assertThat("bad ID should cause 404", notFoundResponse.code(), is(404));
	}

}
