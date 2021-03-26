/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.logging.boundary;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.System.Logger;

/**
 *
 * @author alfonso
 */
public class Logging {

    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        var loggerName = ip.getMember().getDeclaringClass().getName();
        return System.getLogger(loggerName);
    }
}
