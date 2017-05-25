package com.qk.authenticator.exception;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Description：
 * Created by hqhan on 2017/5/25 0025.
 */

@Accessors(chain = true)
@Setter
public class NotAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = -4101970664444907990L;

    public NotAuthorizedException(){
        super("Unauthorized");
    }

}
