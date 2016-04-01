package com.wayneshare.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.configuration.ConfigurationException;
//import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration.PropertiesWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wayneshare.common.CommonConst;
import com.wayneshare.common.Config;

/**
 * this class is to read out all the config files
 * 
 * @author xiaowei
 * 
 */
public class ConfigUtils {

	public static final Logger LOG = LoggerFactory.getLogger(ConfigUtils.class);

	private static PropertiesConfiguration config = null;

	public static void loadConfig() {
		if (config == null)
			try {
				config = new PropertiesConfiguration();
				LOG.debug("config base path is at " + config.getBasePath());

				config.setEncoding("UTF-8");
				config.load("ToolConfig.properties");
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		return;
	}

	public static Config getConfig() {
		loadConfig();
		Config configObj = new Config();
		configObj.username = config.getString(CommonConst.CONFIG_USERNAME);
		configObj.password = config.getString(CommonConst.CONFIG_PASSWD);
		configObj.wordpressURL = config.getString(CommonConst.CONFIG_URL);
		configObj.googleSitesExportFolder = config
				.getString(CommonConst.CONFIG_FOLDER);
		return configObj;
	}

}
