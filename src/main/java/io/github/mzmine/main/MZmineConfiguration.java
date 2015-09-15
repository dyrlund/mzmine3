/*
 * Copyright 2006-2015 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import io.github.mzmine.modules.MZmineModule;
import io.github.mzmine.parameters.ParameterSet;

/**
 * MZmine configuration interface
 */
public class MZmineConfiguration {

    private static final Logger logger = Logger
            .getLogger(MZmineConfiguration.class.getName());

    public static final File CONFIG_FILE = new File("conf/config.xml");

    public MZmineConfiguration() {
        // preferences = new MZminePreferences();
    }

    public NumberFormat getIntensityFormat() {
        return DecimalFormat.getNumberInstance();
        // return
        // preferences.getParameter(MZminePreferences.intensityFormat).getValue();
    }

    public NumberFormat getMZFormat() {
        return DecimalFormat.getNumberInstance();
        // return
        // preferences.getParameter(MZminePreferences.mzFormat).getValue();
    }

    public NumberFormat getRTFormat() {
        return DecimalFormat.getNumberInstance();
        // return
        // preferences.getParameter(MZminePreferences.rtFormat).getValue();
    }

    public static void loadConfiguration(File file) throws IOException {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document configuration = dBuilder.parse(file);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            logger.finest("Loading desktop configuration");

            XPathExpression expr = xpath.compile("//configuration/preferences");
            NodeList nodes = (NodeList) expr.evaluate(configuration,
                    XPathConstants.NODESET);
            if (nodes.getLength() == 1) {
                Element preferencesElement = (Element) nodes.item(0);
                // preferences.loadValuesFromXML(preferencesElement);
            }

            logger.finest("Loading modules configuration");

            for (MZmineModule module : MZmineModules.getAllModules()) {

                String className = module.getClass().getName();
                expr = xpath.compile("//configuration/modules/module[@class='"
                        + className + "']/parameters");
                nodes = (NodeList) expr.evaluate(configuration,
                        XPathConstants.NODESET);
                if (nodes.getLength() != 1)
                    continue;

                Element moduleElement = (Element) nodes.item(0);

                /*ParameterSet moduleParameters = getModuleParameters(
                        module.getClass());
                moduleParameters.loadValuesFromXML(moduleElement);*/
            }

            logger.info("Loaded configuration from file " + file);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public void saveConfiguration(File file) throws IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document configuration = dBuilder.newDocument();
            Element configRoot = configuration.createElement("configuration");
            configuration.appendChild(configRoot);

            Element prefElement = configuration.createElement("preferences");
            configRoot.appendChild(prefElement);
            // preferences.saveValuesToXML(prefElement);

            Element modulesElement = configuration.createElement("modules");
            configRoot.appendChild(modulesElement);

            // traverse modules
            for (MZmineModule module : MZmineModules.getAllModules()) {

                String className = module.getClass().getName();

                Element moduleElement = configuration.createElement("module");
                moduleElement.setAttribute("class", className);
                modulesElement.appendChild(moduleElement);

                Element paramElement = configuration
                        .createElement("parameters");
                moduleElement.appendChild(paramElement);

              /*  ParameterSet moduleParameters = getModuleParameters(
                        module.getClass());
                moduleParameters.saveValuesToXML(paramElement);*/

            }

            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer transformer = transfac.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");

            StreamResult result = new StreamResult(new FileOutputStream(file));
            DOMSource source = new DOMSource(configuration);
            transformer.transform(source, result);

            logger.info("Saved configuration to file " + file);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}