import com.vaadin.gradle.vaadin

plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.vaadin)
}

group = "com.github.nikbucher"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
}

val vaadinProductionMode: Boolean = findProperty("vaadin.productionMode").toString().toBoolean()
vaadin {
	pnpmEnable = true
}

dependencies {
	implementation(enforcedPlatform(libs.spring.boot.bom))
	implementation(enforcedPlatform(libs.vaadin.bom))

	implementation(libs.spring.boot.starter)
	implementation(libs.vaadin.spring.boot.starter)

	implementation(libs.vaadin.core) {
		if (vaadinProductionMode) { // https://github.com/vaadin/flow/issues/18572
			exclude(module = "vaadin-dev")
		}
	}

	testImplementation(libs.spring.boot.starter.test)

	developmentOnly(enforcedPlatform(libs.spring.boot.bom))
	developmentOnly(libs.spring.boot.devtools)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
