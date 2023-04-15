package com.component.authserver.constants;

public enum Provider {
    GOOGLE("Google"),GITHUB("Github"),FACEBOOK("Facebook");

    private String name;
    private Provider(String name){
        this.name = name;
    }
}
