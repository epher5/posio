package com.posio

import com.simplify.payments.PaymentsApi
import com.simplify.payments.PaymentsMap
import com.simplify.payments.domain.Authorization
import grails.transaction.Transactional

@Transactional
class AuthorizationService {

    def authorize() {
        PaymentsApi.PUBLIC_KEY = "sbpb_OWRmZDI5MjktMGUxOC00MzE4LTlmMTYtNWJiZWVmMzliMzUx"
        PaymentsApi.PRIVATE_KEY = "wu0powWN1rOLmOdcS8/uP564gMPmAFdhrE1WfHPE/dZ5YFFQL0ODSXAOkNtXTToq"

        Authorization authorization = Authorization.create(new PaymentsMap()
                .set("amount", 2500)
                .set("card.cvc", "123")
                .set("card.expMonth", 11)
                .set("card.expYear", 19)
                .set("card.number", "5555555555554444")
                .set("currency", "USD")
                .set("description", "test authorization")
                .set("reference", "KP-76TBONES")
        );

        System.out.println(authorization);
    }
}
