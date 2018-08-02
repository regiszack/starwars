package com.starwars.planetas.clients;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class SwApiClient {

    private static final String PLANETA_INEXISTENTE = "Planeta inexistente/desconhecido no universo";

    public JSONArray obterResposta(String nome) {
        String url = "https://swapi.co/api/planets/?search=" + nome;
        RestTemplate template = new RestTemplate();
        HttpHeaders cabecalho = this.criarCabecalho();

        HttpEntity<String> entidadeHttp = new HttpEntity<>(cabecalho);

        ResponseEntity<String> resposta = template.exchange(url, HttpMethod.GET, entidadeHttp, String.class);

        return this.obterFilmesDentroDaResposta(resposta);
    }

    private JSONArray obterFilmesDentroDaResposta(ResponseEntity<String> resposta) {
        try {
            return new JSONObject(resposta.getBody())
                    .getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONArray("films");
        } catch (JSONException e) {
            throw new RuntimeException(PLANETA_INEXISTENTE);
        }
    }

    private HttpHeaders criarCabecalho() {
        HttpHeaders cabecalho = new HttpHeaders();
        cabecalho.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        cabecalho.setContentType(MediaType.APPLICATION_JSON);
        cabecalho.set("user-key", "*********");

        return cabecalho;
    }
}