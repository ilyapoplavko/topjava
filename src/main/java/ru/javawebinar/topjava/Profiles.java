package ru.javawebinar.topjava;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres" +
            "",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String[] getActiveDbProfile() {
        String[] profiles = new String[2];

        profiles[0] = REPOSITORY_IMPLEMENTATION;

        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            profiles[1] = POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            profiles[1] = HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }

        return profiles;
    }
}
