/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversion;

import domain.Customer;
import domain.Account;

public class AccountToCustomer {
   public Customer accountToCustomer(Account account) {
       Customer customer = new Customer();
       customer.setId(account.getId());
       customer.setEmail(account.getEmail());
       customer.setFirstName(account.getFirstName());
       customer.setLastName(account.getLastName());
       customer.setCustomerCode(account.getUsername());
       customer.setGroup("0afa8de1-147c-11e8-edec-2b197906d816");
       return customer;
   } 
}
