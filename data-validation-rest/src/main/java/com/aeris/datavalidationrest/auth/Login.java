package com.aeris.datavalidationrest.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {
    @NotNull(message = "Username cannot be null")
    @ApiModelProperty(value = "admin", example = "user1")
    private String username;
    @NotNull(message = "Password cannot be null")
    @ApiModelProperty(value = "test", example = "test")
    private String password;
    @ApiModelProperty(hidden = true)
    private String client_id;
    @ApiModelProperty(hidden = true)
    private String grant_type;

    public Login() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", client_id='" + client_id + '\'' +
                ", grant_type='" + grant_type + '\'' +
                '}';
    }
}
