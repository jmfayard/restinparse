package com.github.jmfayard.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseResultSchemas {

    public final List<ParseSchema> results;

    public ParseResultSchemas(List<ParseSchema> results) {
        this.results = results;
    }

    public static class ParseSchema {
        public final String className;
        public final Map<String, ParseFieldType> fields;

        public ParseSchema(String className, Map<String, ParseFieldType> fields) {
            this.className = className;
            if (fields == null) {
                this.fields = Collections.emptyMap();
            } else {
                this.fields = new HashMap<String, ParseFieldType>(fields);
            }
        }

        @Override
        public String toString() {
            return "Table[" + className + "] : " + fields.toString();
        }
    }

    public static class ParseFieldType {
        public final String type;

        ParseFieldType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

}
