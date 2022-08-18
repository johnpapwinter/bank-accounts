package com.bank_accounts.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class HolderTest {

    @Test
    @Disabled
    void addAccount() {
    }

    @Test
    @Disabled
    void removeAccount() {
    }

    @Test
    @Disabled
    void getId() {
        // given
        // when
        // that
    }

    @Test
    void getFirstname() {
        // given
        Holder testHolder = new Holder("James", "Bond", "9999");
        // when
        String firstname = testHolder.getFirstname();

        // that
        Assertions.assertThat(firstname).isEqualTo(testHolder.getFirstname());
    }

    @Test
    @Disabled
    void getLastname() {
        // given
        // when
        // that
    }

    @Test
    @Disabled
    void getSsn() {
        // given
        // when
        // that
    }

    @Test
    @Disabled
    void getAccounts() {
    }

    @Test
    @Disabled
    void setId() {
    }

    @Test
    @Disabled
    void setFirstname() {
    }

    @Test
    @Disabled
    void setLastname() {
    }

    @Test
    @Disabled
    void setSsn() {
    }

    @Test
    @Disabled
    void setAccounts() {
    }
}