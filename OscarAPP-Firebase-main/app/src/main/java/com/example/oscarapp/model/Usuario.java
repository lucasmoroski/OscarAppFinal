package com.example.oscarapp.model;

public class Usuario {

    private final String uuid;
    private final String username;
    private final Integer token;
    private final String voto_diretor, voto_filme;

    public Usuario(String uuid, String username, Integer token, String voto_diretor, String voto_filme ) {
        this.uuid = uuid;
        this.username = username;
        this.token = token;
        this.voto_diretor = voto_diretor;
        this.voto_filme = voto_filme;
    }

    public String getVoto_filme() {
        return voto_filme;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public Integer getToken() {
        return token;
    }

    public String voto_diretor() {
        return voto_diretor;
    }

    public String voto_filme() {
        return voto_filme;
    }
}
