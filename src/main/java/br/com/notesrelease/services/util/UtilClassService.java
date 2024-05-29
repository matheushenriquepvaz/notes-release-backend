package br.com.notesrelease.services.util;

import org.springframework.stereotype.Service;

@Service
public class UtilClassService {

    public String returnNormalizedString (String text) {
        Normaliser n = new Normaliser();
        return n.normalise(text);
    }
}
