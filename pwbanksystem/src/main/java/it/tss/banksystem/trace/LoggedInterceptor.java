/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.trace;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author alfonso
 */
@Logged
@Interceptor
public class LoggedInterceptor {

    @Inject
    System.Logger LOG;

    @AroundInvoke
    public Object log(InvocationContext ic) throws Exception {
        
        long start = System.nanoTime();
        
        Object result = ic.proceed();
        
        LOG.log(System.Logger.Level.INFO, ic.getMethod().getDeclaringClass().getName() + "." + ic.getMethod().getName() + " executed time: " + (System.nanoTime() - start));

        return result;

    }
}
