package org.springframework.security.config.annotation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatObject;

/**
 * @author Rob Winch
 * @author Josh Cummings
 *
 */
public class ObjectPostProcessorTests {

	@Test
	public void convertTypes() {
		assertThatObject(PerformConversion.perform(new ArrayList<>())).isInstanceOf(LinkedList.class);
	}

	static class ListToLinkedListObjectPostProcessor implements ObjectPostProcessor<List<?>> {

		@Override
		public <O extends List<?>> O postProcess(O l) {
			return (O) new LinkedList(l);
		}

	}

	static class PerformConversion {

		static List<?> perform(ArrayList<?> l) {
			return new ListToLinkedListObjectPostProcessor().postProcess(l);
		}

	}

}
