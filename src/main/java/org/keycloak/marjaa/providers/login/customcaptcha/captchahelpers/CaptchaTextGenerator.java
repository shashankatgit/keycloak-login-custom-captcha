/*
 * Copyright (C) 2020 Shashank Singh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.keycloak.marjaa.providers.login.customcaptcha.captchahelpers;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 *
 * @author Shashank Singh
 */
public class CaptchaTextGenerator {
    /* Gives a random text for captcha */
    public static String generate() {
        String captcha1 = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        String captcha2 = new Integer(RandomUtils.nextInt(1,10)).toString();
        String captcha3 = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        
        String captcha = captcha1 + captcha2 + captcha3 ;
        
        return captcha;
    }
}
