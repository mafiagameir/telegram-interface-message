/*
 * Copyright (C) 2015 mafiagame.ir
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package co.mafiagame.telegraminterface.message;

import co.mafiagame.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author hekmatof
 */
@Component
public class MessageHolder {
    private static final Logger logger = LoggerFactory.getLogger(MessageHolder.class);
    private static final Properties properties = new Properties();


    @Value("${mafia.language}")
    private String language;

    @PostConstruct
    public void init() {
        try {
            properties.load(MessageHolder.class.getClassLoader().getResourceAsStream(Constants.CONF.MESSAGE_FILE + language + ".properties"));
        } catch (IOException e) {
            logger.error("could not load message properties", e);
        }
    }

    public static String get(String key, String... args) {
        try {
            String value = properties.getProperty(key);
            if (args != null)
                for (String arg : args) {
                    value = value.replaceFirst("%", arg);
                }
            return value;
        } catch (NullPointerException e) {
            return key + Arrays.toString(args);
        }
    }
}
