package com.sadatmalik.fusionbanking.services.hsbc;

import com.sadatmalik.fusionbanking.FusionBankingApplication;
import com.sadatmalik.fusionbanking.model.Account;
import com.sadatmalik.fusionbanking.model.Transaction;
import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcUserAccessToken;
import com.sadatmalik.fusionbanking.repositories.BankRepository;
import com.sadatmalik.fusionbanking.services.AccountServicesRegistry;
import com.sadatmalik.fusionbanking.services.hsbc.model.accounts.HsbcAccountList;
import com.sadatmalik.fusionbanking.services.hsbc.model.balances.HsbcBalanceObject;
import com.sadatmalik.fusionbanking.services.hsbc.model.transacations.HsbcTransactionObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import static com.sadatmalik.fusionbanking.services.hsbc.HsbcApiServiceTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = FusionBankingApplication.class)
class HsbcApiServiceTest {

    @Autowired
    private HsbcApiService hsbcApiService;

    private static HsbcUserAccessToken userAccessToken;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private BankRepository bankRepository;

    @MockBean
    private AccountServicesRegistry accountServicesRegistry;

    @BeforeAll
    static void setUp() {
        // create a valid non-expiring user access token
        userAccessToken = mockValidUserAccessToken();
        HsbcUserAccessToken.setCurrentToken(userAccessToken);
    }

    @Test
    void testServiceContextLoads() {
        assertThat(hsbcApiService).isNotNull();
    }

    @Test
    void getUserAccounts() {
        assert HsbcUserAccessToken.current() == userAccessToken;

        given(
                restTemplate.exchange(
                        ArgumentMatchers.eq(HsbcOpenBankingService.ACCOUNT_INFO_URL),
                        ArgumentMatchers.eq(HttpMethod.GET),
                        ArgumentMatchers.<HttpEntity<String>>any(),
                        ArgumentMatchers.<Class<HsbcAccountList>>any()
                )
        ).willReturn(mockHsbcAccountListResponseEntity());

        given(
                restTemplate.exchange(
                        ArgumentMatchers.eq(HsbcOpenBankingService.ACCOUNT_INFO_URL +
                                "/" + mockHsbcAccount().getAccountId() + "/balances"),
                        ArgumentMatchers.eq(HttpMethod.GET),
                        ArgumentMatchers.<HttpEntity<String>>any(),
                        ArgumentMatchers.<Class<HsbcBalanceObject>>any()
                )
        ).willReturn(mockHsbcBalanceObjectResponseEntity());

        assertThat(hsbcApiService.getUserAccounts())
                .hasOnlyElementsOfType(Account.class);

        assertThat(hsbcApiService.getUserAccounts())
                .hasSize(1);

        verify(restTemplate, times(4))
                .exchange(ArgumentMatchers.contains(HsbcOpenBankingService.ACCOUNT_INFO_URL),
                        ArgumentMatchers.eq(HttpMethod.GET),
                        ArgumentMatchers.<HttpEntity<String>>any(),
                        ArgumentMatchers.<Class<?>>any());
    }

    @Test
    void getTransactions() {
        Account account = mockAccount();

        assert HsbcUserAccessToken.current() == userAccessToken;
        assert account != null;

        given(
                restTemplate.exchange(
                        ArgumentMatchers.eq(HsbcOpenBankingService.ACCOUNT_INFO_URL +
                                "/" + mockAccount().getAccountId() + "/transactions"),
                        ArgumentMatchers.eq(HttpMethod.GET),
                        ArgumentMatchers.<HttpEntity<String>>any(),
                        ArgumentMatchers.<Class<HsbcTransactionObject>>any()
                )
        ).willReturn(mockHsbcTransactionObjectResponseEntity());


        assertThat(hsbcApiService.getTransactions(account))
                .hasOnlyElementsOfType(Transaction.class);

        assertThat(hsbcApiService.getTransactions(account))
                .hasSize(1);

        verify(restTemplate, times(2))
                .exchange(ArgumentMatchers.contains(HsbcOpenBankingService.ACCOUNT_INFO_URL),
                        ArgumentMatchers.eq(HttpMethod.GET),
                        ArgumentMatchers.<HttpEntity<String>>any(),
                        ArgumentMatchers.<Class<?>>any());
    }
}