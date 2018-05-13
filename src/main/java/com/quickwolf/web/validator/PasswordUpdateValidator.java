package com.quickwolf.web.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.quickwolf.web.form.beans.UpdatePasswordFormBean;

@Component
public class PasswordUpdateValidator implements Validator {

    public static final String PASSWORDS_DO_NOT_MATCH = "New passwords do not match";

    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.equals(UpdatePasswordFormBean.class);
    }

    @Override
    public void validate(final Object formBean, final Errors errors) {
        UpdatePasswordFormBean bean = (UpdatePasswordFormBean) formBean;
        if (!StringUtils.equals(bean.getNewPassword(), bean.getConfirmedNewPassword())) {
            errors.reject("UpdatePasswordFormBean.confirmNewPassword", PASSWORDS_DO_NOT_MATCH);
        }
    }
}
