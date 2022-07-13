package com.example.project1.util;


import com.example.project1.dao.PersonDao;
import com.example.project1.models.Person;
import com.example.project1.dao.PersonDao;
import com.example.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;
    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       Person person = (Person) target;

        if (personDao.getPersonByFullName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Person with this name already exist");
    }
}
