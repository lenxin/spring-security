package org.springframework.security.acls;

import org.junit.Test;

import org.springframework.security.acls.domain.AclFormattingUtils;
import org.springframework.security.acls.model.Permission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * Tests for {@link AclFormattingUtils}.
 *
 * @author Andrei Stefan
 */
public class AclFormattingUtilsTests {

	@Test
	public final void testDemergePatternsParametersConstraints() {
		assertThatIllegalArgumentException().isThrownBy(() -> AclFormattingUtils.demergePatterns(null, "SOME STRING"));
		assertThatIllegalArgumentException().isThrownBy(() -> AclFormattingUtils.demergePatterns("SOME STRING", null));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> AclFormattingUtils.demergePatterns("SOME STRING", "LONGER SOME STRING"));
		assertThatNoException().isThrownBy(() -> AclFormattingUtils.demergePatterns("SOME STRING", "SAME LENGTH"));
	}

	@Test
	public final void testDemergePatterns() {
		String original = "...........................A...R";
		String removeBits = "...............................R";
		assertThat(AclFormattingUtils.demergePatterns(original, removeBits))
				.isEqualTo("...........................A....");
		assertThat(AclFormattingUtils.demergePatterns("ABCDEF", "......")).isEqualTo("ABCDEF");
		assertThat(AclFormattingUtils.demergePatterns("ABCDEF", "GHIJKL")).isEqualTo("......");
	}

	@Test
	public final void testMergePatternsParametersConstraints() {
		assertThatIllegalArgumentException().isThrownBy(() -> AclFormattingUtils.mergePatterns(null, "SOME STRING"));
		assertThatIllegalArgumentException().isThrownBy(() -> AclFormattingUtils.mergePatterns("SOME STRING", null));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> AclFormattingUtils.mergePatterns("SOME STRING", "LONGER SOME STRING"));
		assertThatNoException().isThrownBy(() -> AclFormattingUtils.mergePatterns("SOME STRING", "SAME LENGTH"));
	}

	@Test
	public final void testMergePatterns() {
		String original = "...............................R";
		String extraBits = "...........................A....";
		assertThat(AclFormattingUtils.mergePatterns(original, extraBits)).isEqualTo("...........................A...R");
		assertThat(AclFormattingUtils.mergePatterns("ABCDEF", "......")).isEqualTo("ABCDEF");
		assertThat(AclFormattingUtils.mergePatterns("ABCDEF", "GHIJKL")).isEqualTo("GHIJKL");
	}

	@Test
	public final void testBinaryPrints() {
		assertThat(AclFormattingUtils.printBinary(15)).isEqualTo("............................****");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> AclFormattingUtils.printBinary(15, Permission.RESERVED_ON));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> AclFormattingUtils.printBinary(15, Permission.RESERVED_OFF));
		assertThat(AclFormattingUtils.printBinary(15, 'x')).isEqualTo("............................xxxx");
	}

	@Test
	public void testPrintBinaryNegative() {
		assertThat(AclFormattingUtils.printBinary(0x80000000)).isEqualTo("*...............................");
	}

	@Test
	public void testPrintBinaryMinusOne() {
		assertThat(AclFormattingUtils.printBinary(0xffffffff)).isEqualTo("********************************");
	}

}
