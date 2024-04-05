package com.github.nikbucher.vaadintippyjs;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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
