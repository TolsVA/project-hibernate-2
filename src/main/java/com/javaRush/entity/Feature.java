package com.javaRush.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Feature {
    TRAILERS ("Trailers"),
    COMMENTARIES ("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String value;

    public static Feature getFeatureByValue(String value) {
        return Arrays.stream(Feature.values())
                .filter(feature -> feature.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
