package br.com.notesrelease.dtos;

import br.com.notesrelease.services.util.Normaliser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NormaliserParserDTO {

    String text;
    float matches;

    public NormaliserParserDTO(String text) {
        this.text = text;
        this.matches = 0;
    }

    public NormaliserParserDTO(String text, float matches) {
        this.text = text;
        this.matches = matches;
    }

    public static List<NormaliserParserDTO> assembleList(List<String> inputs) {
        List<NormaliserParserDTO> values = new ArrayList<>();
        for (String input: inputs) {
            values.add(new NormaliserParserDTO(input));
        }
        return values;
    }

}
