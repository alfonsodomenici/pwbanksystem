/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.trace;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author alfonso
 */
public class AuthenticationListener {

    @Inject
    System.Logger LOG;

    public void onAuthentication(@Observes String msg) {
        LOG.log(System.Logger.Level.INFO, msg);
    }
}
