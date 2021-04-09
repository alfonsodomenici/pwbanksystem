/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.notification;

import it.tss.banksystem.security.boundary.AuthenticationFailed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author alfonso
 */
public class AuthenticationNotifier {

    @Inject
    System.Logger LOG;

    public void onAuthentication(@Observes @AuthenticationFailed String msg) {
        LOG.log(System.Logger.Level.INFO, "send mail notification " + msg + " ....");
    }
}
