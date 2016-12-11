package com.pollistics.utils;

import java.text.MessageFormat;

public class MemeSlugs {
	private final static String[] words = new String[]{
		"meme",
		"bullgit",
		"yolo",
		"swag",
		"memer",
		"coolio",
		"fun",
		"quark",
		"odisee",
		"lel",
		"xddd",
		"giga",
		"wauw",
		"poll",
		"paul",
		"maliek",
		"hanne",
		"elias",
		"ruben",
		"haroen",
		"peter",
		"katja",
		"kevin",
		"ok",
		"leuk"
	};

	public static String getCombo() {
		return MessageFormat.format("{0}-{1}-{2}", getWord(), getWord(), getWord());
	}

	public static String getWord() {
		return words[(int) Math.floor(Math.random() * words.length)];
	}
}
