package br.com.notesrelease.services.util;

import br.com.notesrelease.dtos.NormaliserParserDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Normaliser {

    private List<String> titles = Arrays.asList(
            "Architect",
            "Software engineer",
            "Quantity surveyor",
            "Accountant");

    public String normalise(String text) {
        List<NormaliserParserDTO> values = NormaliserParserDTO.assembleList(this.titles);
        NormaliserParserDTO actualItem = new NormaliserParserDTO("");
        String[] inputs = text.split(" ");
        for (NormaliserParserDTO matchItem : values) {
            for (String input: inputs) {
                if (matchItem.getText().toLowerCase().contains(input.toLowerCase())) {
                    float inputSize = input.length();
                    float matchItemSize = matchItem.getText().replaceAll(" ", "").length();
                    float percentage = 0;
                    if (inputSize > matchItemSize) {
                        percentage = matchItemSize / inputSize;
                    } else {
                        percentage = inputSize / matchItemSize;
                    }
                    matchItem.setMatches(matchItem.getMatches() + percentage);
                }
            }
            if (matchItem.getMatches() > actualItem.getMatches()) { actualItem = matchItem; }
        }

        //TODO remove before send
        for (NormaliserParserDTO printer: values) {
            System.out.println(printer.getText() + " - " + printer.getMatches());
        }

        return actualItem.getText();
    }
}
