package com.itechart.lab.repository;

import com.itechart.lab.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReaderDao extends AbstractDao<Reader>{
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM reader";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM reader WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM reader WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO reader (`first_name`,`last_name`,`email`,`gender`,`phone`,`date_of_registration`)  VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE reader SET  first_name=?, last_name=?, email=?,gender=?, phone=?, date_of_registration=? WHERE id=?";
    private static final String SELECT_EMAIL_QUERY = "SELECT email FROM reader";
    private static final String SELECT_NAME_BY_MAIL_QUERY = "SELECT first_name FROM reader WHERE email=?";


    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String GENDER_COLUMN = "gender";
    private static final String PHONE_COLUMN = "phone";
    private static final String REGISTRATION_DATE_COLUMN = "date_of_registration";


    public ReaderDao(Connection connection){
        super(connection);
    }

    public List<String> getEmails() throws DaoException{
        try(Statement statement
                    = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_EMAIL_QUERY);
            List<String> emails = new ArrayList<>();
            while(resultSet.next()) {
                String email = resultSet.getString(EMAIL_COLUMN);
                emails.add(email);
            }
            return emails;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public String getNameByEmail(String email) throws DaoException {
        try(PreparedStatement preparedStatement
                = prepareStatementForQuery(SELECT_NAME_BY_MAIL_QUERY, email)){
            ResultSet resultSet = preparedStatement.executeQuery();

            String name = null;
            if(resultSet.next()) {
                name = resultSet.getString(FIRST_NAME_COLUMN);
            }
            return name;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected List<String> getEntityParameters(Reader entity) {
        List<String> parameters = new ArrayList<>();

        String firstname = entity.getFirstname();
        parameters.add(firstname);

        String lastname = entity.getLastname();
        parameters.add(lastname);

        String email = entity.getEmail();
        parameters.add(email);

        boolean gender = entity.getGender();
        String genderValue = String.valueOf(gender);
        parameters.add(genderValue);

        String phone = entity.getPhone();
        if (phone == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            parameters.add(phone);
        }

        Date registrationDate = entity.getDateOfRegistration();
        String registrationDateValue = String.valueOf(registrationDate);
        parameters.add(registrationDateValue);

        return parameters;
    }

    @Override
    protected Reader buildEntity(ResultSet resultSet) throws DaoException {
        try {
            Reader reader = new Reader();

            int id = resultSet.getInt(ID_COLUMN);
            reader.setId(id);

            String firstname = resultSet.getString(FIRST_NAME_COLUMN);
            reader.setFirstname(firstname);

            String lastname = resultSet.getString(LAST_NAME_COLUMN);
            reader.setLastname(lastname);

            String email = resultSet.getString(EMAIL_COLUMN);
            reader.setEmail(email);

            boolean gender = resultSet.getBoolean(GENDER_COLUMN);
            reader.setGender(gender);

            String phone = resultSet.getString(PHONE_COLUMN);
            reader.setPhone(phone);

            Date registrationDate = resultSet.getDate(REGISTRATION_DATE_COLUMN);
            reader.setDateOfRegistration((java.sql.Date) registrationDate);

            return reader;

        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();
        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }

    }
