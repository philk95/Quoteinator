package de.die.dudes.quoteinator.database.migration;

import java.util.ArrayList;

public interface ConsumeData {
	void consumeDocents(ArrayList<String> docents);

	void consumeModules(ArrayList<ModuleXML> modules);

	void consumeQuotations(ArrayList<QuotationXML> quoations);
}
