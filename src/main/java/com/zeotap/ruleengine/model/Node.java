package com.zeotap.ruleengine.model;

public class Node {
    private String type;  // "operator" for AND/OR, "operand" for conditions
    private String value; // The value of the operand (e.g., age > 30)
    private Node left;    // Left child node
    private Node right;   // Right child node

    public Node(String type, String value) {
        this.type = type;
        this.value = value;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
