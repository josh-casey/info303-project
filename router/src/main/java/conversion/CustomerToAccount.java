/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversion;

import domain.Customer;
import domain.Account;

/**
 *
 * @author casjo872
 */
public class CustomerToAccount {

	public Account customerToAccount(Customer cust) {
		Account account = new Account();
		account.setId(cust.getId());
		account.setEmail(cust.getEmail());
		account.setFirstName(cust.getFirstName());
		account.setLastName(cust.getLastName());
		account.setUsername(cust.getCustomerCode());
		account.setGroup("0afa8de1-147c-11e8-edec-2b197906d816");

		return account;
	}
}
