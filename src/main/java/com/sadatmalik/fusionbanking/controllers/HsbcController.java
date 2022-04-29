package com.sadatmalik.fusionbanking.controllers;

import com.sadatmalik.fusionbanking.model.Account;
import com.sadatmalik.fusionbanking.model.Transaction;
import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcAuthenticationService;
import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcClientAccessToken;
import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcConsent;
import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcUserAccessToken;
import com.sadatmalik.fusionbanking.services.OpenBankingService;
import com.sadatmalik.fusionbanking.services.hsbc.HsbcApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is the Spring MVC controller that handles endpoints that are intended for
 * interaction with the HSBC Open Banking API.
 *
 * When the user chooses to connect an HSBC account, the /hsbc endpoint will be hit,
 * this will initiate the OAuth flow process that retrieves from HSBC a secure and
 * authenticated user account authorization URL.
 *
 * The user is then forwarded to HSBC where they can select the accounts that they
 * wish to authorize for access from Fusion.
 *
 * The HSBC Open Banking API will then return the user to the Fusion application
 * redirect URL submitted in the OAuth authentication sequence. This arrives back
 * at the hsbcCallback method defined in the controller below.
 *
 * These two primary methods may be factored out to a clean interface in a future
 * release. Essentially, what is needed is a getAuthorizationUrl() method; and an
 * openBankingCallback() method.
 *
 * @see #getAuthorizationUrl()
 * @author sadatmalik
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class HsbcController implements OpenBankingService {

    private final HsbcAuthenticationService hsbcAuth;
    private final HsbcApiService hsbc;
    private HsbcClientAccessToken clientAccessToken;

    /**
     * When the user hits this endpoint, the intention is to connect out to the Open Banking
     * API implementation at HSBC. It initiates an OAuth sequence that will end, if
     * successful, with the return of a secure, authenticated URL, which the user will be
     * redirected to, so that they can authorize access for Fusion to whichever accounts they
     * wish to grant permission for.
     *
     * Note that the clientAccessToken is Fusion's own access token to the OB API. It does
     * not therefore need to be requested on each occasion. It may be prudent to handle this once
     * on application startup; and to then re-request as needed based on token expiry. In a live
     * environment the application server will be continuously running so this renewals can be
     * handled effectively as needed.
     *
     * A user consent can then be retrieved, and this is followed by a request for the
     * authorisation URL, to which the user in then redirected.
     *
     * @return redirect to HSBC authorization portal
     */
    @Override
    @GetMapping("/get-auth-url")
    public String getAuthorizationUrl() {
        // @todo client access token belongs to creative fusion - store in DB?
        if (clientAccessToken == null) {
            clientAccessToken = hsbcAuth.getAccessToken();
        }

        HsbcConsent consent = hsbcAuth.getConsentID(clientAccessToken);
        String authorizationURL = hsbcAuth.getAuthorizationURL(consent);
        log.debug("Redirecting to HSBC auth URL: " + authorizationURL);

        return "redirect:" + authorizationURL;
    }

    @Override
    @GetMapping({"/get-user-access-token/{authCode}"})
    public void getAccessToken(@PathVariable String authCode) {
        HsbcUserAccessToken.setCurrentToken(hsbcAuth.getAccessToken(authCode));
    }

    @Override
    @GetMapping({"/get-user-accounts"})
    public List<Account> getUserAccounts() {
        List<Account> accounts = hsbc.getUserAccounts();
        return accounts;
    }

    @Override
    @GetMapping({"/get-transactions"})
    public List<Transaction> getTransactions(Account account) {
        List<Transaction> transactions = hsbc.getTransactions(account);
        return transactions;
    }

}
