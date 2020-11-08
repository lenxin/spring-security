package org.springframework.security.config.doc;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Josh Cummings
 */
public class XmlNode {

	private final Node node;

	public XmlNode(Node node) {
		this.node = node;
	}

	public String simpleName() {
		String[] parts = this.node.getNodeName().split(":");
		return parts[parts.length - 1];
	}

	public String text() {
		return this.node.getTextContent();
	}

	public Stream<XmlNode> children() {
		NodeList children = this.node.getChildNodes();
		// @formatter:off
		return IntStream.range(0, children.getLength())
				.mapToObj(children::item)
				.map(XmlNode::new);
		// @formatter:on
	}

	public Optional<XmlNode> child(String name) {
		return this.children().filter((child) -> name.equals(child.simpleName())).findFirst();
	}

	public Optional<XmlNode> parent() {
		// @formatter:off
		return Optional.ofNullable(this.node.getParentNode())
				.map((parent) -> new XmlNode(parent));
		// @formatter:on
	}

	public String attribute(String name) {
		// @formatter:off
		return Optional.ofNullable(this.node.getAttributes())
				.map((attrs) -> attrs.getNamedItem(name))
				.map((attr) -> attr.getTextContent())
				.orElse(null);
		// @formatter:on
	}

	public Node node() {
		return this.node;
	}

}
