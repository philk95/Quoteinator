package de.die.dudes.quoteinator.database.migration;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import de.die.dudes.quoteinator.R;

public class XMLParsing {

    private Context context;

    public XMLParsing(Context context) {
        this.context = context;
    }

    public void run(ConsumeData consumeData) throws FileNotFoundException,
            XmlPullParserException, IOException {
        ArrayList<String> docents = parseDocents();
        ArrayList<ModuleXML> modules = parseModules();
        ArrayList<QuotationXML> quotations = parseQuotations();

        consumeData.consumeDocents(docents);
        consumeData.consumeModules(modules);
        consumeData.consumeQuotations(quotations);
    }

    private ArrayList<QuotationXML> parseQuotations()
            throws XmlPullParserException, FileNotFoundException, IOException {
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory
                .newInstance();
        XmlPullParser parser = pullParserFactory.newPullParser();

        InputStream in_s = context.getResources().openRawResource(R.raw.quotations);
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);

        return parseXMLQuotations(parser);
    }

    private ArrayList<QuotationXML> parseXMLQuotations(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        ArrayList<QuotationXML> quotations = new ArrayList<>();
        int eventType = parser.getEventType();

        String tagName = null;
        QuotationXML quoatation = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if (tagName.equals("Quotation")) {
                        quoatation = new QuotationXML();
                    } else if (tagName.equals("Text")) {
                        quoatation.text = parser.nextText();
                    } else if (tagName.equals("Date")) {
                        quoatation.date = parser.nextText();
                    } else if (tagName.equals("Modul")) {
                        quoatation.module = parser.nextText();
                        quotations.add(quoatation);
                    }
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        return quotations;

    }

    private ArrayList<ModuleXML> parseModules() throws XmlPullParserException,
            FileNotFoundException, IOException {
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory
                .newInstance();
        XmlPullParser parser = pullParserFactory.newPullParser();

        InputStream in_s = context.getResources().openRawResource(R.raw.modules);
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);

        return parseXMLModules(parser);
    }

    private ArrayList<ModuleXML> parseXMLModules(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        ArrayList<ModuleXML> modules = new ArrayList<>();
        int eventType = parser.getEventType();

        String name = null;
        ModuleXML module = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Module")) {
                        module = new ModuleXML();
                    } else if (name.equals("Name")) {
                        module.name = parser.nextText();
                    } else if (name.equals("Docent")) {
                        module.docent = parser.nextText();
                        modules.add(module);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        return modules;

    }

    private ArrayList<String> parseDocents() throws XmlPullParserException,
            FileNotFoundException, IOException {
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory
                .newInstance();
        XmlPullParser parser = pullParserFactory.newPullParser();

        InputStream in_s = context.getResources().openRawResource(R.raw.docents);
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);

        return parseXMLDocents(parser);
    }

    private ArrayList<String> parseXMLDocents(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        ArrayList<String> docents = new ArrayList<>();
        int eventType = parser.getEventType();

        String name = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Docent")) {
                        docents.add(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        return docents;

    }
}
