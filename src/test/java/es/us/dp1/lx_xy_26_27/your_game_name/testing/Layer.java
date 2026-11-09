package es.us.dp1.lx_xy_26_27.your_game_name.testing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.qameta.allure.LabelAnnotation;

/**
 * Sets the Allure "layer" label for layers without a dedicated annotation,
 * e.g. {@code @Layer("security")}. Prefer {@link ControllerTest},
 * {@link ServiceTest}, {@link RepositoryTest} or {@link ModelTest} when they apply.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@LabelAnnotation(name = "layer")
public @interface Layer {

	String value();

}
