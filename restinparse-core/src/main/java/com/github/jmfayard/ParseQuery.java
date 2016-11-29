package com.github.jmfayard;

import com.github.jmfayard.internal.DefaultParseColumn;
import com.github.jmfayard.internal.ParseTableInternal;
import com.github.jmfayard.internal.Strings;
import com.github.jmfayard.model.ParseMap;
import com.github.jmfayard.model.ParsePointer;
import com.github.jmfayard.model.ParseUser;
import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ParseQuery<T extends ParseColumn> extends ParseTableInternal<T> {
    public final String className;
    public final Map<String, String> params;

    ParseQuery(String className, Map<String, String> params, Map<String, Object> where, List<String> includes, int skip, int limit, String order) {
        super(className);
        this.className = className;
        this.params = params;
        _where = where;
        _includes = includes;
        this.skip = skip;
        this.limit = limit;
        this.order = order;
    }

    public Observable<ParseObject<T>> find() {
        return super.find(params);
    }

    public Observable<ParseObject<T>> findAll() {
        Builder<DefaultParseColumn> builder = new Builder<DefaultParseColumn>(className, _where, _includes, 0, limit, order)
                .limit(QUERY_ALL_LIMIT)
                .descending(DefaultParseColumn.createdAt);
        return super.findAll(builder);
    }



    final Map<String, Object> _where;
    final List<String> _includes;
    final int skip; // not implemtented
    final int limit; // no limit
    final String order;


    public Observable<ParseObject<T>> findById(String id) {
        ParseQuery<T> query = new Builder<T>(className).withId(id).build();
        return query.find(params);
    }

    public static class Builder<T extends ParseColumn> {
        private final Map<String, Object> _where = new HashMap<String, Object>();
        private final List<String> _includes = new ArrayList<String>();
        private int skip = 0; // not implemtented
        private final SimpleDateFormat dateFormat;
        final private String className;
        private int limit = -1; // no limit
        private String order = "";

        public Builder(String className) {
            this.className = className;
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        public Builder(String className, Map<String, Object> where, List<String> includes, int skip, int limit, String order) {
            this(className);
            this._where.putAll(where);
            this._includes.addAll(includes);
            this.skip = skip;
            this.limit = limit;
            this.order = order;

        }

        public static String join(String delimiter, List<String> list) {
            if (list == null || list.size() == 0) {
                return "";
            }
            String result = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                result += delimiter;
                result += list.get(i);
            }
            return result;
        }

        public
        @NotNull Builder<T> equalToInt(@NotNull T key, int value) {
            _where.put(key.toString(), value);
            return this;
        }

        public
        @NotNull Builder<T> equalToBoolean(@NotNull T key, boolean value) {
            _where.put(key.toString(), value);
            return this;
        }

        public
        @NotNull Builder<T> equalToString(@NotNull T key, String value) {
            _where.put(key.toString(), value);
            return this;
        }

        public
        @NotNull Builder<T> equalToPtr(@NotNull T key, ParsePointer value) {
            _where.put(key.toString(), value);
            return this;
        }

        public
        @NotNull Builder<T> inRelationWith(ParsePointer relation, @NotNull String key) {
            Object value = objectOf("object", relation, "key", key);
            _where.put("$relatedTo", value);
            return this;
        }

        public
        @NotNull Builder<T> ascending(@NotNull T key) {
            order = key.toString();
            return this;
        }

        public
        @NotNull Builder<T> descending(@NotNull T key) {
            order = "-" + key.toString();
            return this;
        }



        public Builder<T> limit(int limit) {
            this.limit = Math.min(limit, 1000);
            return this;
        }

        public Builder<T> exists(T key) {
            Object encodedValue = objectOf("$exists", true, key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> doesNotExist(T key) {
            Object encodedValue = objectOf("$exists", false, key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> greaterThan(T key, int value) {
            Object encodedValue = objectOf("$gt", value, key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> lessThan(T key, int value) {
            Object encodedValue = objectOf("$lt", value, key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> newerThan(T key, Date date) {
            Object encodedValue = objectOf("$gt", datePtr(date), key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> olderThan(T key, Date date) {
            Object encodedValue = objectOf("$lt", datePtr(date), key);
            _where.put(key.toString(), encodedValue);
            return this;
        }

        public Builder<T> include(@NotNull T key) {
            _includes.add(key.toString());
            return this;
        }

        public Builder<T> withId(String id) {
            _where.put("objectId", id);
            return this;
        }



        public
        @NotNull ParseQuery<T> build() {
            Map<String, String> params = new HashMap<String, String>();
            Moshi moshi = new Moshi.Builder().build();

            if (_where.size() > 0) {
                params.put("where", moshi.adapter(Map.class).toJson(_where));
            }

            if (limit > 0) {
                params.put("limit", Integer.toString(limit));
            }

            if (!Strings.isNullOrEmpty(order)) {
                params.put("order", order);
            }

            String include = join(",", _includes);
            if (!Strings.isNullOrEmpty(include)) {
                params.put("include", include);
            }

            return new ParseQuery(className, params, _where, _includes, skip, limit, order);
        }


        private Object datePtr(@NotNull Date date) {
            return objectOf("__type", "Date", "iso", dateFormat.format(date));
        }

        private Map objectOf(@NotNull String key1, @NotNull Object value1, @NotNull String key2, @NotNull Object value2) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(key1, value1);
            map.put(key2, value2);
            return map;
        }

        private Map objectOf(@NotNull String key, @NotNull Object value, T existingKey) {
            Map<String, Object> map;
            String _k = existingKey.toString();
            if (_where.containsKey(_k) && _where.get(_k) instanceof Map) {
                map = ((Map<String, Object>) _where.get(_k));
            } else {
                map = new HashMap<String, Object>();
            }
            map.put(key, value);
            return map;
        }


    }
}
