package com.github.nikbucher.vaadintippyjs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaadinTippyjsApplication {
	public static void main(String[] args) {
		SpringApplication.run(VaadinTippyjsApplication.class, args);
	}
}


@Route("")
class MainView extends VerticalLayout {
	MainView() {
		var h1 = new H1("Hi Tippy.js!");
		TippyJs.forComponent(h1).setHtml("""
				"Hi" in Swiss German (Schwiizerdütsch):
				<ul>
					<li>Hoi</li>
					<li>Sali</li>
					<li>Grüezi</li>
					<li>Tschou</li>
					<li>...</li>
				</ul>
				""".stripIndent());
		add(h1);
	}
}


@NpmPackage(value = "tippy.js", version = "6.3.7")
@JsModule("./src/tippy-loader.js")
class TippyJs {

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

	enum ContentMode {
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
