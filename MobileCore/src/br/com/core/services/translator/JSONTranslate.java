package br.com.core.services.translator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;

import br.com.core.services.collection.Collection;

public abstract class JSONTranslate {
	protected Gson gson = new Gson();
}
