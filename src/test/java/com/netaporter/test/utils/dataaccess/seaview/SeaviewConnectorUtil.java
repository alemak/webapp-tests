package com.netaporter.test.utils.dataaccess.seaview;

import com.netaporter.pws.automation.shared.enums.Country;
import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import com.netaporter.seaview.connector.Client;
import com.netaporter.seaview.connector.SeaviewCustomerServiceImpl;
import com.netaporter.seaview.connector.SeaviewRequestCredentials;
import com.netaporter.seaview.connector.account.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Created by a.makarenko on 4/22/14.
 */
public class SeaviewConnectorUtil {
    public static final String DEFAULT_REGISTRATION_PASSWORD = "123456";
    public static final String DEFAULT_FIRST_NAME = "Test";
    public static final String DEFAULT_SURNAME = "User";

    @Autowired
    SeaviewCustomerServiceImpl seaviewCustomerService;

    public Customer registerDefaultCustomer(String brand, String region, List<String> roles){
        return registerCustomer(brand, region,
                new Customer(DEFAULT_FIRST_NAME, DEFAULT_SURNAME, CustomerDetailsUtil.generateEmailAddress(),
                        DEFAULT_REGISTRATION_PASSWORD, "United Kingdom"), roles);
    }


    public Customer registerCustomer(String brand, String region, Customer customer, List<String> roles){
        SeaviewRequestCredentials credentials = new SeaviewRequestCredentials(
                customer.getFname() + " " + customer.getSurname(), customer.getFname() + customer.getSurname(), roles, "1234");
        Account account = new Account();
        account.setFirstName(customer.getFname());
        account.setLastName(customer.getSurname());
        account.setEmail(customer.getEmail());
        account.setOrigin(new Client(brand, region, "1234"));
        Country country = null;
        for(Country c:Country.values()) {
            if (customer.getCountry().equals(c.getCountryName())) {
                country = c;
                break;
            }
        }
            if (country == null) {
                throw new RuntimeException("Invalid country name: " + customer.getCountry());
            }
            account.setRegisteredCountryCode(country.name());
            account.setLanguage("en");
            account.setFullyRegistered(true);
            //"123456"
            if(customer.getPassword().equals("123456")){
                account.setPassword("$2a$10$6QHCGqGempRx9U1z4/5Hw.JPimcohrk0Unxc7vzeEvkvrZHLaEkBC");
            }
            else{
                throw new RuntimeException("Do not know how what the hash would be for password: " + customer.getPassword());
            }
            //"password"
            // account.setPassword("$2a$10$DmCcp9Qc2kdGB6XaVrRO5.nJYtXAhGf86Huwkz2AVB6W9zpF852Tq");
            account.setRoles(roles);
            account.setActive(true);
            seaviewCustomerService.createAccount(account, credentials);
            // Account acc = seaviewCustomerService.findAccountByEMailAddress(emailAddress,"NAP", "INTL",credentials);
            return customer;

    }
}
