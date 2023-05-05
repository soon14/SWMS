package com.swms.user.config.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.swms.user.utils.Response;

import java.io.IOException;

/**
 * @author sws
 * @version 1.0
 * @since 2020-12-26
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    private static final long serialVersionUID = 2652127645704345563L;

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Response response = Response.builder().code("1").msg(value.getMessage()).data(value.getAdditionalInformation()).build();
        gen.writeObject(response);
    }
}
