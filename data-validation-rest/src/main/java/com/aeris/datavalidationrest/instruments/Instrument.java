package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.users.User;

public class Instrument {
    private String uuid;
    private User responsible;
    private Parameter[] parameters;
}
