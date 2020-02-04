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
 * You should have received CaptchaImageGenerator copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.keycloak.marjaa.providers.login.customcaptcha.captchahelpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomUtils;

/**
 *
 * @author Shashank Singh
 */
public class CaptchaImageGenerator {
     private static int CANVAS_HEIGHT = 40;
    private static int CANVAS_WIDTH = 175;
    
    public static String generate(String captchaText) {
        BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);

        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        GradientPaint gradientPaint = new GradientPaint(10, 5, Color.blue, 20, 10, Color.LIGHT_GRAY, true);
        graphics.setPaint(gradientPaint);
        String fontNames[] = {"Calibri"};


        int index = (int) (Math.random() * (fontNames.length - 1));
        Font font = new Font(fontNames[index], Font.BOLD, 33);

        graphics.setFont(font);

        graphics.drawString(captchaText, 10, 30);
        RandomUtils RandomUtils = new RandomUtils();
        int randX1 = RandomUtils.nextInt(1, 20);
        int randY1 = RandomUtils.nextInt(1, 10);
        int randX2 = RandomUtils.nextInt(CANVAS_WIDTH-15, CANVAS_WIDTH);
        int randY2 = RandomUtils.nextInt(CANVAS_HEIGHT-10, CANVAS_HEIGHT);
        graphics.drawLine(randX1, randY1, randX2, randY2);

        randX1 = RandomUtils.nextInt(1, 20);
        randY1 = RandomUtils.nextInt(30, 40);
        randX2 = RandomUtils.nextInt(130, 150);
        randY2 = RandomUtils.nextInt(1, 10);
        graphics.drawLine(randX1, randY1, randX2, randY2);

        randX1 = RandomUtils.nextInt(1, 20);
        randY1 = RandomUtils.nextInt(1, 40);
        randX2 = RandomUtils.nextInt(130, 150);
        randY2 = RandomUtils.nextInt(1, 40);
        graphics.drawLine(randX1, randY1, randX2, randY2);

        graphics.dispose();

        //return image;
        String base64Image = null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            base64Image = Base64.getEncoder().encodeToString(imageInByte);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return base64Image;
    }
}
