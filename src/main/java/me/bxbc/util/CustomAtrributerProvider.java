package me.bxbc.util;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * Author: BI XI
 * Date 2021/2/16
 */
public class CustomAtrributerProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if(node instanceof Link) {
            attributes.put("target", "_blank");
        }
        if(node instanceof TableBlock) {
            attributes.put("class", "ui celled table");
        }
        if (node instanceof Image) {
            attributes.put("class", "ui image");
        }
    }
}
