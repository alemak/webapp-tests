package com.netaporter.pws.automation.shared.apiclients.pojos;

import com.netaporter.test.utils.assertion.objectcomparison.BaseClassForAssertingFields;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 06/03/2013
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class GetAccountResults extends BaseClassForAssertingFields {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data extends BaseClassForAssertingFields{

        private String email;
        private String firstname;
        private String surname;


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }
    }
}
