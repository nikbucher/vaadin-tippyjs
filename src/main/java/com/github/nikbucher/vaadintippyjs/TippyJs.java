package com.github.nikbucher.vaadintippyjs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

@NpmPackage(value = "tippy.js", version = "6.3.7")
@JsModule("./src/tippy-loader.js")
public class TippyJs {

	private final Component component;

	public TippyJs(Component component) {
		this.component = component;
	}

	public static TippyJs forComponent(Component component) {
		return new TippyJs(component);
	}

	public void setText(String text) {
		setContent(text, ContentMode.TEXT);
	}

	public void setHtml(String html) {
		setContent(html, ContentMode.HTML);
	}

	public void setContent(String content, ContentMode mode) {
		// https://atomiks.github.io/tippyjs/v6/all-props/
		var props = jsonObject(
				prop("allowHTML", mode == ContentMode.HTML),
				prop("content", content),
				prop("delay", jsonArray(200, 0)),
				prop("placement", "bottom"),
				prop("theme", "light-border"),
				prop("popperOptions", jsonObject(
						prop("modifiers", jsonArray(
								jsonObject(
										prop("name", "flip"),
										prop("enabled", false)
								)
						))
				))
		);
		component.getElement().executeJs("setTooltip(this, $0)", props);
	}

	public enum ContentMode {
		TEXT, HTML
	}


	/*
	 * some convenience classes and methods for {@link elemental.json.Json}
	 */
	record Prop(String name, JsonValue value) {
	}

	static JsonObject jsonObject(Prop... props) {
		JsonObject obj = Json.createObject();
		for (Prop prop : props) {
			obj.put(prop.name, prop.value);
		}
		return obj;
	}

	static Prop prop(String name, String value) {
		return new Prop(name, Json.create(value));
	}

	static Prop prop(String name, boolean value) {
		return new Prop(name, Json.create(value));
	}

	static Prop prop(String name, double value) {
		return new Prop(name, Json.create(value));
	}

	static Prop prop(String name, JsonValue value) {
		return new Prop(name, value);
	}

	static JsonArray jsonArray(Object... values) {
		JsonArray arr = Json.createArray();
		for (Object value : values) {
			switch (value) {
				case null -> arr.set(arr.length(), Json.createNull());
				case Double d -> arr.set(arr.length(), d);
				case Integer i -> arr.set(arr.length(), i);
				case Boolean b -> arr.set(arr.length(), b);
				case String s -> arr.set(arr.length(), s);
				case JsonValue v -> arr.set(arr.length(), v);
				default -> throw new IllegalStateException("Unexpected value: " + value);
			}
		}
		return arr;
	}

}
