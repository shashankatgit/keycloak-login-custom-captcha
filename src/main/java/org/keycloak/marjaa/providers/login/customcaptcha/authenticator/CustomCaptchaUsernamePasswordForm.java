/* Modified by Shashank Singh */
package org.keycloak.marjaa.providers.login.customcaptcha.authenticator;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.marjaa.providers.login.customcaptcha.captchahelpers.CaptchaImageGenerator;
import org.keycloak.marjaa.providers.login.customcaptcha.captchahelpers.CaptchaTextGenerator;
import org.keycloak.models.utils.FormMessage;

public class CustomCaptchaUsernamePasswordForm extends UsernamePasswordForm implements Authenticator {

    private static final String MESSAGE_INVALID_CAPTCHA = "Invalid Captcha";

    @Override
    /* This method is called when the login page is opened. */
    public void authenticate(AuthenticationFlowContext context) {
        context.getEvent().detail(Details.AUTH_METHOD, "auth_method");

        LoginFormsProvider form = context.form();

        String captcha = CaptchaTextGenerator.generate();

        // Setting an auth note so that it can be fetched later for the user entered input to be matched against. 
        // It is quite similar to a session variable 
        context.getAuthenticationSession().setAuthNote("Captcha", captcha);

        // This will set the base64 data of userInputCaptcha image to be shown in the login page. 
        // This will be used in login.ftl of a theme compatible with this
        form.setAttribute("captchaImageBase64", "data:image/jpeg;base64," + CaptchaImageGenerator.generate(captcha));

        // Out intervention stops here. Let's proceed naturally now to the super method. 
        super.authenticate(context);
    }

    @Override
    /* This method is called when the login details are submitted on the login page. */
    public void action(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();
        boolean captchaSuccess = false;
        context.getEvent().detail(Details.AUTH_METHOD, "auth_method");

        /* Retreive the user entered userInputCaptcha */
        String userInputCaptcha = formData.getFirst("captcha");

        /* Matching with Auth Note */
        if (context.getAuthenticationSession().getAuthNote("Captcha").equals(userInputCaptcha)) {
            captchaSuccess = true;
        }

        if (captchaSuccess) { //Captcha Matches With Auth Note
            if (formData.containsKey("cancel")) {
                context.cancelLogin();
                return;
            }
            if (!validateForm(context, formData)) { //validateForm is an inherited method from UsernamePasswordForm

                /* Let's hand over to super method */
                authenticate(context);
                return;
            }
            context.success();
        } else {

            // Let's raise an error event
            // Since there is nothing related to Captcha by default, we'll use just any error 
            context.getEvent().error(Errors.USER_NOT_FOUND);
            LoginFormsProvider form = context.form().setExecution(context.getExecution().getId());

            /* Let's set the error message which will be displayed to user on login page */
            form.setError(MESSAGE_INVALID_CAPTCHA);

            /* Recreating the form */
            form.createLogin();

            /* For regeneration of captcha */
            authenticate(context);

            return;
        }

    }

}
