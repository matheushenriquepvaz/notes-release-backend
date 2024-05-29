package br.com.notesrelease.Util;

import br.com.notesrelease.infra.exceptions.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.UnknownHostException;


@Slf4j
public class ResourceUtils {

	public static String getNumeroIP() throws NegocioException {

        try {
            return Inet4Address.getLocalHost().getHostAddress().toString();

        } catch (UnknownHostException e) {
            log.error("Erro ao identificar IP servidor", e);
        }

        throw new NegocioException("Erro recuperar IP");
    }
}
